package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.MyClass;

//@Repository
public interface IMyClassRepository extends JpaRepository<MyClass, String>
{
}
