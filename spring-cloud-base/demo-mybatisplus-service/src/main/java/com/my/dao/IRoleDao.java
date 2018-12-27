package com.my.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.Role;

@Mapper
public interface IRoleDao extends BaseMapper<Role>
{
    void save(Role role);
    
    Role findRoleById(String roleId);
    
}
