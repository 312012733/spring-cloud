package com.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;

public class AuthFilter implements Filter
{
    // 登录相关的请求。（登录页面和 登录请求）
    private List<String> uriLoginList;
    
    // 白名单， 不需要鉴权
    private List<String> uriWhiteList;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        String UriLloginJsonAryStr = filterConfig.getInitParameter("URI_LOGIN_LIST");
        String UriWhiteJsonAryStr = filterConfig.getInitParameter("URI_WHITE_LIST");
        
        uriLoginList = JSONArray.parseArray(UriLloginJsonAryStr, String.class);
        uriWhiteList = JSONArray.parseArray(UriWhiteJsonAryStr, String.class);
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        // if (true)
        // {
        // fc.doFilter(req, resp);
        // return;
        // }
        
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        
        String uri = getRequestURI(httpReq);
        
        // System.out.println("uri:===="+uri);
        // System.out.println("getServletPath:----"+httpReq.getServletPath());
        // System.out.println("getPathInfo:----"+httpReq.getPathInfo());
        
        // 判定请求是否需要鉴权
        if (!checkWhiteList(uri))
        {
            if (!hasLogin(httpReq, httpResp))
            {
                // 返回错误信息。http的状态码(403 Forbidden 服务器已经理解请求，但是拒绝执行它。)
                httpResp.sendError(403, "auth error.");
                return;
            }
        }
        else
        {
            // 1.如果 是 登录相关 请求
            // 2.该用户已登录
            // 3.直接跳转主页面
            if (isLoginRequest(uri) && hasLogin(httpReq, httpResp))
            {
                httpResp.sendRedirect(httpReq.getContextPath() + "/" + "page/main.html");
                return;
            }
        }
        
        fc.doFilter(req, resp);
    }
    
    private boolean isLoginRequest(String uri) throws IOException
    {
        
        return checkUriList(uri, uriLoginList);
    }
    
    private boolean checkWhiteList(String uri)
    {
        return checkUriList(uri, uriWhiteList);
    }
    
    private boolean checkUriList(String uri, List<String> uriList)
    {
        for (String whiteUri : uriList)
        {
            String whiteUriReg = whiteUri.replaceAll("\\*", ".+");// 把命名规则
                                                                  // 转换成正则表达式
            
            if (uri.matches(whiteUriReg))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasLogin(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException
    {
        // 获取session
        HttpSession session = httpReq.getSession(true);
        
        // 判断session 中 是否有用户信息
        Object user = session.getAttribute(session.getId());
        
        return null != user;
    }
    
    private String getRequestURI(HttpServletRequest httpReq)
    {
        String uri = httpReq.getRequestURI();// 获取请求uri 例如：/test/login.html
        String contentPath = httpReq.getContextPath(); // 项目路径 例如：/test
        
        return uri.substring(contentPath.length());
    }
    
    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }
    
}
