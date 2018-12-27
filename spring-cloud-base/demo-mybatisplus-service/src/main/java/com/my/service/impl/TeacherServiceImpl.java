package com.my.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.my.bean.Teacher;
import com.my.dao.ITeacherDao;
import com.my.service.ITeacherService;

@Service
public class TeacherServiceImpl extends ServiceImpl<ITeacherDao, Teacher> implements ITeacherService
{
    // @Autowired
    // private ITeacherDao teacherDao;
    
    @Override
    public List<Teacher> findTeachers()
    {
        return super.baseMapper.findTeachers();
    }
    
    @Override
    public List<Teacher> findExcludTeachersByStuId(String stuId)
    {
        return super.baseMapper.findTeachersByExcludStuIds(stuId);
    }
    
}
