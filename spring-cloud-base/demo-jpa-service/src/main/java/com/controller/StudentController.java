package com.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bean.Student;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.repository.IStudentRepository;
import com.service.IStudentService;
import com.vo.ErrorHandler;
import com.vo.StudentDTO;

//@Controller
@RestController
public class StudentController
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);
    // private static final Log LOG =
    // LogFactory.getLog(StudentController.class);
    // private static final Logger LOG =
    // Logger.getLogger(StudentController.class);
    
    @Autowired
    private IStudentService stuService;
    
    @Autowired
    private IStudentRepository stuRepository;
    
    // @Autowired
    // private NamedParameterJdbcTemplate jdbcTemplate;
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
    
    @RequestMapping(value = "/student/page", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> findByPage(StudentDTO stuDTO,
            @PageableDefault(page = 0, size = 5, direction = Direction.DESC, sort = "createTime") Pageable pageable)
            throws IOException
    {
        
        try
        {
            
            // Student stu = jdbcTemplate.queryForObject("select s.pk_student_id
            // as id from t_student s where s.pk_student_id = ?", new Object[] {
            // "s1" }, new BeanPropertyRowMapper<>(Student.class));
            //
            // Student stu = jdbcTemplate.queryForObject(
            // "select s.pk_student_id as id from t_student s where
            // s.pk_student_id = :id",
            // new BeanPropertySqlParameterSource(new Student("s1")), new
            // BeanPropertyRowMapper<>(Student.class));
            //
            // DataSource ds = jdbcTemplate.getJdbcTemplate().getDataSource();
            // System.out.println(stu);
            
            Student stuCondition = stuDTO.buildStuCondition();
            
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "teachers")
                    .withMatcher("name", GenericPropertyMatchers.contains())
                    .withMatcher("myClass.name", GenericPropertyMatchers.contains());
            Example<Student> stuExample = Example.of(stuCondition, matcher);
            
            Page<Student> pageResult = stuRepository.findAll(stuExample, null, pageable);
            
            // Page<Student> pageResult = stuRepository.findAll(new
            // StudentSpecification(stuCondition), pageable);
            
            // Page<Student> pageResult =
            // stuService.findStudentsByPage(pageable, stuCondition);
            
            return new ResponseEntity<>(pageResult, HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("find stu By Page error.", e);
            
            return new ResponseEntity<>(new ErrorHandler("find stu By Page error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/student", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> add(@RequestBody StudentDTO stuDTO, HttpServletResponse response) throws IOException
    {
        
        try
        {
            stuService.addStudent(stuDTO);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("add stu error.", e);
            
            return new ResponseEntity<>(new ErrorHandler("add stu error. " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> update(@PathVariable String stuId, @RequestBody StudentDTO stuDTO) throws IOException
    {
        try
        {
            stuDTO.setId(stuId);
            stuService.updateStudent(stuDTO);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("update stu error. stuId is " + stuId, e);
            
            return new ResponseEntity<>(new ErrorHandler("update stu error. stuId is " + stuId + "  " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> findStuById(@PathVariable String stuId) throws IOException
    {
        try
        {
            StudentDTO stu = stuService.findStudentById(stuId);
            
            return new ResponseEntity<>(stu, HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("findStuById error. stuId is " + stuId, e);
            
            return new ResponseEntity<>(
                    new ErrorHandler("findStuById error. stuId is " + stuId + "  " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> del(@PathVariable String stuId) throws IOException
    {
        try
        {
            stuService.delStudent(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("del stu error. stuId is " + stuId, e);
            
            return new ResponseEntity<>(new ErrorHandler("del stu error. stuId is " + stuId + "  " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/students", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> batchDelStudents(@RequestBody String[] stuIds) throws IOException
    {
        try
        {
            stuService.batchDelStudents(stuIds);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("batchDelStudents  error. ", e);
            
            return new ResponseEntity<>(new ErrorHandler("batchDelStudents  error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
