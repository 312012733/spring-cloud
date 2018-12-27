package com.my.repository;

import com.my.bean.Student;
import com.my.repository.support.ICustomRepository;

//@Repository
public interface IStudentRepository extends ICustomRepository<Student, String>
// extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student>
{
    
}
