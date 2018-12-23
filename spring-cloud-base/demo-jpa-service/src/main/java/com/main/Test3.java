// package com.main;
//
// import java.beans.PropertyDescriptor;
// import java.util.LinkedHashMap;
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
// public class Test3
// {
//
// public static void main(String[] args) throws Exception
// {
// String sql = "SELECT * from user u where 1 = 1 and u.username =
// #{user.username} and u.password = #{user.password}";
//
// MyClass myClass = new MyClass();
// myClass.setUser(new User(null, "admin", "1234"));
//
// // Map<String, Object> param = new HashMap<>();
// // param.put("username", "admin");
// // param.put("password", "1234");
//
// sql = buildSql(sql, myClass);
//
// String reslt = "SELECT * from user u where 1 = 1 and u.username = 'admin' and
// u.password = '1234'";
// System.out.println(sql.equals(reslt));
//
// }
//
// private static <T> String buildSql(String sql, T param) throws Exception
// {
// Map<String, Object> dynParamsMap = buildDynParamMap(sql, param);
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
// private static <T> Map<String, Object> buildDynParamMap(String sql, T param)
// throws Exception
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
// Object value = param;
// String dynKey = m.group(1);// f0 | f0.f1.f2.f3
//
// String[] dynKeyAry = dynKey.split("\\.");
//
// String fieldName = dynKeyAry[0];
//
// if (dynKeyAry.length > 1)// f0.f1.f2.f3
// {
//
// if (value instanceof Map)
// {
// throw new SecurityException("buildDynParamMap error . map is not support
// 'key.f1.f2.f3' ");
// }
// else
// {
//
// for (int i = 0; i < dynKeyAry.length; i++)
// {
// PropertyDescriptor pd = new PropertyDescriptor(dynKeyAry[i],
// value.getClass());
//
// value = pd.getReadMethod().invoke(value);
// }
// }
// }
// else // f0
// {
// if (value instanceof Map)
// {
// value = ((Map) param).get(fieldName);
// }
// else
// {
// PropertyDescriptor pd = new PropertyDescriptor(fieldName, value.getClass());
//
// value = pd.getReadMethod().invoke(param);
// }
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
