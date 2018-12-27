package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bean.Role;

//@Repository
public interface IRoleRepository extends JpaRepository<Role, String>
{
}
