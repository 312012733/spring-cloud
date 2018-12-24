package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.User;
import com.form.UserForm;
import com.service.IUserService;
import com.utils.GeneratCheckCodeUtils;
import com.utils.GeneratCheckCodeUtils.CheckCodeCallBack;
import com.vo.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "用户")
@Controller
@ControllerAdvice
public class UserController
{
    public static final String CHECK_CODE = "CheckCode";
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private IUserService userService;
    
    @ExceptionHandler()
    @ResponseBody
    public ResponseEntity<Object> exceptionHandler(Exception e, HttpServletResponse resp)
    {
        LOG.error("==" + e.getMessage(), e);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        
        return new ResponseEntity<Object>(new ErrorHandler(e.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "用户登录")
    @RequestMapping(path = "/user/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
    @ApiResponses(value =
    { @ApiResponse(code = 400, message = "失败", response = ErrorHandler.class) })
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody UserForm userForm, HttpServletResponse resp,
            HttpServletRequest req)
    {
        try
        {
            String checkCode = userForm.getCheckCode();
            String userName = userForm.getUsername();
            String password = userForm.getPassword();
            
            // 验证参数
            // TODO 空判定。。。。。
            
            // 验证验证码
            HttpSession session = req.getSession();
            
            String checkCodeFromsession = (String) session.getAttribute(CHECK_CODE);
            
            if (!checkCode.equals(checkCodeFromsession))
            {
                // throw new SecurityException("check code is error.");
            }
            
            // 验证用户信息
            User user = userService.findByUsernameAndPassword(userName, password);
            
            if (null == user)
            {
                throw new SecurityException("username or password is error.");
            }
            
            // 登录成功。。。进行鉴权
            session.setAttribute(session.getId(), user);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("login error. " + e.getMessage(), e);
            
            return new ResponseEntity<>(new ErrorHandler("login error. " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @ApiOperation(value = "获取验证码", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/user/generatCheckCode", method = RequestMethod.GET)
    public void generatCheckCode(HttpServletResponse resp, HttpServletRequest req) throws IOException
    {
        resp.setHeader("Cache-Control", "no-store, no-cache");
        resp.setContentType(MediaType.IMAGE_JPEG_VALUE);
        
        GeneratCheckCodeUtils.generatCheckCode(resp.getOutputStream(), new CheckCodeCallBack()
        {
            @Override
            public void callBack(String checkCode)
            {
                
                LOG.debug("checkCode:" + checkCode);
                
                HttpSession session = req.getSession(true);
                
                session.setAttribute(CHECK_CODE, checkCode);
            }
        });
        
    }
    
}
