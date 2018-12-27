package com.my.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_user")
public class Role
{
    @TableId(value = "pk_role_id")
    private String id;
    
    @TableField(value = "role_name")
    private String name;
    
    @TableField(value = "role_head")
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
