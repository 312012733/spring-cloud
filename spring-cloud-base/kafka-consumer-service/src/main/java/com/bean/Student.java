package com.bean;

import java.util.ArrayList;
import java.util.List;

public class Student
{
    
    private String id;
    
    private String name;
    
    private Integer age;
    
    private Boolean gender;// 默认是 男
    
    private Long createTime;
    
    private Long lastModifyTime;
    
    private StudentIdCard studentIdCard;
    
    private MyClass myClass;
    
    private List<Teacher> teachers = new ArrayList<>();
    
    public Student()
    {
    }
    
    public Student(String id, String name, Integer age, Boolean gender)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    
    public Long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }
    
    public Long getLastModifyTime()
    {
        return lastModifyTime;
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
    
    public StudentIdCard getStudentIdCard()
    {
        return studentIdCard;
    }
    
    public void setStudentIdCard(StudentIdCard studentIdCard)
    {
        this.studentIdCard = studentIdCard;
    }
    
    public void setLastModifyTime(Long lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
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
    
    public MyClass getMyClass()
    {
        return myClass;
    }
    
    public void setMyClass(MyClass myClass)
    {
        this.myClass = myClass;
    }
    
    public List<Teacher> getTeachers()
    {
        return teachers;
    }
    
    public void setTeachers(List<Teacher> teachers)
    {
        this.teachers = teachers;
    }
    
    @Override
    public String toString()
    {
        return "Student [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", createTime="
                + createTime + ", lastModifyTime=" + lastModifyTime + ", studentIdCard=" + studentIdCard + ", myClass="
                + myClass + ", teachers=" + teachers + "]";
    }
    
}
