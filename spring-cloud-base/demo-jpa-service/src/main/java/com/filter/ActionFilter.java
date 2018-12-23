package com.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.utils.ThreadLocalUtils;

public class ActionFilter implements Filter
{
    
    private List<String> staticResourceList;
    
    private Map<String, ActionMapping> actionMappingMap;
    
    @SuppressWarnings("unused")
    private static class ActionMapping
    {
        String name;// uri
        
        String className;
        
        String methodName;
        
        public ActionMapping(String name, String className, String methodName)
        {
            this.name = name;
            this.className = className;
            this.methodName = methodName;
        }
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        staticResourceList = new ArrayList<>();
        
        staticResourceList.add("*.js");
        staticResourceList.add("*.css");
        staticResourceList.add("*.html");
        staticResourceList.add("*.jpg");
        staticResourceList.add("*.png");
        staticResourceList.add("*.xml");
        
        /*
         * /students class "com.my.StudentAction" method listByPage()
         * /del/student class "com.my.StudentAction" method del()
         * /batDel/student class "com.my.StudentAction" method batchDel()
         * /update/student class "com.my.StudentAction" method update()
         * /add/student class "com.my.StudentAction" method add()
         */
        
        actionMappingMap = new HashMap<>();
        
        actionMappingMap.put("/students", new ActionMapping("/students", "com.action.StudentAction", "listByPage"));
        actionMappingMap.put("/add/student", new ActionMapping("/add/student", "com.action.StudentAction", "add"));
        
    }
    
    @Autowired(required = false)
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        
        String uri = getRequestURI(httpReq);
        
        // System.out.println("uri:===="+uri);
        // System.out.println("getServletPath:----"+httpReq.getServletPath());
        // System.out.println("getPathInfo:----"+httpReq.getPathInfo());
        
        if (checkUriIsStaticeResrouce(uri))
        {
            fc.doFilter(req, resp);
            return;
        }
        
        ActionMapping actionMapping = actionMappingMap.get(uri);
        
        if (null == actionMapping)
        {
            fc.doFilter(req, resp);
            return;
        }
        
        // 反射执行action的方法
        try
        {
            ThreadLocalUtils.put(ThreadLocalUtils.HTTP_REQUEST, httpReq);
            ThreadLocalUtils.put(ThreadLocalUtils.HTTP_RESPONSE, httpResp);
            
            Class<?> actionClass = Class.forName(actionMapping.className);
            Object actionInstance = actionClass.newInstance();
            Method actionMethod = actionClass.getMethod(actionMapping.methodName);
            
            actionMethod.invoke(actionInstance);
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private String getRequestURI(HttpServletRequest httpReq)
    {
        String uri = httpReq.getRequestURI();// 获取请求uri 例如：/test/login.html;xxxx
        String contentPath = httpReq.getContextPath(); // 项目路径 例如：/test
        
        return uri.substring(contentPath.length());
    }
    
    private boolean checkUriIsStaticeResrouce(String uri)
    {
        for (String staticResource : staticResourceList)
        {
            String whiteUriReg = staticResource.replaceAll("\\*", ".+");// 把命名规则
            // 转换成正则表达式
            
            if (uri.matches(whiteUriReg))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }
    
}
