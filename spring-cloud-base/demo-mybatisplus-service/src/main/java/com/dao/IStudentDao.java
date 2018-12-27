package com.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.bean.Student;

public interface IStudentDao
{
    void addStudent(Student stu);
    
    Student findStudentById(String stuId);
    
    List<Student> findStudentsByPage(Pageable pageable, Student condition);
    
    Long queryCount(Student condition);
    
    void updateStudent(Student stu);
    
    void delStudent(String stuId);
    
    // delete s from stu s where s.id in ( 'id1' , 'id2' , 'id3' );
    void batchDelStudent(String[] stuIds);
    
}
