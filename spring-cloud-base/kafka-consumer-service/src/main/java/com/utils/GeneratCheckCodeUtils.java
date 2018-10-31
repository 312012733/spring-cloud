package com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class GeneratCheckCodeUtils
{
    
    public static interface CheckCodeCallBack
    {
        void callBack(String checkCode);
    }
    
    public static void generatCheckCode(OutputStream out, CheckCodeCallBack doSomething) throws IOException
    {
        
        // 3 创建图片对象
        BufferedImage image = new BufferedImage(120, 30, BufferedImage.TYPE_INT_BGR);// 宽度
                                                                                     // 高度
                                                                                     // 编码格式
        
        // 4向图片上面放文字信息
        Graphics graphics = image.getGraphics();
        
        // 5设置图片的背景颜色
        graphics.setColor(Color.white);// 设置画笔颜色
        graphics.fillRect(0, 0, 120, 30);// 设置填充区域
        
        // 6向图片上添加5个文字 文字内容随机、每个文字字体颜色不一样、字体大小
        String s = "";
        for (int i = 0; i < 5; i++)
        {
            graphics.setColor(generateColor());// 设置画笔颜色
            graphics.setFont(generateFont());// 设置字体
            String str = generateStr();// 文字
            s += str;
            // 画到图片上
            graphics.drawString(str, 20 * i, 20);
        }
        
        // TODO 业务处理
        doSomething.callBack(s);
        
        // 7画干扰点
        for (int i = 0; i < 50; i++)
        {
            Random random = new Random();
            int x = random.nextInt(120);
            int y = random.nextInt(30);
            graphics.setColor(generateColor());
            graphics.fillOval(x, y, 2, 2);
        }
        // 8画干扰线
        for (int i = 0; i < 3; i++)
        {
            Random random = new Random();
            // 随机生成两个坐标（起点、终点）
            int x1 = random.nextInt(120);
            int y1 = random.nextInt(30);
            int x2 = random.nextInt(120);
            int y2 = random.nextInt(30);
            graphics.setColor(generateColor());
            // 画线条
            graphics.drawLine(x1, y1, x2, y2);
        }
        
        // 9 把图片给浏览器
        ImageIO.write(image, "jpg", out);
        
    }
    
    // 创建一个方法，用来随机产生
    private static String generateStr()
    {
        String[] nums = new String[62];// 小写26 大写26 数字10
        // 添加10个数字 前10 0-9
        for (int i = 0; i <= 9; i++)
        {
            nums[i] = i + "";
        }
        // 10-36 放大写字母 10-35
        for (int i = 65; i <= 90; i++)
        {
            nums[i - 55] = Character.toString((char) i);
        }
        // 小写字母
        for (int i = 97; i <= 122; i++)
        {
            nums[i - 61] = Character.toString((char) i);
        }
        // 随机产生一个文字 36-61
        Random random = new Random();
        int index = random.nextInt(62);
        return nums[index];
    }
    
    // 随机生成颜色
    private static Color generateColor()
    {
        Random random = new Random();
        
        // 数字 取值范围0-255
        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
    
    // 随机生成字体
    private static Font generateFont()
    {
        String[] font_names = new String[]
        { "Broadway", "方正姚体", "方正舒体", "幼圆", "微软雅黑", "Colonna MT" };
        int[] font_styles = new int[]
        { Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };
        // 随机生成字体的名字、风格
        Random random = new Random();
        int font_index = random.nextInt(font_names.length);
        int style_index = random.nextInt(font_styles.length);
        // 创建字体
        return new Font(font_names[font_index], font_styles[style_index], 20 + random.nextInt(10));
        
    }
    
}
