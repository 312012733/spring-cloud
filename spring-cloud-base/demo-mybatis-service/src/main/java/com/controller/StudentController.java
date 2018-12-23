package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bean.Student;
import com.config.ConfigBean;
import com.service.IStudentService;
import com.vo.ErrorHandler;
//import com.vo.Page;
import com.vo.StudentDTO;

//@Controller
@RestController
public class StudentController
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private ConfigBean configBean;
    
    @Autowired
    private IStudentService stuService;
    
    @RequestMapping(value = "/student/page", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> findByPage(StudentDTO stuDTO, @PageableDefault(page = 0, size = 5, direction = Direction.DESC, sort = "createTime") Pageable pageable, HttpServletResponse response)
            throws IOException
    {
        
        try
        {
            LOG.info("============StudentController.findByPage============================configBean:"
                    + JSONObject.toJSONString(configBean, true));
            
            Page<Student> pageResult = stuService.findStudentsByPage(pageable, stuDTO.buildStuCondition());
            
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
