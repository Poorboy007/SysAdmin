package com.icolsky.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.icolsky.common.constants.BaseDateConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by FuChang Liu
 */
@Slf4j
public class DateUtil {

    public static final SimpleDateFormat STD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 将 yyyy-MM-dd HH:mm:ss 转换为 Date */
    public static Date getDateFromString(String strDate) {
        if(strDate == null || strDate.isEmpty()) return null;
        Date date = null;
        try {
            date = STD_DATE_FORMAT.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /** 将 Date 转换为 yyyy-MM-dd HH:mm:ss */
    public static String getStringFromDate(Date date){
        String empty = "";
        if(date == null) return empty;
        return STD_DATE_FORMAT.format(date);
    }

    /** 将特定格式字符串转换为 Date */
    public static Date getDateFromString (String strDate, String format) {
        if(strDate == null || strDate.isEmpty()) return null;
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /** 将Date转换为特定格式字符串 */
    public static String getStringFromDate(Date date, String format){
        String empty = "";
        if(date == null) return empty;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    /**
     * 将时间字符串，转换别的格式的时间字符串
     * @param strDate 原日期字符串
     * @param oFormat 原日期格式
     * @param nFormat 新日期格式
     */
    public static String getStringForStrDate(String strDate, String oFormat, String nFormat) throws ParseException{
        if(strDate == null || strDate.isEmpty()) return null;
        SimpleDateFormat oldFormat = new SimpleDateFormat(oFormat);
        SimpleDateFormat newFormat = new SimpleDateFormat(nFormat);
        Date date = oldFormat.parse(strDate);
        return newFormat.format(date);
    }

    /** 将特定字符串转换为毫秒值 */
    public static long getMillisFromString(String strDate, String format) {
        if (!StringUtil.isEmpty(strDate)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.parse(strDate);
                Calendar cal = dateFormat.getCalendar();
                return cal.getTimeInMillis();
            } catch (Exception e) {
                ExceptionUtil.printStackTrace(e);
            }
        }
        return 0;
    }

    /** 将毫秒值转换为特定字符串 */
    public static String getStringFromMills(Long mills, String format){
        if(mills == null || mills == 0) return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(new Date(mills));
        } catch (Exception e) {
            ExceptionUtil.printStackTrace(e);
        }
        return "";
    }


    /** 获取当前时间 字符串 */
    public static String setToday(){
        Long nowMills = System.currentTimeMillis();
        return DateUtil.getStringFromMills(nowMills, "yyyy-MM-dd HH:mm:ss");
    }

    /** 获取昨天当前时间 字符串 */
    public static String setYesterday(){
        Long nowMills = System.currentTimeMillis();
        Long yesMills = nowMills - (24 * 60 * 60 * 1000);
        return DateUtil.getStringFromMills(yesMills, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串转时间
     * @update luowuhui
     * @param strDate
     * @param dateConstant
     * @return
     */
    public static Date strToDate(String strDate, BaseDateConstant dateConstant) {
        if (strDate.matches(dateConstant.regex())) {

            SimpleDateFormat dateFormat = new SimpleDateFormat(dateConstant.forma());
            try {
                return dateFormat.parse(strDate);
            } catch (ParseException e) {

            }
        }
        return null;
    }

	@Test
    public void test(){
        Long endMills = System.currentTimeMillis();
        Long startMills = endMills - (1000 * 60 * 5);
        System.out.println(getStringFromMills(startMills, "yyyy-MM-dd HH:mm:ss"));
        System.out.println(getStringFromMills(endMills, "yyyy-MM-dd HH:mm:ss"));
    }
}
