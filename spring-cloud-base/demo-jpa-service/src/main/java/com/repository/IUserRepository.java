package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.User;

//@Repository
public interface IUserRepository extends JpaRepository<User, String>
{
    User findByUsernameAndPassword(String username, String password);
}
