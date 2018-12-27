package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bean.StudentIdCard;

//@Repository
public interface IStudentIdCardRepository extends JpaRepository<StudentIdCard, String>
{
    
}
