package com.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class BaseAdvice implements InvocationHandler
{
    private Object source;
    
    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
    
    public Object getSource()
    {
        return source;
    }
    
    public void setSource(Object source)
    {
        this.source = source;
    }
    
}