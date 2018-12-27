package com.my.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_class")
public class MyClass
{
    
    @Id
    @Column(name = "pk_class_id")
    private String id;
    
    @Column(name = "class_name")
    private String name;
    
    @JsonIgnore
    @OneToMany(mappedBy = "myClass", fetch = FetchType.EAGER)
    // @Cascade(value =
    // { CascadeType.SAVE_UPDATE })
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
