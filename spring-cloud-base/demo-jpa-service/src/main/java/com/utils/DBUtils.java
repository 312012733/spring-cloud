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
public class DBUtils
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
            
            InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
    
    public static <T, P> List<T> findEntiys(Connection connection, String sql, P params, Class<T> entity)
            throws Exception
    {
        PreparedStatement statement = getPreparedStatement(connection, sql, params);
        
        return execute(statement, entity);
    }
    
    public static <T> List<T> findEntiys(Connection connection, String sql, Class<T> entity) throws Exception
    {
        return findEntiys(connection, sql, null, entity);
    }
    
    public static <T, P> T findEntiy(Connection connection, String sql, P params, Class<T> entity) throws Exception
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
    
    public static <T> Object uniqueValue(Connection connection, String sql, T params) throws Exception
    {
        PreparedStatement statement = DBUtils.getPreparedStatement(connection, sql, params);
        
        ResultSet resultSet = statement.executeQuery();
        
        return resultSet.next() ? resultSet.getObject(1) : null;
    }
    
    public static <T> void execute(Connection connection, String sql, T params) throws Exception
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
    private static <T> Map<String, Object> buildDynParamMap(String sql, T param) throws Exception
    {
        Map<String, Object> dynParamsMap = new LinkedHashMap<>();// 使 map key
                                                                 // 有序。（按put的添加顺序）
        
        String numReg = "#\\{([^\\{\\}]+)\\}";
        
        Pattern p = Pattern.compile(numReg);
        
        Matcher m = p.matcher(sql);
        
        while (m.find())
        {
            Object value = param;
            String dynKey = m.group(1);// f0 | f0.f1.f2.f3
            
            String[] dynKeyAry = dynKey.split("\\.");
            
            String fieldName = dynKeyAry[0];
            
            if (dynKeyAry.length > 1)// f0.f1.f2.f3
            {
                
                if (value instanceof Map)
                {
                    throw new SecurityException("buildDynParamMap error . map is not support 'key.f1.f2.f3' ");
                }
                else
                {
                    
                    for (int i = 0; i < dynKeyAry.length; i++)
                    {
                        PropertyDescriptor pd = new PropertyDescriptor(dynKeyAry[i], value.getClass());
                        
                        value = pd.getReadMethod().invoke(value);
                    }
                }
            }
            else // f0
            {
                if (value instanceof Map)
                {
                    value = ((Map) param).get(fieldName);
                }
                else
                {
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, value.getClass());
                    
                    value = pd.getReadMethod().invoke(param);
                }
            }
            
            String key = m.group(0).replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}").replaceAll("\\.", "\\\\.");
            dynParamsMap.put(key, value);
        }
        
        return dynParamsMap;
    }
    
    private static <T> PreparedStatement getPreparedStatement(Connection connection, String sql, T params)
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
    
    private DBUtils()
    {
    }
}
