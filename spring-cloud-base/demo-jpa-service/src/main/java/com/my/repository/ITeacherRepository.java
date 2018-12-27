package com.my.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.bean.Teacher;

//@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, String>
{
    @Query(value = "SELECT * from t_teacher t where t.pk_teacher_id not in ( SELECT t1.pk_teacher_id from t_teacher t1 INNER JOIN t_student_teacher st on t1.pk_teacher_id = st.pk_teacher_id where st.pk_student_id = ?1 ) order by t.teacher_name DESC ", nativeQuery = true)
    List<Teacher> findTeachersByExcludStuIds(String stuId);
    
}
