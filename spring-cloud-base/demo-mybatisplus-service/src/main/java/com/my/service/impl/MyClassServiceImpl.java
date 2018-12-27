package com.my.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.my.bean.MyClass;
import com.my.dao.IMyClassDao;
import com.my.service.IMyClassService;

@Service
public class MyClassServiceImpl extends ServiceImpl<IMyClassDao, MyClass> implements IMyClassService
{
    // @Autowired
    // private IMyClassDao classDao;
    
    @Override
    public List<MyClass> findMyClasses()
    {
        return super.baseMapper.findMyClasses();
    }
    
}
