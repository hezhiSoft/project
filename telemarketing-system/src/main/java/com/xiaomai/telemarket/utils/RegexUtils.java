package com.xiaomai.telemarket.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @description 正则表达式工具类
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 14/04/2017 3:04 PM
 **/
public class RegexUtils {


    private RegexUtils(){}

    /**
     * 用正则验证整数类型
     */
    public static boolean isNumeric(String str){
        if(str==null||str.trim().isEmpty()){
            return  false;
        }
        boolean result=true;
        Pattern pattern= Pattern.compile("[0-9]*");//"[0-9]*"
        Matcher isNum=pattern.matcher(str);
        if (!isNum.matches()) {
            result= false;
        }
        return result;
    }

    /**
     * 验证日期格式
     * @param date
     * @return
     */
    public static boolean isDate(String date){
        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|"+
                "(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?"+
                "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|"+
                "(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern p=Pattern.compile(eL);
        Matcher m=p.matcher(date);
        boolean b=m.matches();
        return b;
    }

    /**
     * 检查整数
     * @param num
     * @param type  "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数  
     * @return
     */
    public static boolean isNumber(String num, String type) {
        String eL = "";
        if (type.equals("0+"))
            eL = "^\\d+$";// 非负整数
        else if (type.equals("+"))
            eL = "^\\d*[1-9]\\d*$";// 正整数
        else if (type.equals("-0"))
            eL = "^((-\\d+)|(0+))$";// 非正整数
        else if (type.equals("-"))
            eL = "^-\\d*[1-9]\\d*$";// 负整数
        else
            eL = "^-?\\d+$";// 整数
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(num);
        boolean b = m.matches();
        return b;
    }

    /**
     * 检查浮点数  
     * @param num
     * @param type "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数  
     * @return
     */
    public static boolean isFloat(String num,String type){
        String eL = "";
        if(type.equals("0+"))eL = "^\\d+(\\.\\d+)?$";//非负浮点数   
        else if(type.equals("+"))eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";//正浮点数   
        else if(type.equals("-0"))eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";//非正浮点数   
        else if(type.equals("-"))eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";//负浮点数   
        else eL = "^(-?\\d+)(\\.\\d+)?$";//浮点数   
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(num);
        boolean b = m.matches();
        return b;
    }
    /**
     * 过滤出字符串中的数字
     * @param str
     * @return
     */
    public static String filterNumber(String str){
        if (TextUtils.isEmpty(str)) return "";
        str=str.replaceAll("[^0-9]", "");//return the modified input string
        return str;
    }

    /**
     * 过滤出字母
     * @param str
     * @return
     */
    public static String filterAlphabet(String str){
        if (TextUtils.isEmpty(str)) return "";//[^(A-Za-z)] will contains '(' ')'
        str=str.replaceAll("[^A-Za-z]", "");//return the modified input string
        return str;
    }

    /**
     * 过滤出中文
     * @param str
     * @return
     */
    public static String filterChinese(String str){
        if (TextUtils.isEmpty(str)) return "";
        str=str.replaceAll("[^\\u4e00-\\u9fa5]", "");//return the modified input string
        return str;
    }

    /**
     * 判断是否有特殊字符(但是没有判断分隔符)
     * (中文、英文、数字、空格、英文逗号,小数点)
     * @param str
     * @return
     */
    public static boolean hasSpecialChr(String str){
        if (!TextUtils.isEmpty(str)) {
            String strFilter=str.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5 ,，.;:)]", "");
            return !TextUtils.equals(str, strFilter);
        }
        return false;
    }

    /**
     * 过滤出字母、数字和中文
     * @param str
     * @return
     */
    public static String filterAll(String str){
        if (TextUtils.isEmpty(str)) return "";//
        str=str.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5 ,，.)]", "");//return the modified input string
        return str;
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str){
        boolean isPhone;
        try {
            if (!TextUtils.isEmpty(str)) {
                String [] specialNumber={"10086","10010","10000"};
                for (String number:specialNumber) {
                    if (TextUtils.equals(number,str)) {
                        return true;
                    }
                }
                isPhone = isChinaPhoneLegal(str) || isHKPhoneLegal(str);
            } else {
                isPhone = false;
            }
        }catch (Exception e){
            isPhone = false;
        }
        return isPhone;
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


}
