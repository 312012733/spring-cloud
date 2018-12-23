package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bean.MyClass;
import com.repository.IMyClassRepository;
import com.service.IMyClassService;

@Service
public class MyClassServiceImpl implements IMyClassService
{
    @Autowired
    private IMyClassRepository classDao;
    
    @Override
    public List<MyClass> findMyClasses()
    {
        return classDao.findAll(new Sort(Direction.DESC, "name"));
    }
    
}
