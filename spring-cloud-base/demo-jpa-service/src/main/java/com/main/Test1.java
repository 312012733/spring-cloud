package com.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SELECT * from stu s where 1 = 1 and s.`name` like '%a%' and s.age = 18
 * 
 * SELECT * from stu s where 1 = 1 and s.`name` like ? and s.age = ?
 * 
 * List paras = new ArrayList();
 * 
 * list.add("xxx"); list.add(123);
 * 
 * #{)
 * 
 * SELECT * from stu s where 1 = 1 and s.`name` like #{name} and s.age = #{age}
 * 
 * Map params = new HashMap();
 * 
 * params.put("age",v); params.put("name",v);
 * 
 * T t;
 * 
 */
public class Test1
{
    
    public static void main(String[] args)
    {
        String sql = "SELECT * from stu s where 1 = 1 and s.name = #{name123} and s.age = #{age123}";
        
        Map<String, Object> params = new HashMap<>();
        
        params.put("age123", 12);
        params.put("name123", "张三");
        params.put("xxxx", "张sdf三");
        params.put("yyyy", "张sfdsdf三");
        params.put("zzzz", "张sdfsdfds三");
        
        // TODO 完成 动态参数 的替换 ， 最后结果 是true
        
        sql = buildSql(sql, params);
        
        String reslt = "SELECT * from stu s where 1 = 1 and s.name = '张三' and s.age = 12";
        
        System.out.println(sql.equals(reslt));
        
    }
    
    private static String buildSql(String sql, Map<String, Object> params)
    {
        Map<String, Object> dynParamsMap = buildDynParamMap(sql, params);
        
        for (Entry<String, Object> entry : dynParamsMap.entrySet())
        {
            Object value = entry.getValue();
            
            if (String.class.getName().equals(value.getClass().getName()))
            {
                value = "'" + value + "'";
            }
            
            sql = sql.replaceAll(entry.getKey(), value.toString());
        }
        
        System.out.println(sql);
        
        return sql;
    }
    
    private static Map<String, Object> buildDynParamMap(String sql, Map<String, Object> params)
    {
        Map<String, Object> dynParamsMap = new HashMap<>();
        
        String numReg = "#\\{([^\\{\\}]+)\\}";
        
        Pattern p = Pattern.compile(numReg);
        
        Matcher m = p.matcher(sql);
        
        while (m.find())
        {
            dynParamsMap.put(m.group(0).replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}"), params.get(m.group(1)));
        }
        
        return dynParamsMap;
    }
    
}
