package com.my.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_student_teacher")
public class StudentAndTeacher
{
    @TableId(value = "pk_student_id")
    private String studentId;
    
    @TableId(value = "pk_teacher_id")
    private String teacherId;
    
    public StudentAndTeacher()
    {
    }
    
    public StudentAndTeacher(String studentId, String teacherId)
    {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }
    
    public String getStudentId()
    {
        return studentId;
    }
    
    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }
    
    public String getTeacherId()
    {
        return teacherId;
    }
    
    public void setTeacherId(String teacherId)
    {
        this.teacherId = teacherId;
    }
    
    @Override
    public String toString()
    {
        return "StudentAndTeacher [studentId=" + studentId + ", teacherId=" + teacherId + "]";
    }
    
}
