package com.service;

import com.bean.StudentIdCard;

public interface IStuIdCardService
{
    StudentIdCard findIdCardByStuId(String stuId);
    
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(String idCardId);
    
    void batDelete(String[] ids);
    
}
