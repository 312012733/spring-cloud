package com.my.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.my.bean.User;
import com.my.dao.IUserDao;
import com.my.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, User> implements IUserService
{
    
    @Override
    public User findByUsernameAndPassword(String userName, String password) throws Exception
    {
        // return userDao.findUserByUsernameAndPassword(userName, password);
        return this.baseMapper.findUserByUsernameAndPassword(userName, password);
    }
    
}
