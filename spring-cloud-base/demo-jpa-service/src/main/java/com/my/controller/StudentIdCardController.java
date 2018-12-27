package com.my.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.service.IStudentService;
import com.my.vo.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "学生证")
@RestController
public class StudentIdCardController
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentIdCardController.class);
    
    @Autowired
    private IStudentService stuService;
    
    @ApiOperation(value = "绑定学生证")
    @ApiImplicitParam(value = "学生id", name = "stuId", dataType = "String", paramType = "path", required = true)
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/idCard/{stuId}/binding", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> bindIdCard(@PathVariable String stuId) throws IOException
    {
        
        try
        {
            stuService.bindStuIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("", e);
            
            return new ResponseEntity<>(new ErrorHandler("bindIdCard error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @ApiOperation(value = "解除绑定学生证")
    @ApiImplicitParam(value = "学生id", name = "stuId", dataType = "String", paramType = "path", required = true)
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/idCard/{stuId}/unBinding", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> unBinding(@PathVariable String stuId) throws IOException
    {
        try
        {
            stuService.unBindStuIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("", e);
            
            return new ResponseEntity<>(new ErrorHandler("unBindStuIdcard error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
