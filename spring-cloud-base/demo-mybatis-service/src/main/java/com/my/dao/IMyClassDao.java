package com.my.dao;

import java.util.List;

import com.my.bean.MyClass;

public interface IMyClassDao
{
    List<MyClass> findMyClasses();
    
    MyClass findMyClassById(String classId);
}
