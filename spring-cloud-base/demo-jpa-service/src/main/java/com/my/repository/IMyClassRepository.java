package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bean.MyClass;

//@Repository
public interface IMyClassRepository extends JpaRepository<MyClass, String>
{
}
