/**
 * @version 1.0  2015年1月21日
 */
package com.louisgeek.louiscommutils.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Application工具类
 * @author louisgeek
 * 2015年1月21日下午8:34:14 
 */
public  class AppUtil{

	private static Context context_AppUtil;
	 
	/** 
     * 获取应用程序名称 
     */ 
    public static String getAppName(Context context)  
    {  
        try  
        {  
            PackageManager packageManager = context.getPackageManager();  
            PackageInfo packageInfo = packageManager.getPackageInfo(  
                    context.getPackageName(), 0);  
            int labelRes = packageInfo.applicationInfo.labelRes;  
            return context.getResources().getString(labelRes);  
        } catch (NameNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context) {
		context_AppUtil=context;
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        int version = info.versionCode;
	        return version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	/**
	 * 获取版本名
	 * @return 当前应用的版本名
	 */
	public static String getVersionName(Context context) {
		context_AppUtil=context;
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        String version = info.versionName;
	        return version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "版本读取错误";
	    }
	}

	/**
	 * 获取应用程序包名
	 */
	public static String getPackageName(Context context) {
		if (context == null) {
			LogUtil.e("getPackageName  context为空");
			return null;
		}
		String pkgName = context.getPackageName();
		LogUtil.i("APP包名：" + pkgName);
		return pkgName;
	}
	public AppUtil(Context context) {
		super();
		context_AppUtil=context;
	}
	public static  String getTopActivityName(Context context){
        String topActivityClassName=null;
         ActivityManager activityManager =
        (ActivityManager)(context.getSystemService(Context.ACTIVITY_SERVICE )) ;
         //android.app.ActivityManager.getRunningTasks(int maxNum) 
         //int maxNum--->The maximum number of entries to return in the list
         //即最多取得的运行中的任务信息(RunningTaskInfo)数量
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
         if(runningTaskInfos != null){
             ComponentName f=runningTaskInfos.get(0).topActivity;
             topActivityClassName=f.getClassName();
             
         }
         //按下Home键盘后 topActivityClassName=com.android.launcher2.Launcher
         System.out.println("louisz==getTopActivityName:"+topActivityClassName);
         return topActivityClassName;
    }
/**
 * App是否在前台运行
 * @return
 */
	public  static boolean isRunningForegroundWithAty(Context context){
        String packageName=getPackageName(context);
        String topActivityClassName=getTopActivityName(context);
        System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
        if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
            System.out.println("louisz==---> isRunningForeGroundWithAty");
            return true;
        } else {
            System.out.println("louisz==---> isRunningBackGroundWithAty");
            return false;
        }
    }
	public static boolean isRunningForeground (Context context)  
	{  
	    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
	    ComponentName cn=null;
		try {
			cn = am.getRunningTasks(1).get(0).topActivity;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}  
	    String currentPackageName = cn.getPackageName();  
	    if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName(context)))  
	    {  
	    	 System.out.println("louisz==---> isRunningForeGround");
	        return true ;  
	    }  
	    System.out.println("louisz==---> isRunningBackGround");
	    return false ;  
	}  
	/**
	 * 判断app是否在前台还是在后台运行
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				/*
				BACKGROUND=400 EMPTY=500 FOREGROUND=100
				GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
				Log.i(context.getPackageName(), "louisz==此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "louisz==处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "louisz==处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
	//安装APK
	public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
	/**
	 * 唤醒屏幕并解锁
	 * 需要添加权限
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.DISABLE_KEYGUARD" />

	 * @param context
	 */
	public static void wakeUpAndUnlock(Context context){  
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);  
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");  
        //解锁  
        kl.disableKeyguard();  
        //获取电源管理器对象  
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);  
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");  
        //点亮屏幕  
        wl.acquire();  
        //释放  
        wl.release();  
    }

	/**
	 * 2016年6月22日11:39:07
	 */

	/** 安装一个apk文件 */
	public static void install(Context context, File uriFile) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/** 卸载一个app */
	public static void uninstall(Context context, String packageName) {
		//通过程序的包名创建URI
		Uri packageURI = Uri.parse("package:" + packageName);
		//创建Intent意图
		Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		//执行卸载程序
		context.startActivity(intent);
	}

	/** 检查手机上是否安装了指定的软件 */
	public static boolean isAvailable(Context context, String packageName) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}

	/** 检查手机上是否安装了指定的软件 */
	public static boolean isAvailable(Context context, File file) {
		return isAvailable(context, getPackageName(context, file.getAbsolutePath()));
	}

	/** 根据文件路径获取包名 */
	public static String getPackageName(Context context, String filePath) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			return appInfo.packageName;  //得到安装包名称
		}
		return null;
	}

	/** 从apk中获取版本信息 */
	public static String getChannelFromApk(Context context, String channelPrefix) {
		//从apk包中获取
		ApplicationInfo appinfo = context.getApplicationInfo();
		String sourceDir = appinfo.sourceDir;
		//默认放在meta-inf/里， 所以需要再拼接一下
		String key = "META-INF/" + channelPrefix;
		String ret = "";
		ZipFile zipfile = null;
		try {
			zipfile = new ZipFile(sourceDir);
			Enumeration<?> entries = zipfile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String entryName = entry.getName();
				if (entryName.startsWith(key)) {
					ret = entryName;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zipfile != null) {
				try {
					zipfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String[] split = ret.split(channelPrefix);
		String channel = "";
		if (split.length >= 2) {
			channel = ret.substring(key.length());
		}
		return channel;
	}
}
