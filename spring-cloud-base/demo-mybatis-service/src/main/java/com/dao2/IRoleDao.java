package com.dao2;

import com.bean.Role;

public interface IRoleDao
{
    void save(Role role);
    
    Role findRoleById(String roleId);
    
}
