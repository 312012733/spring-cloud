package com.my.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.config.ConfigBean;
import com.my.vo.ErrorHandler;

@Controller
@EnableConfigurationProperties(ConfigBean.class)
public class UploadAndDownloadController
{
    private static final Logger LOG = LoggerFactory.getLogger(UploadAndDownloadController.class);
    
    @Autowired
    private ConfigBean configBean;
    
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> upload(@RequestParam String stuName, MultipartHttpServletRequest requst,
            HttpServletResponse resp)
    {
        try
        {
            Iterator<String> formFileNames = requst.getFileNames();
            
            File uploadDir = ResourceUtils.getFile(configBean.getUploadDir());
            
            if (!uploadDir.exists())
            {
                uploadDir.mkdirs();
            }
            else if (!uploadDir.isDirectory())
            {
                throw new SecurityException("upload dir is error. " + uploadDir.getCanonicalPath());
            }
            
            // TODO 验证目录
            LOG.info("【uploadDir:】" + uploadDir.getCanonicalPath());
            
            while (formFileNames.hasNext())
            {
                String formFileName = formFileNames.next();
                
                List<MultipartFile> files = requst.getFiles(formFileName);
                
                for (MultipartFile file : files)
                {
                    String newFile = uploadDir.getCanonicalPath() + File.separator + file.getOriginalFilename();
                    file.transferTo(new File(newFile));
                }
            }
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("upload error. " + e.getMessage(), e);
            
            return new ResponseEntity<Object>(new ErrorHandler("upload error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/{fileName}/download", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> download(@PathVariable String fileName,
            @RequestParam(defaultValue = "false") boolean attarchment, HttpServletResponse resp, HttpServletRequest req)
    {
        try
        {
            // InputStream in =
            // req.getServletContext().getResourceAsStream("upload" +
            // File.separator + fileName);
            
            File uploadDir = ResourceUtils.getFile(configBean.getUploadDir());
            
            File downloadFile = new File(uploadDir, fileName);
            
            LOG.info("【uploadDir:】" + uploadDir);
            
            if (!downloadFile.exists())
            {
                throw new SecurityException(fileName + " is  not found.");
            }
            
            InputStream in = new FileInputStream(downloadFile);
            
            byte[] bytes = FileCopyUtils.copyToByteArray(in);
            
            // responseEntity.setContent(bytes);
            // resp.setHeader("Content-Disposition", "attarchment;filename=" +
            // fileName);
            // resp.getOutputStream().write(bytes);
            
            MultiValueMap<String, String> headers = null;
            
            if (attarchment)
            {
                headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attarchment;filename=" + fileName);
                
            }
            
            return new ResponseEntity<Object>(bytes, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("download error. " + e.getMessage(), e);
            
            return new ResponseEntity<Object>(new ErrorHandler("download error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
