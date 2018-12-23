package com.controller;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bean.Role;
import com.dao2.IRoleDao;
import com.vo.ErrorHandler;

@Controller
public class RoleController
{
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    
    @Autowired
    private IRoleDao roleDao;
    
    @RequestMapping(path = "/role", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addRole(@RequestParam String stuName, MultipartHttpServletRequest requst,
            HttpServletResponse resp)
    {
        try
        {
            Iterator<String> formFileNames = requst.getFileNames();
            
            while (formFileNames.hasNext())
            {
                String formFileName = formFileNames.next();
                
                List<MultipartFile> files = requst.getFiles(formFileName);
                
                for (MultipartFile file : files)
                {
                    byte[] fileBytes = file.getBytes();
                    
                    Role role = new Role(UUID.randomUUID().toString(), stuName, fileBytes);
                    roleDao.save(role);
                    return new ResponseEntity<>(role, HttpStatus.OK);
                }
            }
            
            throw new SecurityException("head is null");
        }
        catch (Exception e)
        {
            LOG.error("upload error. " + e.getMessage(), e);
            
            return new ResponseEntity<Object>(new ErrorHandler("add role error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/{roleId}/role", method = RequestMethod.GET)
    public void getRoleById(@PathVariable String roleId, HttpServletResponse resp, HttpServletRequest req)
    {
        try
        {
            Role role = roleDao.findRoleById(roleId);
            if (null == role)
            {
                throw new SecurityException("role is not found . role id is " + roleId);
            }
            
            byte[] bytes = role.getHead();
            
            OutputStream out = resp.getOutputStream();
            
            out.write(bytes);
        }
        catch (Exception e)
        {
            LOG.error("getRoleById error. " + e.getMessage(), e);
            
        }
        
    }
    
}
