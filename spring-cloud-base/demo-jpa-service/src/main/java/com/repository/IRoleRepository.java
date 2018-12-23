package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.Role;

//@Repository
public interface IRoleRepository extends JpaRepository<Role, String>
{
}
