package com.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils
{
    public static final String SQL_SESSION = "sql_session";
    public static final String JDBC_CONNECTION = "jdbc_connection";
    public static final String HTTP_REQUEST = "http_request";
    public static final String HTTP_RESPONSE = "http_response";
    
    private static final ThreadLocal<Map<String, Object>> MY_THREAD_LOCAL = new ThreadLocal<>();
    
    public static void put(String key, Object value)
    {
        Map<String, Object> map = getMap();
        
        map.put(key, value);
    }
    
    public static Object get(String key)
    {
        Map<String, Object> map = getMap();
        
        return map.get(key);
    }
    
    public static Object remove(String key)
    {
        Map<String, Object> map = getMap();
        
        return map.remove(key);
    }
    
    private static Map<String, Object> getMap()
    {
        Map<String, Object> map = MY_THREAD_LOCAL.get();
        
        if (null == map)
        {
            map = new HashMap<>();
            MY_THREAD_LOCAL.set(map);
        }
        
        return map;
    }
}
