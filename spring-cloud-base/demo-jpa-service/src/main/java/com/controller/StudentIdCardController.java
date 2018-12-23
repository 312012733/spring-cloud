package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.IStudentService;
import com.vo.ErrorHandler;

//@Controller
@RestController
public class StudentIdCardController
{
    @Autowired
    private IStudentService stuService;
    
    @RequestMapping(value = "/idCard/{stuId}/binding", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> bindIdCard(@PathVariable String stuId) throws IOException
    {
        
        try
        {
            stuService.bindStuIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<>(new ErrorHandler("bindIdCard error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(value = "/idCard/{stuId}/unBinding", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> unBinding(@PathVariable String stuId) throws IOException
    {
        
        try
        {
            stuService.unBindStuIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<>(new ErrorHandler("unBindStuIdcard error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
