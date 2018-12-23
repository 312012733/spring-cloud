package com.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.stereotype.Component;

//@org.springframework.context.annotation.Configuration
@Component
public class HibernateUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtils.class);
    
    // @Bean
    // public SessionFactory buildSessionFactory()
    // {
    // Configuration config = new
    // Configuration().configure("hibernate.cfg.xml");
    // SessionFactory sf = config.buildSessionFactory();
    //
    // return sf;
    // }
    @Autowired
    private EntityManager entityManager;
    
    public <T> Page<T> findByPage(String listSql, String countSql, Pageable pageable, Map<String, Object> condition,
            Class<T> entityType)
    {
        List<T> pageList = findEntits(listSql, pageable, condition, entityType);
        
        BigInteger count = uniqueResult(countSql, condition, BigInteger.class);
        
        Page<T> page = new PageImpl<T>(pageList, pageable, count.longValue());
        
        return page;
    }
    
    public static <T> PagedResources<T> buildPageResourse(Pageable pageable, List<T> result, long totalSize,
            HttpServletRequest request)
    {
        Page<T> page = new PageImpl<T>(result, pageable, totalSize);
        
        List<Link> links = LinkUtils.prepareLinks(pageable.getPageNumber(), pageable.getPageSize(), request, page, "");
        
        return new PagedResources<T>(result,
                new PageMetadata(page.getSize(), page.getNumber(), totalSize, getTotalPages(page)), links);
    }
    
    public static <T> int getTotalPages(Page<T> page)
    {
        return page.getSize() == 0 ? 1 : (int) Math.ceil((double) page.getTotalElements() / (double) page.getSize());
    }
    
    public <T> List<T> findEntits(String sql, Map<String, Object> condition, Class<T> entityType)
    {
        return findEntits(sql, null, condition, entityType);
    }
    
    public <T> T findEntit(String sql, Map<String, Object> condition, Class<T> entityType)
    {
        
        List<T> list = findEntits(sql, condition, entityType);
        
        if (null == list)
        {
            return null;
        }
        
        if (list.size() > 1)
        {
            throw new SecurityException("result > 1");
        }
        
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T uniqueResult(String sql, Map<String, Object> condition, Class<T> resultType)
    {
        LOG.info(sql + " -- " + condition);
        
        Query query = entityManager.createNativeQuery(sql);
        
        setParameter(condition, query);
        
        T result = (T) query.getSingleResult();
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private <T> List<T> findEntits(String sql, Pageable page, Map<String, Object> condition, Class<T> entityType)
    {
        LOG.info(sql + " -- " + condition);
        
        Query query = entityManager.createNativeQuery(sql, entityType);
        
        if (null != page)
        {
            query.setFirstResult(page.getPageSize() * page.getPageNumber());
            query.setMaxResults(page.getPageSize());
        }
        
        setParameter(condition, query);
        
        List<T> listResult = query.getResultList();
        
        return listResult;
        
    }
    
    private <T> void setParameter(Map<String, Object> condition, Query query)
    {
        if (null != condition)
        {
            for (Entry<String, Object> entry : condition.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
    
}
