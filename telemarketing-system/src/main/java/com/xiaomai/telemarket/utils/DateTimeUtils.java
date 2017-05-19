package com.xiaomai.telemarket.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @description 日期操作工具类
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 16/04/2017 10:23 AM
 **/
public class DateTimeUtils {

    /********************************* 获取当前时间start *************************************/

    /**
     * 获取当前日期时间 (yyyy-MM-dd HH:mm:ss)
     * @return  yyyy-MM-dd HH:mm:ss
     */
    public static String getDate(){
        String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return date;
    }

    /**
     * 获取格式化的时间戳
     * @param format  yyyy-MM-dd HH:mm
     * @return
     */
    public static String getDateFormated(String format){
        String date=new SimpleDateFormat(format).format(new Date());
        return date;
    }

    /********************************* 获取当前时间end *************************************/



    /********************************* 时间戳格式化start *************************************/
    /**
     * utc时间戳格式化
     * @param utc
     * @param format 为空默认为“yyyy-MM-dd HH:mm:ss”
     * @return
     */
    public static String utcStampToDate(String utc,String format){
        if (TextUtils.isEmpty(format)) {
            format="yyyy-MM-dd HH:mm:ss";
        }
        long timeStamp=Long.parseLong(utc)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat(format,
                Locale.CHINA);
        return sdf.format(new Date(timeStamp));
    }

    public static String utcStampToDateStr(long timeStamp,String format){
        if (TextUtils.isEmpty(format)) {
            format="yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format,
                Locale.CHINA);
        return sdf.format(new Date(timeStamp));
    }

    /**
     * 格式化时间转换为UNIX时间戳(单位为秒)
     * @param time
     * @param format
     * @return
     */
    public static String getTimeToStamp(String time,String format) {
        if (TextUtils.isEmpty(format)) {
            format="yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format,
                Locale.CHINA);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tmptime = String.valueOf(date.getTime()).substring(0, 10);
        return tmptime;
    }

    /********************************* 时间戳格式化end **************************************/


    /********************************* 时间值验证start **************************************/
    /**
     * 校验时间格式是否有效
     * @param str
     * @param defaultPattern 默认为"yyyy/MM/dd HH:mm"
     * @return
     */
    public static boolean isValidDate(String str,String defaultPattern){
        boolean convertSuccess=false;
        if (TextUtils.isEmpty(defaultPattern)) {
            defaultPattern="yyyy/MM/dd HH:mm";
        }
        SimpleDateFormat format=new SimpleDateFormat(defaultPattern);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /***/
    /**
     * 比较dateStart和dateEnd日期大小
     * @param dateStart 开始时间
     * @param dateEnd 终止时间
     * @param deviation 终止时间比开始时间多的偏差 单位：毫秒
     * @param dateFormat 日期格式(例： "yyyy/MM/dd hh:mm:ss")；设置null，默认为"yyyy-MM-dd"
     * @return dateStart <= dateEnd， 返回true
     */
    public static boolean compareDate(String dateStart,String dateEnd,long deviation,String dateFormat,String replaceChr){
        boolean result=false;
        if (dateFormat==null) {
            dateFormat="yyyy-MM-dd";
        }
        if (!dateFormat.isEmpty()) {
            if (!TextUtils.isEmpty(replaceChr)) {
                dateStart=StringUtils.replaceStr(dateStart, replaceChr, new String[]{"-","/"});
                dateEnd=StringUtils.replaceStr(dateEnd, replaceChr, new String[]{"-","/"});
            }
            SimpleDateFormat df=new SimpleDateFormat(dateFormat,Locale.CHINA);
            Date dtStart=null,dtEnd=null;
            try {
                if (df!=null&&dateStart!=null&&dateEnd!=null) {
                    dtStart = df.parse(dateStart);
                    dtEnd = df.parse(dateEnd);
                    if (deviation!=0) {
                        dtEnd=new Date(dtEnd.getTime()+deviation);
                    }
                }
                if (dtStart!=null&&dtEnd!=null) {
                    result=dtStart.before(dtEnd)||dtStart.equals(dtEnd);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return result;
    }

    /********************************* 时间值验证end **************************************/
}
