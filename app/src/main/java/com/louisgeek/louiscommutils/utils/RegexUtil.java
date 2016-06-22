package com.louisgeek.louiscommutils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/** 
     * 手机号码格式匹配 
     *  
     * @param mobiles 
     * @return 
     */  
    public static boolean isMobileNO(String mobiles) {  
        Pattern p = Pattern  
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,3,4,5-9]))\\d{8}$");  
        Matcher m = p.matcher(mobiles);  
        System.out.println(m.matches() + "-telnum-");  
        return m.matches();  
    }  
  
    /** 
     * 是否含有指定字符 
     *  
     * @param expression 
     * @param text 
     * @return 
     */  
    private static boolean matchingText(String expression, String text) {  
        Pattern p = Pattern.compile(expression);  
        Matcher m = p.matcher(text);  
        boolean b = m.matches();  
        return b;  
    }  
  
    /** 
     * 邮政编码 
     *  
     * @param zipcode 
     * @return 
     */  
    public static boolean isZipcode(String zipcode) {  
        Pattern p = Pattern.compile("[0-9]\\d{5}");  
        Matcher m = p.matcher(zipcode);  
        System.out.println(m.matches() + "-zipcode-");  
        return m.matches();  
    }  
    /**
     * 
     * @param regexStr4Pattern 
     * like  email: "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$" 
     * @param inputStr
     * @return
     */
    public static boolean RegexBackTrue(String regexStr4Pattern,String inputStr) {    
        Pattern p = Pattern.compile(regexStr4Pattern);   
        Matcher m = p.matcher(inputStr);   
        return m.matches();   
    } 
    /** 
     * 邮件格式 
     *  
     * @param email 
     * @return 
     */  
    public static boolean isValidEmail(String email) {  
        Pattern p = Pattern  
                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
        Matcher m = p.matcher(email);  
        System.out.println(m.matches() + "-email-");  
        return m.matches();  
    }  
  
    /** 
     * 固话号码格式 
     *  
     * @param telfix 
     * @return 
     */  
    public static boolean isTelfix(String telfix) {  
        Pattern p = Pattern.compile("d{3}-d{8}|d{4}-d{7}");  
        Matcher m = p.matcher(telfix);  
        System.out.println(m.matches() + "-telfix-");  
        return m.matches();  
    }  
  
    /** 
     * 用户名匹配  长度 在2-10之间
     *  
     * @param name 
     * @return 
     */  
    public static boolean isCorrectUserName(String name) {  
        Pattern p = Pattern.compile("([A-Za-z0-9]){2,10}");  
        Matcher m = p.matcher(name);  
        System.out.println(m.matches() + "-name-");  
        return m.matches();  
    }  

    /** 
     * 密码匹配，以字母开头，长度 在6-18之间，只能包含字符、数字和下划线。 
     *  
     * @param pwd 
     * @return 
     *  
     */  
    public static boolean isCorrectUserPwd(String pwd) {  
        Pattern p = Pattern.compile("\\w{6,18}");  
        Matcher m = p.matcher(pwd);  
        System.out.println(m.matches() + "-pwd-");  
        return m.matches();  
    }  
  
}
