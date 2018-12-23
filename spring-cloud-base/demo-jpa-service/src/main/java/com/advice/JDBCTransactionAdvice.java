package com.advice;

import java.lang.reflect.Method;
import java.sql.Connection;

import com.utils.DBUtils;

public class JDBCTransactionAdvice extends BaseAdvice
{
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        
        Connection connection = DBUtils.getCurrentConnection();
        
        try
        {
            // 执行 service的方法
            Object result = method.invoke(getSource(), args);
            
            connection.commit();
            
            // System.out.println(
            // "【debug】" + getSource().getClass().getName() + "." +
            // method.getName() + " transaction is commit ");
            
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            if (null != connection)
            {
                connection.rollback();
            }
            
            throw (e.getCause() == null ? e : e.getCause());
        }
        finally
        {
            // DBUtils.close(connection);
        }
        
    }
    
}