package com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bean.Student;
import com.form.StudentAddOrUpdateForm;
import com.vo.StudentDTO;

public interface IStudentService
{
    
    void addStudent(StudentAddOrUpdateForm stuDTO);
    
    Page<Student> findStudentsByPage(Pageable pageable, Student condition);
    
    StudentDTO findStudentById(String stuId);
    
    void updateStudent(StudentAddOrUpdateForm stuDTO);
    
    void delStudent(String stuId);
    
    void batchDelStudents(String[] ids);
    
    void bindStuIdcard(String stuId);
    
    void unBindStuIdcard(String stuId);
}
