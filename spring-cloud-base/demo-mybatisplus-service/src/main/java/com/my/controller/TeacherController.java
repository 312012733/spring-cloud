package com.my.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.bean.Teacher;
import com.my.service.ITeacherService;
import com.my.vo.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "老师")
@RestController
public class TeacherController
{
    @Autowired
    private ITeacherService teacherService;
    
    @ApiOperation(value = "查询所有老师", responseContainer = "List", response = Teacher.class)
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/teacher/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> findClasses() throws IOException
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
