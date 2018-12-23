package com.main;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bean2.User;

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
public class Test2
{
    
    public static void main(String[] args) throws Exception
    {
        String sql = "SELECT * from user u where 1 = 1 and u.username = #{username} and u.password = #{password}";
        
        User params = new User(null, "amdin", "1234");
        
        // TODO 完成 动态参数 的替换 ， 最后结果 是true
        
        sql = buildSql(sql, params);
        
        String reslt = "SELECT * from user u where 1 = 1 and u.username = 'amdin' and u.password = '1234'";
        
        System.out.println(sql.equals(reslt));
        
    }
    
    private static <T> String buildSql(String sql, T condition) throws Exception
    {
        Map<String, Object> dynParamsMap = buildDynParamMap(sql, condition);
        
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
    
    private static <T> Map<String, Object> buildDynParamMap(String sql, T condition) throws Exception
    {
        Map<String, Object> dynParamsMap = new HashMap<>();
        
        String numReg = "#\\{([^\\{\\}]+)\\}";
        
        Pattern p = Pattern.compile(numReg);
        
        Matcher m = p.matcher(sql);
        
        while (m.find())
        {
            String fileName = m.group(1);
            
            PropertyDescriptor pd = new PropertyDescriptor(fileName, condition.getClass());
            
            Object value = pd.getReadMethod().invoke(condition);
            
            dynParamsMap.put(m.group(0).replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}"), value);
        }
        
        return dynParamsMap;
    }
    
}
