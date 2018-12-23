package com.dao;

import java.util.List;

import com.bean.MyClass;

public interface IMyClassDao
{
    List<MyClass> findMyClasses();
    
    // MyClass findMyClassById(String temp,String classId);
}
