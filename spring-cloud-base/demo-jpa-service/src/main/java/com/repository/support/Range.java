
package com.repository.support;

import java.io.Serializable;

public class Range<E> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String field;
    private Comparable<Object> from;
    private Comparable<Object> to;
    private Boolean includeNull;
    
    public Range(String field)
    {
        this.field = field;
    }
    
    public Range(String field, Comparable<Object> from, Comparable<Object> to)
    {
        this.field = field;
        this.from = from;
        this.to = to;
    }
    
    public Range(String field, Comparable<Object> from, Comparable<Object> to, Boolean includeNull)
    {
        this.field = field;
        this.from = from;
        this.to = to;
        this.includeNull = includeNull;
    }
    
    public Range(Range<E> other)
    {
        this.field = other.getField();
        this.from = other.getFrom();
        this.to = other.getTo();
        this.includeNull = other.getIncludeNull();
    }
    
    public String getField()
    {
        return field;
    }
    
    public Comparable<Object> getFrom()
    {
        return from;
    }
    
    public void setFrom(Comparable<Object> from)
    {
        this.from = from;
    }
    
    public boolean isFromSet()
    {
        return getFrom() != null;
    }
    
    public Comparable<Object> getTo()
    {
        return to;
    }
    
    public void setTo(Comparable<Object> to)
    {
        this.to = to;
    }
    
    public boolean isToSet()
    {
        return getTo() != null;
    }
    
    public void setIncludeNull(boolean includeNull)
    {
        this.includeNull = includeNull;
    }
    
    public Boolean getIncludeNull()
    {
        return includeNull;
    }
    
    public boolean isIncludeNullSet()
    {
        return includeNull != null;
    }
    
    public boolean isBetween()
    {
        return isFromSet() && isToSet();
    }
    
    public boolean isSet()
    {
        return isFromSet() || isToSet() || isIncludeNullSet();
    }
    
    public boolean isValid()
    {
        if (isBetween())
        {
            return getFrom().compareTo(getTo()) <= 0;
        }
        
        return true;
    }
}