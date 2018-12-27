package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.Student;

@Mapper
public interface IStudentDao extends BaseMapper<Student>
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
