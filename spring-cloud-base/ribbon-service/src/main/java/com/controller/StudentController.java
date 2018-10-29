package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.bean.Student;
import com.vo.ErrorHandler;
import com.vo.Page;
import com.vo.StudentDTO;

//@Controller
@Controller
@RestController
public class StudentController
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/student/page", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> findByPage(StudentDTO stuDTO, Page<Student> page, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        
        try
        {
            String url = "http://temp-service/temp/student/page?" + request.getQueryString();
            // Map<String, Object> param = new HashMap<>();
            // param.put("curPage", page.getCurPage());
            // param.put("pageSize", page.getPageSize());
            LOG.info("===========StudentController.findByPage============url:{}", url);
            
            HttpEntity<?> requestEntity = HttpEntity.EMPTY;
            
            ResponseEntity<Page> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Page.class);
            
            LOG.info("===========StudentController.findByPage============result:\n{}",
                    JSONObject.toJSONString(result, true));
            
            return new ResponseEntity<>(result.getBody(), HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.OK);
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
