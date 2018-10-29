package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Teacher;
import com.dao.ITeacherDao;
import com.service.ITeacherService;

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
