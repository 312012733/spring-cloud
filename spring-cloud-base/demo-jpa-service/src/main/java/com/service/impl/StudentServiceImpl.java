package com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bean.MyClass;
import com.bean.Student;
import com.bean.StudentIdCard;
import com.bean.Teacher;
import com.repository.IMyClassRepository;
import com.repository.IStudentIdCardRepository;
import com.repository.IStudentRepository;
import com.repository.ITeacherRepository;
import com.service.IStudentService;
import com.utils.HibernateUtils;
import com.vo.StudentDTO;

@Service
public class StudentServiceImpl implements IStudentService
{
    
    @Autowired
    private IStudentRepository stuRepository;
    
    @Autowired
    private IMyClassRepository classRepository;
    
    @Autowired
    private ITeacherRepository teacherRepository;
    
    @Autowired
    private IStudentIdCardRepository idCardRepository;
    
    @Autowired
    HibernateUtils hibernateUtils;
    
    @Transactional
    @Override
    public void addStudent(StudentDTO stuDTO)
    {
        Student stu = new Student();
        
        stu.setId(UUID.randomUUID().toString());
        stu.setCreateTime(System.currentTimeMillis());
        stu.setLastModifyTime(stu.getCreateTime());
        
        stu.setAge(stuDTO.getAge());
        stu.setName(stuDTO.getName());
        stu.setGender(stuDTO.getGender());
        
        // 处理班级的关系
        if (null != stuDTO.getMyClass() && !StringUtils.isEmpty(stuDTO.getMyClass().getId()))
        {
            MyClass myClass = classRepository.findById(stuDTO.getMyClass().getId()).orElse(null);
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuDTO.getMyClass().getId());
            }
            
            stu.setMyClass(myClass);
        }
        
        // 处理老师的关系
        if (null != stuDTO.getTeacherIds() && !stuDTO.getTeacherIds().isEmpty())
        {
            List<Teacher> teachers = teacherRepository.findAllById(stuDTO.getTeacherIds());
            
            if (stuDTO.getTeacherIds().size() != teachers.size())
            {
                throw new SecurityException("teacher ids is error. ");
            }
            
            stu.setTeachers(teachers);
        }
        
        stuRepository.save(stu);
    }
    
    @Override
    public StudentDTO findStudentById(String stuId)
    {
        
        Student stu = stuRepository.findById(stuId).orElse(null);
        
        if (stu == null)
        {
            return null;
        }
        
        List<Teacher> unOwerTeachers = teacherRepository.findTeachersByExcludStuIds(stuId);
        
        StudentDTO stuDTO = new StudentDTO();
        
        stuDTO.setAge(stu.getAge());
        stuDTO.setName(stu.getName());
        stuDTO.setGender(stu.getGender());
        stuDTO.setMyClass(stu.getMyClass());
        
        stuDTO.setOwerTeachers(stu.getTeachers());
        stuDTO.setUnOwerTeachers(unOwerTeachers);
        
        return stuDTO;
    }
    
    @Transactional
    @Override
    public void updateStudent(StudentDTO stuDTO)
    {
        String stuId = stuDTO.getId();
        
        Student dbStu = stuRepository.findById(stuId).orElse(null);
        
        if (null == dbStu)
        {
            throw new SecurityException("update stu error. stuId is not found. stuId:" + stuId);
        }
        
        dbStu.setAge(stuDTO.getAge());
        dbStu.setName(stuDTO.getName());
        dbStu.setLastModifyTime(System.currentTimeMillis());
        
        // 处理班級的关系
        
        MyClass myClass = null;
        
        if (null != stuDTO.getMyClass() && !StringUtils.isEmpty(stuDTO.getMyClass().getId()))
        {
            myClass = classRepository.findById(stuDTO.getMyClass().getId()).orElse(null);
            
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuDTO.getMyClass().getId());
            }
            
            dbStu.setMyClass(myClass);
        }
        else
        {
            dbStu.setMyClass(null);
        }
        
        // 处理老师的关系
        if (null != stuDTO.getTeacherIds() && !stuDTO.getTeacherIds().isEmpty())
        {
            List<Teacher> teachers = teacherRepository.findAllById(stuDTO.getTeacherIds());
            
            if (stuDTO.getTeacherIds().size() != teachers.size())
            {
                throw new SecurityException("teacher ids is error. ");
            }
            
            dbStu.setTeachers(teachers);
        }
        else
        {
            dbStu.setTeachers(null);
        }
        
        stuRepository.save(dbStu);
    }
    
    @Transactional
    @Override
    public void delStudent(String stuId)
    {
        
        Student dbStu = stuRepository.findById(stuId).orElse(null);
        
        if (null == dbStu)
        {
            throw new SecurityException("del stu error. stuId is not found. stuId:" + stuId);
        }
        
        // 解除和学生证的关系
        StudentIdCard idCard = dbStu.getStudentIdCard();
        
        if (null != idCard)
        {
            idCard.setStudent(null);
            dbStu.setStudentIdCard(null);
            
            idCardRepository.delete(idCard);
        }
        
        // 解除与班级的关系
        MyClass myClass = dbStu.getMyClass();
        if (null != myClass)
        {
            myClass.getStudents().remove(dbStu);
        }
        
        // 处理老师的关系
        List<Teacher> teachers = dbStu.getTeachers();
        
        for (Teacher teacher : teachers)
        {
            teacher.getStudents().remove(dbStu);
        }
        
        // 删除学生
        stuRepository.delete(dbStu);
    }
    
    @Transactional
    @Override
    public void batchDelStudents(String[] ids)
    {
        
        if (null == ids || ids.length <= 0)
        {
            throw new SecurityException("batch del stu error. stuId is null.");
        }
        
        for (String stuId : ids)
        {
            delStudent(stuId);
        }
    }
    
    @Override
    public Page<Student> findStudentsByPage(Pageable pageable, Student condition)
    {
        // Page<Student> stuPage = stuRepository.findAll(pageable);
        
        Map<String, Object> conditionMap = new HashMap<>();
        
        String conditionSql = buildPageCondition(condition, conditionMap);
        
        String listSql = "SELECT * from t_student s " + "where 1 =1 " + conditionSql + "order by s.create_time desc";
        String countSql = "SELECT count(*) from t_student s where 1 = 1 " + conditionSql;
        
        Page<Student> stuPage = hibernateUtils.findByPage(listSql, countSql, pageable, conditionMap, Student.class);
        
        return stuPage;
    }
    
    @Transactional
    @Override
    public void bindStuIdcard(String stuId)
    {
        Student stu = stuRepository.findById(stuId).orElse(null);
        
        // 验证
        
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        if (null != stu.getStudentIdCard())
        {
            
            throw new SecurityException("idcard is exist. ");
        }
        
        // 生成一个的学生证
        StudentIdCard studentIdCard = idCardRepository.save(buildStuIdCard());
        
        // 设置学生和学生证关系
        stu.setStudentIdCard(studentIdCard);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        stuRepository.save(stu);
    }
    
    @Transactional
    @Override
    public void unBindStuIdcard(String stuId)
    {
        Student stu = stuRepository.findById(stuId).orElse(null);
        
        // 验证
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        StudentIdCard idCard = stu.getStudentIdCard();
        
        if (null == idCard)
        {
            throw new SecurityException("idcard is not exist. ");
        }
        
        // 解除学生和学生证关系
        stu.setStudentIdCard(null);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        // 删除学生证
        idCardRepository.delete(idCard);
    }
    
    private StudentIdCard buildStuIdCard()
    {
        String id = UUID.randomUUID().toString();
        String num = System.currentTimeMillis() + "";
        
        StudentIdCard idcard = new StudentIdCard(id, num);
        
        return idcard;
    }
    
    private String buildPageCondition(Student condition, Map<String, Object> conditionMap)
    {
        StringBuilder conditionSql = new StringBuilder();
        
        if (null == condition)
        {
            return "";
        }
        
        if (null != condition.getName() && condition.getName().length() > 0)
        {
            conditionSql.append("and s.student_name like :name ");
            conditionMap.put("name", "%" + condition.getName() + "%");
        }
        
        if (null != condition.getAge())
        {
            conditionSql.append("and s.age = :age ");
            conditionMap.put("age", condition.getAge());
            
        }
        
        if (null != condition.getGender())
        {
            conditionSql.append("and s.gender = :gender ");
            conditionMap.put("gender", condition.getGender());
        }
        
        return conditionSql.toString();
    }
    
}