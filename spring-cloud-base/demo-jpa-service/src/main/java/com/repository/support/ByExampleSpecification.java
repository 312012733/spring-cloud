package com.repository.support;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

public class ByExampleSpecification<T> implements Specification<T>
{
    private static final long serialVersionUID = -55852941510100030L;
    
    private final Example<T> example;
    
    public ByExampleSpecification(Example<T> example)
    {
        
        Assert.notNull(example, "Example must not be null!");
        this.example = example;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
    {
        return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
    }
}
