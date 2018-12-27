package com.my.bean;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_class")
public class MyClass
{
    @TableId(value = "pk_class_id")
    private String id;
    
    @TableField(value = "class_name")
    private String name;
    
    private List<Student> students = new ArrayList<>();
    
    public MyClass()
    {
    }
    
    public MyClass(String id)
    {
        this.id = id;
    }
    
    public MyClass(String id, String name)
    {
        this.id = id;
        this.name = name;
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
    
    public List<Student> getStudents()
    {
        return students;
    }
    
    public void setStudents(List<Student> students)
    {
        this.students = students;
    }
    
    @Override
    public String toString()
    {
        return "MyClass [id=" + id + ", name=" + name + ", students=" + students + "]";
    }
    
}
