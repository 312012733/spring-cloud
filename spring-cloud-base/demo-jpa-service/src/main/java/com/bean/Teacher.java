package com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_teacher")
public class Teacher
{
    
    @Id
    @Column(name = "pk_teacher_id")
    private String id;
    
    @Column(name = "teacher_name")
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
    // @Cascade(value =
    // { CascadeType.SAVE_UPDATE })
    private List<Student> students = new ArrayList<>();
    
    public Teacher()
    {
    }
    
    public Teacher(String id, String name)
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
        return "Teacher [id=" + id + ", name=" + name + ", students=" + students + "]";
    }
    
}
