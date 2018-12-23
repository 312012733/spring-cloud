package com.advice;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeAdvice extends BaseAdvice
{
    private static final Logger LOG = LoggerFactory.getLogger(TimeAdvice.class);
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        try
        {
            
            Long startTime = System.currentTimeMillis();
            
            Object source = getSource();
            
            // 执行 service的方法
            Object result = method.invoke(source, args);
            
            Long endTime = System.currentTimeMillis();
            
            LOG.debug(source.getClass().getName() + "." + method.getName() + " cost time is " + (endTime - startTime));
            
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
    }
    
}