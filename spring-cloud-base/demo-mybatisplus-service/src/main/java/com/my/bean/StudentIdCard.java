package com.my.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_student_idcard")
public class StudentIdCard
{
    @TableId(value = "pk_student_idcard_id")
    private String id;
    
    @TableField(value = "student_num")
    private String num;
    
    private Student student;
    
    public StudentIdCard()
    {
    }
    
    public StudentIdCard(String id, String num)
    {
        this.id = id;
        this.num = num;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getNum()
    {
        return num;
    }
    
    public void setNum(String num)
    {
        this.num = num;
    }
    
    public Student getStudent()
    {
        return student;
    }
    
    public void setStudent(Student student)
    {
        this.student = student;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "StudentIdCard [id=" + id + ", num=" + num + ", student=" + student + "]";
    }
    
}
