package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bean.MyClass;
import com.bean.Student;
import com.service.IMyClassService;
import com.vo.ErrorHandler;
import com.vo.Page;
import com.vo.StudentDTO;

//@Controller
@RestController
public class MyClassController
{
    @Autowired
    private IMyClassService myClassService;
    
    @RequestMapping(value = "/myClass/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> findClasses(StudentDTO stuDTO, Page<Student> page, HttpServletResponse response)
            throws IOException
    {
        
        try
        {
            List<MyClass> classList = myClassService.findMyClasses();
            return new ResponseEntity<>(classList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<>(new ErrorHandler("findClasses error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
