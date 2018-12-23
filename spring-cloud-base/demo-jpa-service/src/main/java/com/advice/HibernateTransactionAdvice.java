package com.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//@Aspect
//@Component
public class HibernateTransactionAdvice
{
    // @Autowired
    private SessionFactory sf;
    
    @Around("execution(* com.service..*(..))")
    public Object invoke(ProceedingJoinPoint jp) throws Throwable
    {
        
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        
        try
        {
            
            Object result = jp.proceed(jp.getArgs());
            
            transaction.commit();
            
            return result;
        }
        catch (Throwable e)
        {
            transaction.rollback();
            throw e;
        }
        
    }
    
}