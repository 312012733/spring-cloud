package com.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.persistent.annotation.Colum;

/*
 * TODO  连接关闭 -- 已解决 （OpenConnectionInViewFilter 或  DBUtils.close()）
 * */
public class DBUtilsForDaoMapping
{
    private static final String DYN_PARAM_REG = "#\\{([^\\{\\}]+)\\}";
    
    private static final String URL_KEY = "url";
    private static final String USER_NAME_KEY = "jdbc.username";
    private static final String PASSWORD_KEY = "jdbc.password";
    private static final String DRIVER_KEY = "jdbc.driverClassName";
    
    private static String url;
    private static String userName;
    private static String password;
    private static String driver;
    private static Properties config;
    
    static
    {
        try
        {
            // 加载jdbc配置文件
            config = new Properties();
            
            InputStream in = DBUtilsForDaoMapping.class.getClassLoader().getResourceAsStream("jdbc.properties");
            config.load(in);
            
            url = config.getProperty(URL_KEY);
            userName = config.getProperty(USER_NAME_KEY);
            password = config.getProperty(PASSWORD_KEY);
            driver = config.getProperty(DRIVER_KEY);
            
            // 加载驱动
            Class.forName(driver);// 利用类加载后 执行 静态块代码的原理，来进行驱动的初始化。
            
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    public static Connection getCurrentConnection() throws SQLException
    {
        Connection connection = (Connection) ThreadLocalUtils.get(ThreadLocalUtils.JDBC_CONNECTION);
        
        if (null == connection)
        {
            connection = getConnection();
            
            ThreadLocalUtils.put(ThreadLocalUtils.JDBC_CONNECTION, connection);
        }
        
        return connection;
    }
    
    public static <T> List<T> findEntiys(Connection connection, String sql, Object[] params, Class<T> entity)
            throws Exception
    {
        PreparedStatement statement = getPreparedStatement(connection, sql, params);
        
        return execute(statement, entity);
    }
    
    public static <T> List<T> findEntiys(Connection connection, String sql, Class<T> entity) throws Exception
    {
        return findEntiys(connection, sql, null, entity);
    }
    
    public static <T> T findEntiy(Connection connection, String sql, Object[] params, Class<T> entity) throws Exception
    {
        
        List<T> resultList = findEntiys(connection, sql, params, entity);
        
        if (null == resultList || resultList.isEmpty())
        {
            return null;
        }
        
        if (resultList.size() > 1)
        {
            throw new SecurityException("row is > 1");
        }
        
        return resultList.get(0);
    }
    
    public static <T> Object uniqueValue(Connection connection, String sql, Object[] params) throws Exception
    {
        PreparedStatement statement = DBUtilsForDaoMapping.getPreparedStatement(connection, sql, params);
        
        ResultSet resultSet = statement.executeQuery();
        
        return resultSet.next() ? resultSet.getObject(1) : null;
    }
    
    public static void execute(Connection connection, String sql, Object[] params) throws Exception
    {
        PreparedStatement statement = getPreparedStatement(connection, sql, params);
        execute(statement, null);
    }
    
    public static void close(Connection connection)
    {
        if (null != connection)
        {
            try
            {
                connection.close();
                
                ThreadLocalUtils.remove(ThreadLocalUtils.JDBC_CONNECTION);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void close(Statement statement)
    {
        if (null != statement)
        {
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private static <T> List<T> execute(PreparedStatement statement, Class<T> entity) throws Exception
    {
        boolean flag = statement.execute();// true: 有结果集（结果集不一定有数据） false: 非查询操作
        
        if (flag)
        {
            return buildResultSet(statement.getResultSet(), entity);
        }
        
        return null;
    }
    
    private static Connection getConnection() throws SQLException
    {
        Connection connection = DriverManager.getConnection(url, userName, password);
        
        connection.setAutoCommit(false);// 设置成手动提交
        
        return connection;
    }
    
    private static String buildSql(String sql)
    {
        return sql.replaceAll(DYN_PARAM_REG, "?");
    }
    
    @SuppressWarnings("rawtypes")
    private static <T> Map<String, Object> buildDynParamMap(String sql, Object[] params) throws Exception
    {
        Map<String, Object> dynParamsMap = new LinkedHashMap<>();// 使 map key
                                                                 // 有序。（按put的添加顺序）
        
        String numReg = "#\\{([^\\{\\}]+)\\}";
        
        Pattern p = Pattern.compile(numReg);
        
        Matcher m = p.matcher(sql);
        
        while (m.find())
        {
            Object value = null;
            String dynKey = m.group(1);// argN | argN.f1.f2.f3
            
            String[] dynKeyAry = dynKey.split("\\.");
            
            int paramIndex = Integer.parseInt(dynKeyAry[0].substring(3));
            
            if (dynKeyAry.length > 1)// argN.f1.f2.f3
            {
                
                value = params[paramIndex];
                
                if (value instanceof Map)
                {
                    value = ((Map) value).get(dynKeyAry[1]);
                }
                else
                {
                    for (int i = 1; i < dynKeyAry.length; i++)
                    {
                        PropertyDescriptor pd = new PropertyDescriptor(dynKeyAry[i], value.getClass());
                        
                        value = pd.getReadMethod().invoke(value);
                    }
                }
                
            }
            else // argN
            {
                value = params[paramIndex];
            }
            
            String key = m.group(0).replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}").replaceAll("\\.", "\\\\.");
            dynParamsMap.put(key, value);
        }
        
        return dynParamsMap;
    }
    
    private static PreparedStatement getPreparedStatement(Connection connection, String sql, Object[] params)
            throws Exception
    {
        
        System.out.println("【debug】" + sql + "   params:" + params);
        
        // select * from table t where 1 =1 and t.id = #{xxxx}
        // select * from table t where 1 =1 and t.id = ?
        
        String newSql = (null == params) ? sql : buildSql(sql);
        
        PreparedStatement preSatement = connection.prepareStatement(newSql);
        
        if (null == params)
        {
            return preSatement;
        }
        
        Map<String, Object> dynParamsMap = buildDynParamMap(sql, params);
        
        int i = 1;
        
        for (Entry<String, Object> entry : dynParamsMap.entrySet())
        {
            preSatement.setObject(i++, entry.getValue());
        }
        
        return preSatement;
    }
    
    private static <T> List<T> buildResultSet(ResultSet resultSet, Class<T> entity) throws Exception
    {
        
        if (null == entity)
        {
            return null;
        }
        
        List<T> resultList = new ArrayList<>();
        
        while (resultSet.next())
        {
            T entityObj = (T) entity.newInstance();
            
            Field[] fields = entity.getDeclaredFields();
            
            for (Field field : fields)// 获取每一行 中 每一列的数据
            {
                try
                {
                    Class<?> fieldType = field.getType();
                    
                    if (fieldType != String.class && fieldType != Integer.class && fieldType != boolean.class
                            && fieldType != Boolean.class)
                    {
                        // TODO
                        throw new SecurityException(fieldType.getName() + " type is un support..");
                    }
                    
                    String colum = getColumName(field);// 通过实体的属性，获取数据库列名
                    
                    // ===================获取数据 start=====================
                    
                    Object result = null;
                    
                    // TODO
                    if (fieldType == Boolean.class || fieldType == boolean.class)
                    {
                        result = resultSet.getBoolean(colum);// 0 false； !0
                                                             // true;
                    }
                    else
                    {
                        result = resultSet.getObject(colum);
                    }
                    
                    // ===================获取数据 end=====================
                    
                    // 给实例 的属性 赋值 .
                    // PropertyDescriptor 属性描述器
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entity);
                    
                    Method writMethod = pd.getWriteMethod();// 获取set方法
                    // pd.getReadMethod() 获取 get方法
                    
                    writMethod.invoke(entityObj, result);
                    
                }
                catch (Exception e)
                {
                    
                    System.out.println(
                            "【error】" + entity.getName() + "." + getColumName(field) + " is error. " + e.getMessage());
                    // e.printStackTrace();
                    continue;
                }
                
            }
            
            resultList.add(entityObj);// 添加当前行的数据
        }
        
        return resultList;
    }
    
    private static String getColumName(Field field)
    {
        Colum[] colums = field.getAnnotationsByType(Colum.class);
        
        if (null != colums && colums.length > 0)
        {
            return colums[colums.length - 1].name();
        }
        
        return field.getName();
    }
    
    private DBUtilsForDaoMapping()
    {
    }
}
