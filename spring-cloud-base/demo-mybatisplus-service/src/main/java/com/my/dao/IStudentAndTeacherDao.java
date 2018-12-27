package com.my.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.StudentAndTeacher;

@Mapper
public interface IStudentAndTeacherDao extends BaseMapper<StudentAndTeacher>
{
    
    void batchSaveStuAndTeacher(String studentId, String[] teacherIds);
    
    // void save(StudentAndTeacher student);
    //
    //
    void deleteByStudentId(String studentId);
    //
    // void deleteByteacherId(String teacherId);
    
}
