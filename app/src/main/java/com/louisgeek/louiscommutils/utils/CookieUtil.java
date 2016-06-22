/**
 * @version 1.0  2015年3月22日
 */
package com.louisgeek.louiscommutils.utils;

import java.util.Date;

import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * CookieUtil.java
 * @author louisgeek
 * 2015年3月22日下午3:07:31 
 */
public class CookieUtil {
	/**
	清除所有cookie
	 * 
	 * @param context webview.getContext()
	 */
	public static void removeAllCookie(Context context)
	  {
	 //   CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webview.getContext());
		CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
	    CookieManager cookieManager = CookieManager.getInstance();
	    cookieManager.setAcceptCookie(true);
	    cookieManager.removeSessionCookie();
	    cookieManager.removeAllCookie();
	    cookieSyncManager.sync();
	  }
	//好像可以是 MainActivity.this
	public static void removeAllCookieWithoutSessionCookie(Context context) {
        CookieSyncManager.createInstance(context);  
        CookieManager cookieManager = CookieManager.getInstance(); 
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();  
    }
	
	/**
	 * 得到Cookie的值
	 * @param url
	 * @return
	 */
	public static   String getCookie(String url) {  
		//得到网页的 Cookie值  
		CookieManager cookieManager = CookieManager.getInstance();
	    String cookieStr = cookieManager.getCookie(url);
	    return cookieStr;
	  } 
	/**
	 * 
	 * @param context
	 * @return 
	 */
	public static String getCookieSync(Context context,String url) {
		// TODO Auto-generated method stub
		CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
		cookieSyncManager.sync();
		CookieManager cookieManager = CookieManager.getInstance();
		 return cookieManager.getCookie(url);
	}
	  /** 
	   * 同步一下cookie  设置Cookie给url  这里设置夜间模式无效   暂时废弃  用下面的setCookies反而好用   浪费2天时间
	   *  webview.getContext()
	   */  
	//CookieManager会将这个Cookie存入该应用程序/data/data/com.myproject/databases/目录下的webviewCookiesChromium.db数据库的cookies表中
	//打开网页，WebView从数据库中读取该cookie值，放到http请求的头部，传递到服务器
	public static   void setCookieSync(Context context, String url,String cookieStr) {  
	      CookieSyncManager.createInstance(context);  
	      CookieManager cookieManager = CookieManager.getInstance();  
	      /*cookieManager.setAcceptCookie(true);  
	      cookieManager.removeSessionCookie();//移除  
*/	      cookieManager.setCookie(url, cookieStr);//cookieStr是在HttpClient中获得的cookie  
	      CookieSyncManager.getInstance().sync();  
	  }
	/**
	 * 设置Cookie给url
	 * @param url
	 * @param cookieStr
	 */
	public static   void setCookie(String url,String cookieStr) {  
	      CookieManager cookieManager = CookieManager.getInstance();  
	      cookieManager.setCookie(url, cookieStr);//cookieStr是在HttpClient中获得的cookie  
	  } 
	
}
