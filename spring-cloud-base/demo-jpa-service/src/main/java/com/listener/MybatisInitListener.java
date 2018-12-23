package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MybatisInitListener implements ServletContextListener
{
    
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            Class.forName("com.utils.MybatisUtils");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        // TODO Auto-generated method stub
        
    }
}
