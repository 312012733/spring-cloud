package com.service.impl;

import org.springframework.stereotype.Service;

import com.bean.StudentIdCard;
import com.service.IStuIdCardService;

@Service
public class StuIdCardServiceImpl implements IStuIdCardService
{

    @Override
    public StudentIdCard findIdCardByStuId(String stuId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(StudentIdCard studentIdCard)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(StudentIdCard studentIdCard)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(String idCardId)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void batDelete(String[] ids)
    {
        // TODO Auto-generated method stub
        
    }
    // @Autowired
    // private IStudentIdCardRepository idCardDao;
    
    // @Override
    // public void save(StudentIdCard studentIdCard)
    // {
    // idCardDao.save(studentIdCard);
    // }
    //
    // @Override
    // public void update(StudentIdCard studentIdCard)
    // {
    // idCardDao.save(studentIdCard);
    // }
    //
    // @Override
    // public void delete(StudentIdCard studentIdCard)
    // {
    // idCardDao.delete(studentIdCard);
    // }
    
}
