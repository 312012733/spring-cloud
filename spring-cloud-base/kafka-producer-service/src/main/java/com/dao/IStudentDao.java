package com.dao;

import java.util.List;

import com.bean.Student;
import com.vo.Page;

public interface IStudentDao
{
    void addStudent(Student stu);
    
    Student findStudentById(String stuId);
    
    List<Student> findStudentsByPage(Page<Student> page, Student condition);
    
    Long queryCount(Student condition);
    
    void updateStudent(Student stu);
    
    void delStudent(String stuId);
    
    // delete s from stu s where s.id in ( 'id1' , 'id2' , 'id3' );
    void batchDelStudent(String[] stuIds);
    
}
