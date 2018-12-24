package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.User;
import com.repository.IUserRepository;
import com.service.IUserService;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private IUserRepository userDao;
    
    @Override
    public User findByUsernameAndPassword(String username, String password) throws Exception
    {
        return userDao.findByUsernameAndPassword(username, password);
    }
    
}
