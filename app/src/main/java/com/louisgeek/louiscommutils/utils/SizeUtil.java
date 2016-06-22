package com.louisgeek.louiscommutils.utils;

import android.content.Context;

/**
 * 单位尺寸转化类    说明：相对于官方内部转换   都+0.5f的原因：根据网上的说法是为了保证结果不小于0.
 * 
 * @Author Administrator 2015-1-14 16:43:46
 */
public class SizeUtil {

	//2015年11月13日15:20:37
	public static int dip2sp(Context context, float dipValue) {
		int pxValue = dip2px(context, dipValue);
		return px2sp(context, pxValue);
	}

	public static int sp2dip(Context context, float spValue) {
		int pxValue = sp2px(context, spValue);
		return px2dip(context, pxValue);
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变  【代码里最终设置的单位基本都是px】
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变  【代码里最终设置的单位基本都是px，所以最常用使用这个】
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变   
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
