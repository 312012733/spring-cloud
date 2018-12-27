package com.my.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.bean.Teacher;
import com.my.dao.ITeacherDao;
import com.my.service.ITeacherService;

@Service
public class TeacherServiceImpl implements ITeacherService
{
    @Autowired
    private ITeacherDao teacherDao;
    
    @Override
    public List<Teacher> findTeachers()
    {
        return teacherDao.findTeachers();
    }
    
    @Override
    public List<Teacher> findExcludTeachersByStuId(String stuId)
    {
        return teacherDao.findTeachersByExcludStuIds(stuId);
    }
    
}
