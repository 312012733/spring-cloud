package com.my.service;

import java.util.List;

import com.my.bean.Teacher;

public interface ITeacherService
{
    
    // Teacher findTeacherById(String id);
    //
    // List<Teacher> findTeachersByIds(String... ids);
    //
    // void save(Teacher Teacher);
    //
    // void update(Teacher Teacher);
    //
    // void delete(String id);
    //
    // void batDelete(String... ids);
    //
    // Integer findTeacherCount(Teacher condition);
    //
    // List<Teacher> findTeacherByPage(Page<Teacher> page, Teacher condition);
    //
    List<Teacher> findTeachers();
    
    //
    // List<Teacher> findIncludTeachersByStuId(String studentId);
    //
    List<Teacher> findExcludTeachersByStuId(String stuId);
    
}
