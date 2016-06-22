/**
 * @version 1.0  2015年1月27日
 */
package com.louisgeek.louiscommutils.utils;

import com.sunstar.agronet.info.InnerContacts;

import android.util.Log;

/**
 * LogPrint.java
 * @author louisgeek
 * 2015年1月27日下午3:10:50 
 */
public class LogUtil {
	public static boolean isDebug = InnerContacts.DEFAULT_DEBUG;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = InnerContacts.DEFAULT_TAG;

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}
}
