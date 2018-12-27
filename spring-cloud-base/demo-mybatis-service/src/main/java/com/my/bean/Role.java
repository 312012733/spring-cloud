package com.my.bean;

public class Role
{
    private String id;
    
    private String name;
    
    private byte[] head;
    
    public Role()
    {
    }
    
    public Role(String id, String name, byte[] head)
    {
        this.id = id;
        this.name = name;
        this.head = head;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public byte[] getHead()
    {
        return head;
    }
    
    public void setHead(byte[] head)
    {
        this.head = head;
    }
    
    @Override
    public String toString()
    {
        return "Role [id=" + id + ", name=" + name + "]";
    }
    
}
