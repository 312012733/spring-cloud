package com.my.vo;

import java.util.List;

import com.my.bean.MyClass;
import com.my.bean.Teacher;

public class StudentDTO
{
    private String id;
    
    private String name;
    
    private Integer age;
    
    private Boolean gender;// 默认是 男
    
    private MyClass myClass;
    
    private List<String> teacherIds;
    
    private List<Teacher> owerTeachers;
    
    private List<Teacher> unOwerTeachers;
    
    public StudentDTO()
    {
    }
    
    public List<String> getTeacherIds()
    {
        return teacherIds;
    }
    
    public void setTeacherIds(List<String> teacherIds)
    {
        this.teacherIds = teacherIds;
    }
    
    public MyClass getMyClass()
    {
        return myClass;
    }
    
    public void setMyClass(MyClass myClass)
    {
        this.myClass = myClass;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public Boolean getGender()
    {
        return gender;
    }
    
    public void setGender(Boolean gender)
    {
        this.gender = gender;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<Teacher> getOwerTeachers()
    {
        return owerTeachers;
    }
    
    public void setOwerTeachers(List<Teacher> owerTeachers)
    {
        this.owerTeachers = owerTeachers;
    }
    
    public List<Teacher> getUnOwerTeachers()
    {
        return unOwerTeachers;
    }
    
    public void setUnOwerTeachers(List<Teacher> unOwerTeachers)
    {
        this.unOwerTeachers = unOwerTeachers;
    }
    
    @Override
    public String toString()
    {
        return "StudentDTO [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", myClass="
                + myClass + ", teacherIds=" + teacherIds + ", owerTeachers=" + owerTeachers + ", unOwerTeachers="
                + unOwerTeachers + "]";
    }
    
}
