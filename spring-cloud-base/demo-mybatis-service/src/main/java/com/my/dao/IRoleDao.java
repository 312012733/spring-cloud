package com.my.dao;

import com.my.bean.Role;

public interface IRoleDao
{
    void save(Role role);
    
    Role findRoleById(String roleId);
    
}
