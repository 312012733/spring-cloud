package com.dao;

import com.bean.StudentIdCard;

public interface IStudentIdCardDao
{
    
    StudentIdCard findIdCardByStuId(String stuId);
    
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(String idCardId);
    
    void batDelete(String[] ids);
    
}
