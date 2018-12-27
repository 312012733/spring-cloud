package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.MyClass;

@Mapper
public interface IMyClassDao extends BaseMapper<MyClass>
{
    List<MyClass> findMyClasses();
    
    MyClass findMyClassById(String classId);
}
