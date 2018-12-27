package com.my.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_role")
public class Role
{
    
    @Id
    @Column(name = "pk_role_id")
    private String id;
    
    @Column(name = "role_name")
    private String name;
    
    @Column(name = "role_head", columnDefinition = "longblob")
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
