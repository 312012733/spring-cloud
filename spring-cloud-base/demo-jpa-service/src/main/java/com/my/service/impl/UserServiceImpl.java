package com.my.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.bean.User;
import com.my.repository.IUserRepository;
import com.my.service.IUserService;

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
