package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.StudentIdCard;

//@Repository
public interface IStudentIdCardRepository extends JpaRepository<StudentIdCard, String>
{
    
}
