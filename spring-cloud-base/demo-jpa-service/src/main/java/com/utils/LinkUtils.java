package com.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.util.StringUtils;

public class LinkUtils
{
    public static List<Link> prepareLinks(int page, int size, HttpServletRequest request, Page<?> result,
            String pathParams)
    {
        List<Link> list = new ArrayList<>();
        if (result.hasNext())
        {
            list.add(new Link(
                    getHost(request) + request.getRequestURI() + "?page=" + (page + 1) + "&size=" + size + pathParams,
                    Link.REL_NEXT));
        }
        if (result.hasPrevious())
        {
            list.add(new Link(
                    getHost(request) + request.getRequestURI() + "?page=" + (page - 1) + "&size=" + size + pathParams,
                    Link.REL_PREVIOUS));
        }
        list.add(new Link(getHost(request) + request.getRequestURI() + "?page=" + page + "&size=" + size + pathParams,
                Link.REL_SELF));
        return list;
    }
    
    private static String getHost(HttpServletRequest request)
    {
        int port = request.getServerPort();
        String host = request.getServerName();
        String header = request.getHeader("X-Forwarded-Host");
        if (!StringUtils.isEmpty(header))
        {
            return "http://" + header;
        }
        return "http://" + host + ":" + port;
    }
}
