package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.MyClass;
import com.dao.IMyClassDao;
import com.service.IMyClassService;

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
