package com.my.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.my.bean.MyClass;
import com.my.bean.Student;
import com.my.bean.StudentIdCard;
import com.my.bean.Teacher;
import com.my.form.StudentAddOrUpdateForm;
import com.my.repository.IMyClassRepository;
import com.my.repository.IStudentIdCardRepository;
import com.my.repository.IStudentRepository;
import com.my.repository.ITeacherRepository;
import com.my.service.IStudentService;
import com.my.vo.StudentDTO;

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
    
    // @Autowired
    // private HibernateUtils hibernateUtils;
    
    static class StudentSpecification implements Specification<Student>
    {
        private static final long serialVersionUID = -1001469100819522717L;
        
        private Student stuCondition;
        
        StudentSpecification(Student stuCondition)
        {
            Assert.notNull(stuCondition, "stuCondition must not be null!");
            this.stuCondition = stuCondition;
        }
        
        @Override
        public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
        {
            List<Predicate> predicates = Lists.newArrayList();
            
            if (!StringUtils.isEmpty(stuCondition.getName()))
            {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + stuCondition.getName() + "%"));
            }
            
            if (null != stuCondition.getAge())
            {
                predicates.add(criteriaBuilder.equal(root.get("age"), stuCondition.getAge()));
            }
            
            if (null != stuCondition.getGender())
            {
                predicates.add(criteriaBuilder.equal(root.get("gender"), stuCondition.getGender()));
            }
            
            if (null != stuCondition.getMyClass() && !StringUtils.isEmpty(stuCondition.getMyClass().getName()))
            {
                predicates.add(criteriaBuilder.like(root.get("myClass").get("name"),
                        "%" + stuCondition.getMyClass().getName() + "%"));
            }
            
            return predicates.isEmpty() ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(Iterables.toArray(predicates, Predicate.class));
        }
        
    }
    
    @Override
    public Page<Student> findStudentsByPage(Pageable pageable, Student condition)
    {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "teachers")
                .withMatcher("name", GenericPropertyMatchers.contains())
                .withMatcher("myClass.name", GenericPropertyMatchers.contains());
        
        Example<Student> stuExample = Example.of(condition, matcher);
        
        Page<Student> pageResult = stuRepository.findAll(stuExample, null, pageable);
        
        // Page<Student> pageResult = stuRepository.findAll(new
        // StudentSpecification(condition), pageable);
        
        return pageResult;
    }
    
    @Transactional
    @Override
    public void addStudent(StudentAddOrUpdateForm stuForm)
    {
        Student stu = new Student();
        
        stu.setId(UUID.randomUUID().toString());
        stu.setCreateTime(System.currentTimeMillis());
        stu.setLastModifyTime(stu.getCreateTime());
        
        stu.setAge(stuForm.getAge());
        stu.setName(stuForm.getName());
        stu.setGender(stuForm.getGender());
        
        // 处理班级的关系
        if (!StringUtils.isEmpty(stuForm.getMyClassId()))
        {
            MyClass myClass = classRepository.findById(stuForm.getMyClassId()).orElse(null);
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuForm.getMyClassId());
            }
            
            stu.setMyClass(myClass);
        }
        
        // 处理老师的关系
        if (null != stuForm.getTeacherIds() && !stuForm.getTeacherIds().isEmpty())
        {
            List<Teacher> teachers = teacherRepository.findAllById(stuForm.getTeacherIds());
            
            if (stuForm.getTeacherIds().size() != teachers.size())
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
    public void updateStudent(StudentAddOrUpdateForm stuForm)
    {
        String stuId = stuForm.getId();
        
        Student dbStu = stuRepository.findById(stuId).orElse(null);
        
        if (null == dbStu)
        {
            throw new SecurityException("update stu error. stuId is not found. stuId:" + stuId);
        }
        
        dbStu.setAge(stuForm.getAge());
        dbStu.setName(stuForm.getName());
        dbStu.setLastModifyTime(System.currentTimeMillis());
        
        // 处理班級的关系
        
        MyClass myClass = null;
        
        if (!StringUtils.isEmpty(stuForm.getMyClassId()))
        {
            myClass = classRepository.findById(stuForm.getMyClassId()).orElse(null);
            
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuForm.getMyClassId());
            }
            
            dbStu.setMyClass(myClass);
        }
        else
        {
            dbStu.setMyClass(null);
        }
        
        // 处理老师的关系
        if (null != stuForm.getTeacherIds() && !stuForm.getTeacherIds().isEmpty())
        {
            List<Teacher> teachers = teacherRepository.findAllById(stuForm.getTeacherIds());
            
            if (stuForm.getTeacherIds().size() != teachers.size())
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
    
    // @Override
    // public Page<Student> findStudentsByPage(Pageable pageable, Student
    // condition)
    // {
    // Map<String, Object> conditionMap = new HashMap<>();
    //
    // String conditionSql = buildPageCondition(condition, conditionMap);
    //
    // String listSql = "SELECT * from t_student s " + "where 1 =1 " +
    // conditionSql + "order by s.create_time desc";
    // String countSql = "SELECT count(*) from t_student s where 1 = 1 " +
    // conditionSql;
    //
    // Page<Student> stuPage = hibernateUtils.findByPage(listSql, countSql,
    // pageable, conditionMap, Student.class);
    //
    // return stuPage;
    // }
    //
    // private String buildPageCondition(Student condition, Map<String, Object>
    // conditionMap)
    // {
    // StringBuilder conditionSql = new StringBuilder();
    //
    // if (null == condition)
    // {
    // return "";
    // }
    //
    // if (null != condition.getName() && condition.getName().length() > 0)
    // {
    // conditionSql.append("and s.student_name like :name ");
    // conditionMap.put("name", "%" + condition.getName() + "%");
    // }
    //
    // if (null != condition.getAge())
    // {
    // conditionSql.append("and s.age = :age ");
    // conditionMap.put("age", condition.getAge());
    //
    // }
    //
    // if (null != condition.getGender())
    // {
    // conditionSql.append("and s.gender = :gender ");
    // conditionMap.put("gender", condition.getGender());
    // }
    //
    // return conditionSql.toString();
    // }
    
}
