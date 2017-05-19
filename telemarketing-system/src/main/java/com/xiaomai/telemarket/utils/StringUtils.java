package com.xiaomai.telemarket.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 字符串操作类
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 14/04/2017 2:27 PM
 **/
public class StringUtils {

    /********************************* 字符串值替换start **************************************/

    /**
     * 过滤字符串中指定字符
     * @param oldtext
     * @param newChar  新的字符
     * @param replaceChrs 待替换的字符
     * @return
     */
    public static String replaceStr(String oldtext,String newChar,String...replaceChrs){
        if (!TextUtils.isEmpty(oldtext)&&replaceChrs!=null&&newChar!=null) {
            for (String chr : replaceChrs)
            {
                if (chr!=null) {
                    oldtext=oldtext.replaceAll(chr, newChar);
                }
            }
        }
        return oldtext;
    }

    /**
     * String --> List<String>
     * @param text
     * @param splitChar 分隔符
     * @author du yang
     * @return
     */
    public static List<String> string2List(String text, String splitChar){
        List<String> list=null;
        if (TextUtils.isEmpty(text)==false) {
            String[] strArray=text.split(splitChar);
            if (strArray!=null) {
                list=new ArrayList<String>();
                for (String str : strArray) {
                    list.add(str);
                }
            }
        }
        return list;
    }

    /**
     * List<String> -->  String
     * @param list
     * @param splitChar 分隔符
     * @author du yang
     * @return
     */
    public static String list2String(List<String> list,String splitChar){
        StringBuilder sb=new StringBuilder();
        if (list!=null&&list.size()>0) {
            for (String str : list) {
                sb.append(str+splitChar);
            }
            if (sb.lastIndexOf(splitChar)>0) {
                sb.deleteCharAt(sb.lastIndexOf(splitChar));
            }
        }
        return sb.toString();
    }


    /**
     * @desc 字符串转Integer
     *
     * @author youngdu <youngdu29@gmail.com>
     * @createtime 16/04/2017 10:27 AM
     */
    public static int string2Integer(String text,int defaultValue){
        int value=defaultValue;
        if (!TextUtils.isEmpty(text)&&RegexUtils.isNumeric(text)) {
            value=Integer.valueOf(text);
        }
        return value;
    }

    /**
     * @desc 字符窜转Double
     * 
     * @author youngdu <youngdu29@gmail.com>
     * @createtime 16/04/2017 10:27 AM
     */
    public static double string2Double(String text,double defaultValue){
        double value=defaultValue;
        if (!TextUtils.isEmpty(text)&&RegexUtils.isFloat(text, "")) {
            value=Double.valueOf(text);
        }
        return value;
    }

    /**
     * @desc 字符串转Float
     * 
     * @author youngdu <youngdu29@gmail.com>
     * @createtime 16/04/2017 10:27 AM
     */
    public static float string2Float(String text,float defaultValue){
        float value=defaultValue;
        if (!TextUtils.isEmpty(text)&&RegexUtils.isFloat(text, "")) {
            value=Float.valueOf(text);
        }
        return value;
    }

    /********************************* 字符串值替换end **************************************/
}
