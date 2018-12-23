package com.repository.support;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements ICustomRepository<T, ID>
{
    
    public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager)
    {
        super(entityInformation, entityManager);
    }
    
    @Override
    public Page<T> findAll(Example<T> example, List<Range<T>> ranges, Pageable pageable)
    {
        ByExampleSpecification<T> byExampleSpecification = new ByExampleSpecification<>(example);
        ByRangeSpecification<T> byRangeSpecification = new ByRangeSpecification<>(ranges);
        
        return findAll(Specification.where(byExampleSpecification).and(byRangeSpecification), pageable);
    }
    
    @Override
    public Page<T> findAll(Specification<T> specification, List<Range<T>> ranges, Pageable pageable)
    {
        ByRangeSpecification<T> byRangeSpecification = new ByRangeSpecification<>(ranges);
        
        return findAll(Specification.where(specification).and(byRangeSpecification), pageable);
    }
    
}
