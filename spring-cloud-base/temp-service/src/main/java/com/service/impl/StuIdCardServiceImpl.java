package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.StudentIdCard;
import com.dao.IStudentIdCardDao;
import com.service.IStuIdCardService;

@Service
public class StuIdCardServiceImpl implements IStuIdCardService
{
    @Autowired
    private IStudentIdCardDao idCardDao;
    
    @Override
    public StudentIdCard findIdCardByStuId(String stuId)
    {
        return idCardDao.findIdCardByStuId(stuId);
    }
    
    @Override
    public void save(StudentIdCard studentIdCard)
    {
        
        idCardDao.save(studentIdCard);
    }
    
    @Override
    public void update(StudentIdCard studentIdCard)
    {
        idCardDao.update(studentIdCard);
    }
    
    @Override
    public void delete(String idCardId)
    {
        idCardDao.delete(idCardId);
    }
    
    @Override
    public void batDelete(String[] ids)
    {
        idCardDao.batDelete(ids);
    }
    
}
