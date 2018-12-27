package com.my.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.bean.MyClass;
import com.my.dao.IMyClassDao;
import com.my.service.IMyClassService;

@Service
public class MyClassServiceImpl implements IMyClassService
{
    @Autowired
    private IMyClassDao classDao;
    
    @Override
    public List<MyClass> findMyClasses()
    {
        return classDao.findMyClasses();
    }
    
}
