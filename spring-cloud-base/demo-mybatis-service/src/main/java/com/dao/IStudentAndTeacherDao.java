package com.dao;

public interface IStudentAndTeacherDao
{
    
    void batchSaveStuAndTeacher(String studentId, String[] teacherIds);
    
    // void save(StudentAndTeacher student);
    //
    //
    void deleteByStudentId(String studentId);
    //
    // void deleteByteacherId(String teacherId);
    
}
