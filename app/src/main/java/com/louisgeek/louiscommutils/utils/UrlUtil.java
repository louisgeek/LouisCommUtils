/**
 * @version 1.0  2015年3月3日
 */
package com.louisgeek.louiscommutils.utils;

/**
 * UrlUtil.java
 * @author louisgeek
 * 2015年3月3日下午3:03:11 
 */
public class UrlUtil {
	/**
	 *返回查询字符串<br>
	 *输入uid返回字符串  xxx.html?uid=something&qq=457810101的查询字符串uid=something
	 *@param  QueryUrl
	 *@param  QueryKey
	 *@return QueryString
	 */
public static String GetQueryStr(String QueryUrl,String QueryKey) {
		String QueryStr = "";
		try {
		String tempQueryUrl=QueryUrl.substring(QueryUrl.indexOf(QueryKey+"="), QueryUrl.length());
			if(tempQueryUrl.contains("&")){
				QueryStr = tempQueryUrl.substring(0, tempQueryUrl.indexOf("&"));
			}else{
				QueryStr = tempQueryUrl.substring(0, tempQueryUrl.length());
			}
		} catch (Exception e) {
			QueryStr = "";
		}
		return QueryStr;
	}
/**
 *返回路径<br>
 *输入url返回字符串  www.baidu.com/xxx.html?uid=something&qq=457810101的查询字符串xxx.html?uid=something&qq=457810101
 *@param  QueryUrl
 *@return QueryPath
 */
public static String GetPathUrlStr(String QueryUrl) {
	String PathUrl = "";
	try {
		PathUrl = QueryUrl.substring(QueryUrl.lastIndexOf("/"), QueryUrl.length());
	} catch (Exception e) {
		PathUrl = "";
	}
	return PathUrl;
}
/**
 *返回?后面所有路径<br>
 *输入url返回字符串  www.baidu.com/xxx.html?uid=something&qq=457810101的查询字符串uid=something&qq=457810101
 *@param  QueryUrl
 *@return QueryString
 */
public static String GetAllQueryStr(String QueryUrl) {
	String PathUrl = "";
	try {
		PathUrl = QueryUrl.substring(QueryUrl.indexOf("?"), QueryUrl.length());
	} catch (Exception e) {
		PathUrl = "";
	}
	return PathUrl;
}
public static String addQueryString(String tempUrl, String queryString) {
	String newUrl="";
	if(!tempUrl.contains("?")){
		newUrl=tempUrl+"?"+queryString;
	}else {
		newUrl=tempUrl+"&"+queryString;
	}
	return newUrl;
}
}
