package com.louisgeek.louiscommutils.utils;

import java.io.File;
import java.text.DecimalFormat;

import com.sunstar.agronet.activity.MainActivity;

import android.content.Context;
import android.util.Log;

/**
 * webview工具类
 * @author louisgeek
 * 2015-1-20 15:24:28
 */
public class WebViewUtil {
	private static final String TAG ="WebViewUtil";
	private static Context context_webViewUtil;
	/** 
     * 清除WebView缓存 
     * @param  context 
     * @param  cacaheDirname APP_WEBVIEW_CACAHE_DIRNAME
     */ 
    public static void clearWebViewCache(Context context,String cacaheDirname){  
           
        //清理Webview缓存数据库  
        try {  
        	context.deleteDatabase("webview.db");   
        	context.deleteDatabase("webviewCache.db");  
        	context.deleteDatabase("webviewCookiesChromium.db");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
           
        //WebView 缓存文件  
     //   File appCacheDir = new File(context.getFilesDir().getAbsolutePath()+cacaheDirname);  
       // Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());  
           
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCache");  
       // Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());  
        
        File webviewCacheChromiumDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCacheChromium");  
    	  File webviewDBDir = new File(context.getFilesDir().getAbsolutePath().replace("files", "databases")+"/webview.db");
    	  File webviewCookiesChromiumDBDir = new File(context.getFilesDir().getAbsolutePath().replace("files", "databases")+"/webviewCookiesChromium.db");
    	  //小米系统
      	  File MIUI_app_webviewDir = new File(context.getFilesDir().getAbsolutePath().replace("files", "app_webview"));
        //删除webview 缓存目录  
        if(webviewCacheDir.exists()){  
        	FileUtil.deleteFile(webviewCacheDir);  
        }  
      /*  //删除appCacheDir 
        if(appCacheDir.exists()){  
            deleteFile(appCacheDir);  
        } */ 
        //删除webviewCacheChromiumDir
        if(webviewCacheChromiumDir.exists()){  
        	FileUtil.deleteFile(webviewCacheChromiumDir);  
        } 
        //删除webviewDBDir
        if(webviewDBDir.exists()){  
        	FileUtil.deleteFile(webviewDBDir);  
        } 
        //删除webviewCookiesChromiumDBDir
        if(webviewCookiesChromiumDBDir.exists()){  
        	FileUtil.deleteFile(webviewCookiesChromiumDBDir);  
        } 
        //删除MIUI_app_webviewDir
        if(MIUI_app_webviewDir.exists()){  
        	FileUtil.deleteFile(MIUI_app_webviewDir);  
        } 
    }
    /**
     * 获取webview缓存大小
     * 2015-1-22 14:32:35
     * @param context
     * @param cacaheDirname
     * @return
     * @throws Exception 
     */
    public static long getWebViewCacheSize(Context context,String cacaheDirname) throws Exception{  
    	long size = 0;//字节
    	//自定义cacaheDirname
    	if(cacaheDirname!=""){
    		//WebView 缓存文件  
            File appCacheDir = new File(context.getFilesDir().getAbsolutePath()+cacaheDirname); 
            if(appCacheDir.exists()){  
            	size+=FileUtil.getFolderSize(appCacheDir);
            }
    	}else {
    		//webviewCacheChromium和webviewCache
      	  File webviewCacheChromiumDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCacheChromium");  
      	  File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCache"); 
      	  File webviewDBFile = new File(context.getFilesDir().getAbsolutePath().replace("files", "databases")+"/webview.db");
      	  File webviewCookiesChromiumDBFile = new File(context.getFilesDir().getAbsolutePath().replace("files", "databases")+"/webviewCookiesChromium.db");
      	  //小米系统
      	  File MIUI_app_webviewDir = new File(context.getFilesDir().getAbsolutePath().replace("files", "app_webview"));
      	  if(webviewCacheChromiumDir.exists()){  
         	size+=FileUtil.getFolderSize(webviewCacheChromiumDir);
         }
        if(webviewCacheDir.exists()){  
        	size+=FileUtil.getFolderSize(webviewCacheDir);
        }
        if(webviewDBFile.exists()){  
        	size+=FileUtil.getFileSize(webviewDBFile);
        }
        if(webviewCookiesChromiumDBFile.exists()){  
        	size+=FileUtil.getFileSize(webviewCookiesChromiumDBFile);
        }
        if(MIUI_app_webviewDir.exists()){  
        	size+=FileUtil.getFolderSize(MIUI_app_webviewDir);
        }
        
		}
           return size;
    }
 
  
  
	public  WebViewUtil(Context context) {
		//super();
		// TODO 自动生成的构造函数存根
		context_webViewUtil=context;
	} 
}
