package com.my.bean;

public class StudentAndTeacher
{
    private String studentId;
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
