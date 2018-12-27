package com.my.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_student")
public class Student
{
    @Id
    @Column(name = "pk_student_id")
    private String id;
    
    @Column(name = "student_name")
    private String name;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "gender")
    private Boolean gender;// 默认是 男
    
    @Column(name = "create_time")
    private Long createTime;
    
    @Column(name = "last_modify_time")
    private Long lastModifyTime;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_card", unique = true)
    // @Cascade(value =
    // { CascadeType.SAVE_UPDATE })
    private StudentIdCard studentIdCard;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_class_id")
    // @Cascade(value =
    // { CascadeType.SAVE_UPDATE })
    private MyClass myClass;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_student_teacher", joinColumns =
    { @JoinColumn(name = "pk_student_id") }, inverseJoinColumns =
    { @JoinColumn(name = "pk_teacher_id") })
    // @Cascade(value =
    // { CascadeType.SAVE_UPDATE })
    private List<Teacher> teachers;
    
    public Student()
    {
    }
    
    public Student(String id)
    {
        this.id = id;
    }
    
    public Student(String id, String name, Integer age, Boolean gender, MyClass myClass)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.myClass = myClass;
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
        if (null == teachers)
        {
            teachers = new ArrayList<>();
        }
        
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
                + (null == myClass ? null : myClass.getName()) + ", teachers=" + teachers + "]";
    }
    
}
