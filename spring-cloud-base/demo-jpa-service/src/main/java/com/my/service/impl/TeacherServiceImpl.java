package com.my.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.my.bean.Teacher;
import com.my.repository.ITeacherRepository;
import com.my.service.ITeacherService;

@Service
public class TeacherServiceImpl implements ITeacherService
{
    @Autowired
    private ITeacherRepository teacherDao;
    
    @Override
    public List<Teacher> findTeachers()
    {
        return teacherDao.findAll(new Sort(Direction.DESC, "name"));
    }
    
    @Override
    public List<Teacher> findExcludTeachersByStuId(String stuId)
    {
        return teacherDao.findTeachersByExcludStuIds(stuId);
    }
    
}
