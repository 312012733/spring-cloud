package com.repository.support;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ByRangeSpecification<T> implements Specification<T>
{
    private static final long serialVersionUID = 3901727776901807612L;
    
    private final List<Range<T>> ranges;
    
    public ByRangeSpecification(List<Range<T>> ranges)
    {
        this.ranges = ranges;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
    {
        List<Predicate> predicates = Lists.newArrayList();
        
        if (null == ranges)
        {
            return builder.conjunction();
        }
        
        for (Range<T> range : ranges)
        {
            if (range.isSet())
            {
                Predicate rangePredicate = buildRangePredicate(range, root, builder);
                
                if (rangePredicate != null)
                {
                    if (!range.isIncludeNullSet() || Boolean.FALSE == range.getIncludeNull())
                    {
                        predicates.add(rangePredicate);
                    }
                    else
                    {
                        predicates.add(builder.or(rangePredicate, builder.isNull(root.get(range.getField()))));
                    }
                }
                
                if (Boolean.TRUE == range.getIncludeNull())
                {
                    predicates.add(builder.isNull(root.get(range.getField())));
                }
                else if (Boolean.FALSE == range.getIncludeNull())
                {
                    predicates.add(builder.isNotNull(root.get(range.getField())));
                }
            }
        }
        
        return predicates.isEmpty() ? builder.conjunction()
                : builder.and(Iterables.toArray(predicates, Predicate.class));
    }
    
    private Predicate buildRangePredicate(Range<T> range, Root<T> root, CriteriaBuilder builder)
    {
        if (range.isBetween())
        {
            return builder.between(root.get(range.getField()), range.getFrom(), range.getTo());
        }
        else if (range.isFromSet())
        {
            return builder.greaterThanOrEqualTo(root.get(range.getField()), range.getFrom());
        }
        else if (range.isToSet())
        {
            return builder.lessThanOrEqualTo(root.get(range.getField()), range.getTo());
        }
        return null;
    }
    
}
