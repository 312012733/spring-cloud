package com.listener;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.util.StringUtils;

import com.advice.BaseAdvice;
import com.persistent.annotation.Sql;
import com.utils.DBUtils;
import com.utils.DBUtilsForDaoMapping;
import com.utils.FileUtils;
import com.utils.FileUtils.ClassHandler;

/**
 * 在服务器启动的时候，扫描并加载 所有的 service实现类 对他们进行动态代理，处理事务
 * 
 * 好处：实现了 持久层和业务层 的解耦 是代码内聚度提高，把事务的处理集中 减少了重复代码
 */
public class DaoMappingListener implements ServletContextListener
{
    
    // 存放 代理对象 的容器
    private static final Map<Object, Object> PROXY_CONTAINER_MAP = new HashMap<>();
    
    private static final Map<Object, DaoConfig> DAO_CONFIG_CONTAINER_MAP = new HashMap<>();
    
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            
            // 获取相关dao接口的命名规则
            String daoMapping = sce.getServletContext().getInitParameter("daoMapping");
            
            // 把命名规则 转化 成 正则表达式
            String daoMappingReg = daoMapping.replaceAll("\\*", ".+");
            
            /*--------------------------------开始扫描 class文件------------------------------------------*/
            
            // 获取 classPath
            String classPath = sce.getServletContext().getRealPath(FileUtils.WEB_INFO_CLASSES);
            
            File classPathDir = new File(classPath);
            
            // 扫描 class文件，并且 加载 符合命名规则的的 class类，然后对其 动态代理，最后存放至内存（map）。
            FileUtils.listClassFile(classPathDir, daoMappingReg, new DaoMappingProxyHandler());
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static class DaoMapper
    {
        
        @SuppressWarnings("unchecked")
        public static <T> T get(Class<T> daoClass)
        {
            T service = (T) PROXY_CONTAINER_MAP.get(daoClass);
            
            return service;
        }
    }
    
    private static class DaoMappingProxyHandler implements ClassHandler
    {
        @Override
        public void handle(Class<?> sourceClass)
        {
            try
            {
                if (!sourceClass.isInterface())// TODO dao 应该给个注解
                {
                    throw new SecurityException(sourceClass.getName() + " is not  dao interface");
                }
                
                BaseAdvice invocationHandler = new DaoProcessAdvice();
                
                Object daoProxyObj = Proxy.newProxyInstance(DaoMappingListener.class.getClassLoader(), new Class[]
                { sourceClass }, invocationHandler);
                
                PROXY_CONTAINER_MAP.put(sourceClass, daoProxyObj);// 提供给sevice
                
                // 加载配置。并放进容器
                DaoConfig daoConfig = loadDaoConfig(sourceClass);
                DAO_CONFIG_CONTAINER_MAP.put(sourceClass.getName(), daoConfig);// 提供给切面
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private static DaoConfig loadDaoConfig(Class<?> daoClass) throws ClassNotFoundException
    {
        DaoConfig daoConfig = new DaoConfig();
        
        // 设置dao的nameSpace
        daoConfig.nameSpace = daoClass.getName();
        
        Method[] methods = daoClass.getDeclaredMethods();
        
        for (Method method : methods)
        {
            DaoMethodConfig methodConfig = new DaoMethodConfig();
            
            String sql = "";
            Sql sqlAnotation = method.getAnnotation(Sql.class);
            
            if (null != sqlAnotation)
            {
                sql = sqlAnotation.sql();
                
                methodConfig.sql = sql;
                methodConfig.methodName = method.getName();
                methodConfig.retunType = method.getReturnType();
                
                if (!StringUtils.isEmpty(sqlAnotation.entityTypeName()))
                {
                    methodConfig.entiyType = Class.forName(sqlAnotation.entityTypeName());
                }
                
                daoConfig.daoMethodMapping.put(method.getName(), methodConfig);
            }
        }
        
        return daoConfig;
    }
    
    @SuppressWarnings("unused")
    private static class DaoConfig
    {
        String nameSpace;
        
        Map<String, DaoMethodConfig> daoMethodMapping = new HashMap<>();
    }
    
    @SuppressWarnings("unused")
    private static class DaoMethodConfig
    {
        private String methodName;
        
        private String sql;// TODO 用注解？
        
        private Class<?> retunType;
        
        private Class<?> entiyType;
        
    }
    
    private static class DaoProcessAdvice extends BaseAdvice
    {
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            
            try
            {
                Connection connection = DBUtils.getCurrentConnection();
                
                Object result = null;
                
                Class<?> daoClass = proxy.getClass().getInterfaces()[0];
                
                DaoConfig config = DAO_CONFIG_CONTAINER_MAP.get(daoClass.getName());
                
                DaoMethodConfig methodConfig = config.daoMethodMapping.get(method.getName());
                
                String sql = methodConfig.sql;
                
                System.out.println("【debug】 sql: " + sql);
                
                for (int i = 0; i < args.length; i++)
                {
                    System.out.println("【debug】 arg[" + i + "]: " + args[i]);
                }
                
                Class<?> entityType = methodConfig.entiyType;// 实体的类型。
                Class<?> retunType = methodConfig.retunType;// 不为空 就是查询。。
                
                if (void.class.getName() == retunType.getName())
                {
                    DBUtilsForDaoMapping.execute(connection, sql, args);
                }
                else if (retunType.getName().equals(List.class.getName()))
                {
                    result = DBUtilsForDaoMapping.findEntiys(connection, sql, args, entityType);
                }
                else
                {
                    if (null == entityType)
                    {
                        result = DBUtilsForDaoMapping.uniqueValue(connection, sql, args);
                    }
                    else
                    {
                        
                        result = DBUtilsForDaoMapping.findEntiy(connection, sql, args, entityType);
                    }
                }
                
                return result;
            }
            catch (Exception e)
            {
                throw e;
            }
            
        }
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
    }
}
