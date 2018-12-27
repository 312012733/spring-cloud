package com.my.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.StudentIdCard;

@Mapper
public interface IStudentIdCardDao extends BaseMapper<StudentIdCard>
{
    
    StudentIdCard findIdCardByStuId(String stuId);
    
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(String idCardId);
    
    void batDelete(String[] ids);
    
}
