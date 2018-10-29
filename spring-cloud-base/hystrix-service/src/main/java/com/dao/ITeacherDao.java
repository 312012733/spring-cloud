package com.dao;

import java.util.List;

import com.bean.Teacher;

public interface ITeacherDao
{
    // Teacher findTeacherById(String TeacherId);
    //
    // void save(Teacher Teacher);
    //
    // void update(Teacher Teacher);
    //
    // void delete(String id);
    //
    // void batDelete(String[] ids);
    //
    // Integer findTeacherCount(Teacher condition);
    //
    // List<Teacher> findTeachersByPage(Page<Teacher> page, Teacher condition);
    //
    
    String[] findTeacherIdsByTeacherIds(String[] teacherIds);
    
    List<Teacher> findTeachers();
    
    // List<Teacher> findTeachersByIncludStuIds(String stuId);
    
    List<Teacher> findTeachersByExcludStuIds(String stuId);
    
}
