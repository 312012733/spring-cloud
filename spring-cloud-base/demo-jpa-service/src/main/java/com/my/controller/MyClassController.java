package com.my.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.bean.MyClass;
import com.my.service.IMyClassService;
import com.my.vo.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "班级")
@RestController
public class MyClassController
{
    @Autowired
    private IMyClassService myClassService;
    
    @ApiOperation(value = "查询所有班级", responseContainer = "List", response = MyClass.class)
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/myClass/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> findClasses() throws IOException
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
