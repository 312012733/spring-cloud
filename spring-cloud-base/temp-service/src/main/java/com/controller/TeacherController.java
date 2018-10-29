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

import com.bean.Student;
import com.bean.Teacher;
import com.service.ITeacherService;
import com.vo.ErrorHandler;
import com.vo.Page;
import com.vo.StudentDTO;

//@Controller
@RestController
public class TeacherController
{
    @Autowired
    private ITeacherService teacherService;
    
    @RequestMapping(value = "/teacher/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> findClasses(StudentDTO stuDTO, Page<Student> page, HttpServletResponse response)
            throws IOException
    {
        
        try
        {
            List<Teacher> teacherList = teacherService.findTeachers();
            return new ResponseEntity<>(teacherList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<>(new ErrorHandler("findClasses error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
