/**
 * @version 1.0  2015年1月28日
 */
package com.louisgeek.louiscommutils.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.text.TextUtils;

/**
 * 时间工具类
 * @author louisgeek
 * 2015年1月28日上午9:26:20 
 */
public class DateUtil {

	public  static final String  PNG="png";
	public  static final String  JPG="jpg";
	
	/**
	 *  获取当前时间戳
	 * @return 时间戳
	 */
	public static long getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}
	/**
	 *  获取当前时间字符串
	 * @param  "yyyy-MM-dd HH:mm:ss"
	 * @return 
	 */
	public static String getCurrentTimeWithFormat(String FormatStr){
		if(FormatStr==""){
			FormatStr="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FormatStr);
		return sdf.format(new Date());// new Date()为获取当前系统时间;
	}
	/**
	 *  获取当前时间字符串2
	 * @param  "yyyy-MM-dd HH:mm:ss"
	 * @return 
	 */
	public static String getCurrentTimeWithFormatTwo(String FormatStr){
	    	if(FormatStr==""){
				FormatStr="yyyy-MM-dd HH:mm:ss";
			}
	        long time = System.currentTimeMillis();
	 	    SimpleDateFormat format = new SimpleDateFormat(FormatStr);
	 	   return format.format(new Date(time));
	   }
	/**
	 *  获取有时区的字符串格式字符
	 *  @param  like 1384171247000+0800
	 * @param  "yyyy-MM-dd HH:mm:ss"  
	 * @return 
	 */ 	   
	public static String  getTimeFromLikeShiQuTimeStr(String ShiQuTimeStr,String FormatStr){
		if(FormatStr==""){
			FormatStr="yyyy-MM-dd HH:mm:ss";
		}
	  String time = ShiQuTimeStr.substring(0,ShiQuTimeStr.length()-5);
	   // System.out.println(time);
	    //final String timeZone = str.substring(str.length()-5, str.length());
	    //System.out.println(timeZone);
	   Date date = new Date(Long.parseLong(time));
	   SimpleDateFormat format = new SimpleDateFormat(FormatStr);
	  //  System.out.println(format.format(date));
	    return format.format(date);
}
	/*
	 * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm
	 */
	public static String getStrTime_ymd_hm(String cc_time) {
		String re_StrTime = "";
		if(TextUtils.isEmpty(cc_time) || "null".equals(cc_time)){
			return re_StrTime;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;

	}

	/*
	 * 格式化24小时制<br>
	 * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm:ss  
	 */
	public static String getStrTime_ymd_hms(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;

	}
	/**
	 * 格式化12小时制<br>
	 * 格式：yyyy-MM-dd hh-mm-ss
	 * @param time 时间
	 * @return
	 */
	public static String format12Time(long time){
		return format(time,"yyyy-MM-dd hh:mm:ss");
	}
	/**
	 * 格式化时间,自定义标签
	 * @param time 时间
	 * @param pattern 格式化时间用的标签
	 * @return
	 */
	public static String format(long time,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
	}
	/*
	 * 将时间戳转为字符串 ，格式：yyyy.MM.dd
	 */
	public static String getStrTime_ymd(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/*
	 * 将时间戳转为字符串 ，格式：yyyy
	 */
	public static String getStrTime_y(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/*
	 * 将时间戳转为字符串 ，格式：MM-dd
	 */
	public static String getStrTime_md(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/*
	 * 将时间戳转为字符串 ，格式：HH:mm
	 */
	public static String getStrTime_hm(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/*
	 * 将时间戳转为字符串 ，格式：HH:mm:ss
	 */
	public static String getStrTime_hms(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
	
	/*
	 * 将时间戳转为字符串 ，格式：MM-dd HH:mm:ss
	 */
	public static String getNewsDetailsDate(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/* 
	 * 将字符串转为时间戳
	 */
	public static String getTime() {
		String re_time = null;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date d;
		d = new Date(currentTime);
		long l = d.getTime();
		String str = String.valueOf(l);
		re_time = str.substring(0, 10);
		return re_time;
	}
	
	/*
	 * 将时间戳转为字符串 ，格式：yyyy.MM.dd  星期几
	 */
	public static String getSection(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  EEEE");
//		对于创建SimpleDateFormat传入的参数：EEEE代表星期，如“星期四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
//		yyyy代表年份，如“2010”；dd代表天，如“25”
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
	
//	public static String getTodayDate(){
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		String nowTime=format.format(new Date());
//		return 
//	}
	/**
	 * 利用时间戳取得文件名
	 * @param prefixStr   IMG
	 * @param suffixStr   png
	 * @return
	 */
	public static String getDateTimeStr4FileName(String prefixStr,String suffixStr){
		Date date = new Date(System.currentTimeMillis());
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
	            "'"+prefixStr+"'_yyyyMMdd_HHmmss");
	    return dateFormat.format(date) + "."+suffixStr;
}
	/**
	 * 获取当前天
	 * @return 天
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentDay(){
		Calendar calendar = Calendar.getInstance();
		return calendar.DAY_OF_MONTH;
	}
	
	/** 获取当前月
	 * @return 月
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.MONTH;
	}
	/** 获取当前年
	 * @return 年
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.YEAR;
	}
    /** 
     * 计算剩余日期 
     *  
     * @param remainTime 
     * @return 
     */  
    public static String calculationRemainTime(String endTime, long countDown) { //倒计时 
  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            Date now = new Date(System.currentTimeMillis());// 获取当前时间  
            Date endData = df.parse(endTime);  
            long l = endData.getTime() - countDown - now.getTime();  
            long day = l / (24 * 60 * 60 * 1000);  
            long hour = (l / (60 * 60 * 1000) - day * 24);  
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  
            return "剩余" + day + "天" + hour + "小时" + min + "分" + s + "秒";  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }
    /** 
     * 计算距离现在时间 
     *  
     * @param startTime 
     * @param maxDay like 5  
     * @return 
     */  
    public static String calculationDistanceNowTime(String startTime,String nowTime,int maxDay) {
    	if(startTime!=null&&!startTime.equals("")){
    	startTime=startTime.replace("/", "-");//2015年8月28日14:01:15
    	nowTime=nowTime.replace("/", "-");
    	LogUtil.i("louis==calculationDistanceNowTime=startTime"+startTime);
    	LogUtil.i("louis==calculationDistanceNowTime=nowTime"+nowTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
        	Date nowDate= df.parse(nowTime);
            Date startData = df.parse(startTime);  
            long l =nowDate.getTime()-startData.getTime();  
            long day = l / (24 * 60 * 60 * 1000);  
            long hour = (l / (60 * 60 * 1000) - day * 24);  
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  
        if(day>maxDay){
        	 SimpleDateFormat df_need = new SimpleDateFormat("yyyy-MM-dd");  
        String	needStr=df_need.format(startData);
        	return needStr;
        }else if(day>0){
        	 return day + "天前"; 
         }else if (hour>0) {
        	 return  hour + "小时前";  
		}else if (min>0) {
			 return  min + "分钟前";  
		}else {
			return Math.abs(s)+ "秒前"; //如果取得的时间是某些时区（不是+8时区）的 会影响结果为负数    取绝对值
		}
          
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return ""; 
    	}
    	return "";  
    }
    //http://forensicswiki.org/wiki/Google_Chrome
    public static long  getUtcTime4CookieStr() {
    long	utcTime=System.currentTimeMillis();
    long utcTimeLong=116444736;
    utcTimeLong=utcTimeLong*100;
    //
    utcTime=utcTime+utcTimeLong;
    utcTime=utcTime*1000000;
        return utcTime;
	}
    /**
     * if(result==0)
    	{System.out.println("c1相等c2");}
    	else if(result<0)
    	{System.out.println("c1小于c2");}
    	else
    	{System.out.println("c1大于c2");}
     * @param timeStr01
     * @param timeStr02
     */
    public static int  compareTime(String timeStr01,String timeStr02) {
    	//String timeStr01="2008-01-25 09:12:09";
    	//String timeStr02="2008-01-29 09:12:11";
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar calendar01=Calendar.getInstance();
    	Calendar calendar02=Calendar.getInstance();
    	try{
    		calendar01.setTime(df.parse(timeStr01));
    		calendar02.setTime(df.parse(timeStr02));
    	}catch(ParseException e){
    	System.err.println("格式不正确");
    	}
    	int result=calendar01.compareTo(calendar02);
    	/*if(result==0)
    	{System.out.println("c1相等c2");}
    	else if(result<0)
    	{System.out.println("c1小于c2");}
    	else
    	{System.out.println("c1大于c2");}*/
    	return result;
	}
    /**
     * like   Calendar.HOUR_OF_DAY,10  十小时后
     * like   Calendar.HOUR_OF_DAY,-10  十小时前
     * like   Calendar.MINUTE,10   十分钟后
     * like   Calendar.MINUTE,-10   十分钟前
     * like   Calendar.SECOND,10   十秒钟后
     * like   Calendar.SECOND,-10   十秒钟前
     *  * like  ca.add(Calendar.YEAR, -1); // 年份减1  
				ca.add(Calendar.MONTH, -1);// 月份减1  
				ca.add(Calendar.DATE, -1);// 日期减1 
     * ...待扩展
     * @param timeStr
     */
    public static String getN_Time_Qian_Hou(String timeStr,int CalendarField,int CalendarValue) {
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar Cal=Calendar.getInstance();    
    	try {
			Cal.setTime(df.parse(timeStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    	Cal.add(CalendarField,CalendarValue);    
    	System.out.println("date:"+df.format(Cal.getTime()));   
    	return df.format(Cal.getTime());
	}
    public static String getN_Time_Qian_Hou_4_Now(int CalendarField,int CalendarValue) {
    	String timeStr=getZhongGuoTime();
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar Cal=Calendar.getInstance();    
    	try {
			Cal.setTime(df.parse(timeStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    	Cal.add(CalendarField,CalendarValue);    
    	System.out.println("date:"+df.format(Cal.getTime()));   
    	return df.format(Cal.getTime());
	}


	/**
	 * 2016年6月22日11:29:45
	 */
	public static final String FORMAT_DATE="yyyy-MM-dd";
	public static final String FORMAT_DATE_TIME="yyyy-MM-dd HH:mm:ss";
	/**
	 * 取当前China日期
	 * @return
	 */
	public static String getChinaDate() {
		return parseDate2Str(new Date(),FORMAT_DATE);//取当前时间
	}
	/**
	 * 取当前China时间
	 * @return
	 */
	public static String getChinaDateTime() {
		return parseDate2Str(new Date(),FORMAT_DATE_TIME);//取当前时间
	}

	/**
	 * 多参数 Calendar 获取日期
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static String getChinaDateFromCalendar(int year,int monthOfYear,int dayOfMonth) {
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
		//new Data(int,int,int)过时了
		GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);//初始具有指定年月日的公历类对象。
		Long timeInMillis = calendar.getTimeInMillis();
		return sdf.format(timeInMillis);//new Data()//取当前时间
	}
	/**
	 * 多参数 Calendar 获取时间
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static String getChinaDateTimeFromCalendar(int year,int monthOfYear,int dayOfMonth) {
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
		//new Data(int,int,int)过时了
		GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);//初始具有指定年月日的公历类对象。
		Long timeInMillis = calendar.getTimeInMillis();
		return sdf.format(timeInMillis);//new Data()//取当前时间
	}


	//不推荐  好用的
	@Deprecated
	public static String getZhongGuoTime(){
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_TIME);
		String times = format.format(new Date());
		System.out.print("日期格式---->" + times);
		return times;
	}


	public static Date parseStr2Data(String dateStr,String formatStr){
		Date date=null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
		try {
			date=sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date;
	}

	public static String parseDate2Str(Date date,String formatStr) {
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
		return sdf.format(date);
	}


	public static String parseCalendar2Str(Calendar calendar,String formatStr){
		Date date=parseCalendar2Date(calendar);
		return parseDate2Str(date,formatStr);
	}


	public static Calendar parseStr2Calendar(String dateStr,String formatStr){
		Date date=parseStr2Data(dateStr,formatStr);
		return  parseDate2Calendar(date);
	}

	public static Date parseCalendar2Date(Calendar calendar){
		if (calendar==null){
			calendar= Calendar.getInstance();
		}
		Date date=calendar.getTime();
		return  date;
	}

	public static Calendar parseDate2Calendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return  calendar;
	}
}
