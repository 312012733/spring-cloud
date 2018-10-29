package com.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bean.Student;
import com.vo.Page;

@FeignClient(name = "temp-service")
public interface FeignApi
{
    @RequestMapping(value = "/temp/student/page", method = RequestMethod.GET)
    Page<Student> findByPage(@RequestParam("curPage") int curPage);
}
