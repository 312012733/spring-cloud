// package com.main;
//
// import java.beans.PropertyDescriptor;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
//
// import com.bean.MyClass;
// import com.bean.User;
//
/// **
// * SELECT * from stu s where 1 = 1 and s.`name` like '%a%' and s.age = 18
// *
// * SELECT * from stu s where 1 = 1 and s.`name` like ? and s.age = ?
// *
// * List paras = new ArrayList();
// *
// * list.add("xxx"); list.add(123);
// *
// * #{)
// *
// * SELECT * from stu s where 1 = 1 and s.`name` like #{name} and s.age =
// #{age}
// *
// * Map params = new HashMap();
// *
// * params.put("age",v); params.put("name",v);
// *
// * T t;
// *
// */
// public class Test4
// {
//
// public static void main(String[] args) throws Exception
// {
// String sql = "SELECT * from user u where 1 = 1 and u.username =
// #{arg1.username} and u.password = #{arg0.user.password}";
//
// List<Object> params = new ArrayList<>();
//
// MyClass myClass = new MyClass();
// myClass.setUser(new User(null, null, "1234"));
//
// Map<String, Object> mapParam = new HashMap<>();
// mapParam.put("username", "admin");
//
// params.add(myClass);
// params.add(mapParam);
//
// sql = buildSql(sql, params);
//
// String reslt = "SELECT * from user u where 1 = 1 and u.username = 'admin' and
// u.password = '1234'";
// System.out.println(sql.equals(reslt));
//
// }
//
// private static <T> String buildSql(String sql, List<Object> params) throws
// Exception
// {
// Map<String, Object> dynParamsMap = buildDynParamMap(sql, params);
//
// for (Entry<String, Object> entry : dynParamsMap.entrySet())
// {
// Object value = entry.getValue();
//
// if (String.class.getName().equals(value.getClass().getName()))
// {
// value = "'" + value + "'";
// }
//
// sql = sql.replaceAll(entry.getKey(), value.toString());
// }
//
// System.out.println(sql);
//
// return sql;
// }
//
// @SuppressWarnings("rawtypes")
// private static <T> Map<String, Object> buildDynParamMap(String sql,
// List<Object> params) throws Exception
// {
// Map<String, Object> dynParamsMap = new LinkedHashMap<>();// 使 map key
// // 有序。（按put的添加顺序）
//
// String numReg = "#\\{([^\\{\\}]+)\\}";
//
// Pattern p = Pattern.compile(numReg);
//
// Matcher m = p.matcher(sql);
//
// while (m.find())
// {
// Object value = null;
// String dynKey = m.group(1);// argN | argN.f1.f2.f3
//
// String[] dynKeyAry = dynKey.split("\\.");
//
// int paramIndex = Integer.parseInt(dynKeyAry[0].substring(3));
//
// if (dynKeyAry.length > 1)// argN.f1.f2.f3
// {
//
// value = params.get(paramIndex);
//
// if (value instanceof Map)
// {
// value = ((Map) value).get(dynKeyAry[1]);
// }
// else
// {
// for (int i = 1; i < dynKeyAry.length; i++)
// {
// PropertyDescriptor pd = new PropertyDescriptor(dynKeyAry[i],
// value.getClass());
//
// value = pd.getReadMethod().invoke(value);
// }
// }
//
// }
// else // argN
// {
// value = params.get(paramIndex);
// }
//
// String key = m.group(0).replaceAll("\\{", "\\\\{").replaceAll("\\}",
// "\\\\}").replaceAll("\\.", "\\\\.");
// dynParamsMap.put(key, value);
// }
//
// return dynParamsMap;
// }
//
// }
