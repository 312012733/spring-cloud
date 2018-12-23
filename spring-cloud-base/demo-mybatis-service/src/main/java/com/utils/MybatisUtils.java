package com.utils;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils
{
    private static SqlSessionFactory sqlSessionFactory;
    
    static
    {
        InputStream in = MybatisUtils.class.getClassLoader().getResourceAsStream("mybatis.xml");
        
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }
    
    // private static SqlSessionFactory getSqlSessionFactory()
    // {
    // return sqlSessionFactory;
    // }
    
    public static SqlSession getCurrentSession()
    {
        
        SqlSession session = (SqlSession) ThreadLocalUtils.get(ThreadLocalUtils.SQL_SESSION);
        
        if (null == session)
        {
            session = sqlSessionFactory.openSession();
            
            ThreadLocalUtils.put(ThreadLocalUtils.SQL_SESSION, session);
        }
        
        return session;
        
    }
    
    public static void close(SqlSession session)
    {
        
        if (null != session)
        {
            session.close();
            ThreadLocalUtils.remove(ThreadLocalUtils.SQL_SESSION);
        }
        
    }
}
