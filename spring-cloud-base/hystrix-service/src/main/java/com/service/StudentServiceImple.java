package com.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.bean.Student;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vo.Page;
import com.vo.StudentDTO;

@Service
public class StudentServiceImple implements IStudentService
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImple.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    @HystrixCommand(fallbackMethod = "findStudentsByPageError")
    @Override
    public Page<Student> findStudentsByPage(Page<Student> page, Student condition)
    {
        String url = "http://temp-service/temp/student/page?curPage= {curPage}&pageSize= {pageSize}";
        
        Map<String, Object> param = new HashMap<>();
        param.put("curPage", page.getCurPage());
        param.put("pageSize", page.getPageSize());
        
        LOG.info("===========StudentServiceImple.findStudentsByPage============url:{}", url);
        
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;
        
        ResponseEntity<Page> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Page.class, param);
        
        LOG.info("===========StudentServiceImple.findStudentsByPage============result:\n{}",
                JSONObject.toJSONString(result, true));
        return result.getBody();
    }
    
    public Page<Student> findStudentsByPageError(Page<Student> page, Student condition)
    {
        LOG.info("===========StudentServiceImple.findStudentsByPageError============page:\n{}",
                JSONObject.toJSONString(page, true));
        return page;
    }
    
    @Override
    public void addStudent(StudentDTO stuDTO)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public StudentDTO findStudentById(String stuId)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void updateStudent(StudentDTO stuDTO)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void delStudent(String stuId)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void batchDelStudents(String[] ids)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void bindStuIdcard(String stuId)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void unBindStuIdcard(String stuId)
    {
        // TODO Auto-generated method stub
        
    }
}
