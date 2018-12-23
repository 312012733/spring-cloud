package com.listener;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.advice.BaseAdvice;
import com.utils.FileUtils;
import com.utils.FileUtils.ClassHandler;

/**
 * 在服务器启动的时候，扫描并加载 所有的 service实现类 对他们进行动态代理，处理事务
 * 
 * 好处：实现了 持久层和业务层 的解耦 是代码内聚度提高，把事务的处理集中 减少了重复代码
 */
public class DynProxyListener implements ServletContextListener
{
    
    // 存放 代理对象 的容器
    private static final Map<Object, Object> PROXY_CONTAINER_MAP = new HashMap<>();
    
    private static final List<String> SERVICE_ADVICE_CLASS_NAME_LIST = new ArrayList<>();
    
    private static final List<String> DAO_ADVICE_CLASS_NAME_LIST = new ArrayList<>();
    
    static
    {
        SERVICE_ADVICE_CLASS_NAME_LIST.add("com.advice.MybatisTransactionAdvice");
        // SERVICE_ADVICE_CLASS_NAME_LIST.add("com.advice.TimeAdvice");
        
        // DAO_ADVICE_CLASS_NAME_LIST.add("com.advice.TimeAdvice");
        // DAO_ADVICE_CLASS_NAME_LIST.add("com.advice.DaoProcessAdvice");
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            // 获取相关service类的命名规则
            String serviceReg = sce.getServletContext().getInitParameter("serviceReg");
            
            // 把命名规则 转化 成 正则表达式
            serviceReg = serviceReg.replaceAll("\\*", ".+");
            //
            // // 获取相关service类的命名规则
            // String daoReg =
            // sce.getServletContext().getInitParameter("daoReg");
            //
            // // 把命名规则 转化 成 正则表达式
            // daoReg = daoReg.replaceAll("\\*", ".+");
            
            /*--------------------------------开始扫描 class文件------------------------------------------*/
            
            // 获取 classPath
            String classPath = sce.getServletContext().getRealPath(FileUtils.WEB_INFO_CLASSES);
            
            File classPathDir = new File(classPath);
            
            /* -------注意：dao 和 service 的一个加载顺序 . 依赖 低 --》》》》 依赖 高 ---------- */
            // // 扫描 class文件，并且 加载 符合命名规则的的 class类，然后对其 动态代理，最后存放至内存（map）。
            // FileUtils.listClassFile(classPathDir, daoReg, new
            // DaoProxyHandler());
            
            // 扫描 class文件，并且 加载 符合命名规则的的 class类，然后对其 动态代理，最后存放至内存（map）。
            FileUtils.listClassFile(classPathDir, serviceReg, new ServiceProxyHandler());
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void generateProxyObject(Class<?> sourceClass, List<String> adviceClassNameList,
            Map<Object, Object> container)
    {
        try
        {
            // 通过 类全名 获取动态代理后的实例
            // 实例化 service的实现类
            Object sourceInstance = sourceClass.newInstance();
            
            // 获取 service的实现类 的代理对象 （利用了动态代理）
            
            // for (String adviceClassName : adviceClassNameList)
            for (int i = adviceClassNameList.size() - 1; i >= 0; i--)
            {
                
                String adviceClassName = adviceClassNameList.get(i);
                
                BaseAdvice invocationHandler = (BaseAdvice) Class.forName(adviceClassName).newInstance();
                
                invocationHandler.setSource(sourceInstance);
                
                sourceInstance = Proxy.newProxyInstance(DynProxyListener.class.getClassLoader(),
                        sourceClass.getInterfaces(), invocationHandler);
                
            }
            
            // 把代理对象 放入 map中，key 是 service的实现类的所有接口。value
            // 是service的实现类 的代理对象
            for (Class<?> interfaceClass : sourceClass.getInterfaces())
            {
                container.put(interfaceClass, sourceInstance);
            }
            
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static class ProxyOjectUtils
    {
        
        @SuppressWarnings("unchecked")
        public static <T> T getProxyObject(Class<T> serviceInterfaceClass)
        {
            T service = (T) PROXY_CONTAINER_MAP.get(serviceInterfaceClass);
            
            return service;
        }
    }
    
    private static class ServiceProxyHandler implements ClassHandler
    {
        
        @Override
        public void handle(Class<?> sourceClass)
        {
            generateProxyObject(sourceClass, SERVICE_ADVICE_CLASS_NAME_LIST, PROXY_CONTAINER_MAP);
        }
    }
    
    @SuppressWarnings("unused")
    private static class DaoProxyHandler implements ClassHandler
    {
        @Override
        public void handle(Class<?> sourceClass)
        {
            generateProxyObject(sourceClass, DAO_ADVICE_CLASS_NAME_LIST, PROXY_CONTAINER_MAP);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
    }
}
