package com.my.controller;

import java.io.IOException;

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

import com.my.bean.Student;
import com.my.form.StudentAddOrUpdateForm;
import com.my.form.StudentConditonForm;
import com.my.service.IStudentService;
import com.my.vo.ErrorHandler;
import com.my.vo.StudentDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "学生")
@RestController
public class StudentController
{
    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private IStudentService stuService;
    
    @ApiOperation(value = "查询学生分页列表", response = org.springframework.data.domain.Page.class)
    @ApiImplicitParams(value =
    { @ApiImplicitParam(value = "当前第几页（默认：重0开始）", name = "pageNumber", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(value = "每页显示多少条（默认：5）", name = "pageSize", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(value = "名字", name = "name", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(value = "年龄", name = "age", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(value = "性别", name = "gender", dataType = "Boolean", paramType = "query", required = false),
            @ApiImplicitParam(value = "班级名字", name = "myClassName", dataType = "String", paramType = "query", required = false) })
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/student/page", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> findByPage(StudentConditonForm stuConditonForm,
            @PageableDefault(page = 0, size = 5, direction = Direction.DESC, sort = "createTime") Pageable pageable)
            throws IOException
    {
        try
        {
            Student condition = stuConditonForm.buildStuCondition();
            Page<Student> pageResult = stuService.findStudentsByPage(pageable, condition);
            
            return new ResponseEntity<>(pageResult, HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("find stu By Page error.", e);
            
            return new ResponseEntity<>(new ErrorHandler("find stu By Page error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @ApiOperation(value = "添加学生")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/student", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> add(@RequestBody StudentAddOrUpdateForm stuForm) throws IOException
    {
        
        try
        {
            stuService.addStudent(stuForm);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("add stu error.", e);
            
            return new ResponseEntity<>(new ErrorHandler("add stu error. " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @ApiOperation(value = "修改学生")
    @ApiImplicitParam(value = "学生ID", name = "stuId", dataType = "String", paramType = "path")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> update(@PathVariable String stuId, @RequestBody StudentAddOrUpdateForm stuForm)
            throws IOException
    {
        try
        {
            stuForm.setId(stuId);
            stuService.updateStudent(stuForm);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("update stu error. stuId is " + stuId, e);
            
            return new ResponseEntity<>(new ErrorHandler("update stu error. stuId is " + stuId + "  " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @ApiOperation(value = "通过学生ID查询学生", response = StudentDTO.class)
    @ApiImplicitParam(value = "学生ID", name = "stuId", dataType = "String", paramType = "path")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
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
    
    @ApiOperation(value = "通过学生ID删除学生")
    @ApiImplicitParam(value = "学生ID", name = "stuId", dataType = "String", paramType = "path")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
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
    
    @ApiOperation(value = "通过学生ID列表批量删除学生")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @RequestMapping(value = "/students", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
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
