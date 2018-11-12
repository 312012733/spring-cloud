package com.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类，主要计算每年每月时间 Created by YuanSongMing on 2016/6/3 7:31.
 */
public class DateUtil
{
    public static int DAY_FIRST_MONTH = 1; // 计算当月第一天
    public static int DAY_LAST_MONTH = -1; // 计算当月最后一天
    /**
     * 英文简写（默认）如：2015-11-09
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    
    /**
     * 英文全称，无秒针 如：2015-11-09 12:10
     */
    public static String FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";
    
    /**
     * 英文全称 如：2015-11-09 12:10:08
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 英文全称，无秒针 如：2015-11-09 12:10
     */
    public static String FORMAT_NO_SECOND_CN = "yyyy-MM-dd HH:mm";
    
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    
    /**
     * 中文简写 如：2015年11月09日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    
    /**
     * 中文全称 如：2015年11月09日 12时10分08秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    
    /**
     * 精确到毫秒的完整中文时间 如：2015年11月09日 12时10分08秒335毫秒
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    
    /**
     * 将指定格式的字符串转换为时间
     */
    public static Date parse(String date, String format) throws Exception
    {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }
    
    /**
     * 将时间转换为指定格式的字符串
     */
    public static String format(Date date, String format) throws Exception
    {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    
    /**
     * 获取指定时间的当天0点
     */
    public static Date getFirstTimeOfDayByDate(Date date) throws Exception
    {
        String dateStr = format(date, "yyyy-MM-dd") + " 00:00:00";
        return DateUtil.parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 获取指定时间的当天24点
     */
    public static Date getLastTimeOfDayByDate(Date date) throws Exception
    {
        String dateStr = format(date, "yyyy-MM-dd") + " 24:00:00";
        return DateUtil.parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 获得指定时间当月的第一天0点
     */
    public static Date getFirstTimeOfMonthByDate(Date date) throws Exception
    {
        Date dayOfMonth = getFirstDayOfMonthByDate(date);
        return DateUtil.getFirstTimeOfDayByDate(dayOfMonth);
    }
    
    /**
     * 获得指定时间当月的最后一天24点
     */
    public static Date getLastTimeOfMonthByDate(Date date) throws Exception
    {
        Date dayOfMonth = getLastDayOfMonthByDate(date);
        return DateUtil.getLastTimeOfDayByDate(dayOfMonth);
    }
    
    /**
     * 得到指定时间当月的第一天
     */
    public static Date getFirstDayOfMonthByDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    
    /**
     * 得到指定时间当月的最后一天
     */
    public static Date getLastDayOfMonthByDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 获取当月最后一天的方法，这个稍微要变通一下
        // 先将日期设置为下一月的第一天，然后减去一天就变成了当月的最后一天了
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
    
    /**
     * 获得指定时间当年的第一天0点
     */
    public static Date getFirstTimeOfYearByDate(Date date) throws Exception
    {
        Date dayOfMonth = getFirstDayOfYearByDate(date);
        return DateUtil.getFirstTimeOfDayByDate(dayOfMonth);
    }
    
    /**
     * 获得指定时间当年的最后一天24点
     */
    public static Date getLastTimeOfYearByDate(Date date) throws Exception
    {
        Date dayOfMonth = getLastDayOfYearByDate(date);
        return DateUtil.getLastTimeOfDayByDate(dayOfMonth);
    }
    
    /**
     * 得到指定时间当年的第一天
     */
    public static Date getFirstDayOfYearByDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
    
    /**
     * 得到指定时间当年的最后一天
     */
    public static Date getLastDayOfYearByDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
    
    // public static void main(String[] args) throws Exception {
    // Date date = new Date();
    // System.out.println(format(getFirstTimeOfDayByDate(date),
    // "yyyy-MM-dd HH:mm:ss"));
    // System.out.println(format(getLastTimeOfDayByDate(date),
    // "yyyy-MM-dd HH:mm:ss"));
    // }
}
