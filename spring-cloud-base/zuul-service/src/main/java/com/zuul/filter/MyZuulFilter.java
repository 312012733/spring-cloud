package com.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class MyZuulFilter extends ZuulFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulFilter.class);
    
    @Override
    public boolean shouldFilter()
    {// 这里可以写逻辑判断，是否要过滤，true,永远过滤。
        return true;
    }
    
    @Override
    public String filterType()
    {
        // pre：路由之前 。routing：路由之时。 post： 路由之后。 error：发送错误调用。
        return "pre";
    }
    
    @Override
    public int filterOrder()
    {
        return 0;
    }
    
    @Override
    public Object run() throws ZuulException
    {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        LOG.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        
//        Object accessToken = request.getParameter("token");
//        
//        if (accessToken == null)
//        {
//            LOG.warn("token is empty");
//            
//            try
//            {
//                ctx.setSendZuulResponse(false);// 不需要进行路由，也就是不会调用api服务提供者
//                ctx.setResponseStatusCode(401);
//                ctx.set("isOK", false);// 可以把一些值放到ctx中，便于后面的filter获取使用
//                // 返回内容给客户端
//                // ctx.setResponseBody("{\"result\":\"token is empty\"}");//
//                // 返回错误内容
//                
//                ctx.getResponse().getWriter().write("token is empty");
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            
//        }
//        else
//        {
//            ctx.setSendZuulResponse(true);// 会进行路由，也就是会调用api服务提供者
//            ctx.setResponseStatusCode(200);
//            ctx.set("isOK", true);// 可以把一些值放到ctx中，便于后面的filter获取使用
//        }
        
        return null;
    }
    
}
