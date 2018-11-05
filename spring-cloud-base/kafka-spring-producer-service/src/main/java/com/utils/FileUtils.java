package com.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils
{
    
    public static final String WEB_INFO_CLASSES = File.separator + "WEB-INF" + File.separator + "classes"
            + File.separator;
    public static final String SUFFIX_CLASS = ".class";
    
    public interface ClassHandler
    {
        void handle(Class<?> sourceClass);
    }
    
    public static void listClassFile(File classPath, String nameReg, ClassHandler classHandle) throws IOException
    {
        if (null == classPath || !classPath.isDirectory())
        {
            throw new SecurityException("classPath is error..." + classPath.getCanonicalPath());
        }
        
        // 获取该目录下 所有的 文件 和 文件夹
        File[] files = classPath.listFiles();
        
        for (File file : files)
        {
            if (file.isDirectory())
            {
                listClassFile(file, nameReg, classHandle);
            }
            else
            {
                // 获取 文件 路径
                String filePath = file.getCanonicalPath();
                
                // 先过滤是否是class文件
                if (FileUtils.isClassFile(filePath))
                {
                    // 通过 文件 绝对路径 获取 类全名
                    String className = FileUtils.buildClassName(filePath);
                    
                    // 把 类全名 和 配置中的命名规则 进行 匹配
                    if (className.matches(nameReg))
                    {
                        // 对class进行处理
                        try
                        {
                            classHandle.handle(Class.forName(className));
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
    private static String buildClassName(String filePath)
    {
        // E:\apache-tomcat-9.0.0.M26\wtpwebapps\Json-ajax-test\WEB-INF\classes\com\service\Hello.class
        int startIndex = filePath.indexOf(WEB_INFO_CLASSES) + WEB_INFO_CLASSES.length();
        int endIndex = filePath.length() - SUFFIX_CLASS.length();
        
        // com\service\Hello
        String className = filePath.substring(startIndex, endIndex).replaceAll("\\\\", ".");
        // com.service.Hello
        return className;
    }
    
    private static boolean isClassFile(String filePath)
    {
        return filePath.lastIndexOf(SUFFIX_CLASS) == (filePath.length() - SUFFIX_CLASS.length());
    }
}
