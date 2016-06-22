/**
 * @version 1.0  2015年1月27日
 */
package com.louisgeek.louiscommutils.utils;

import com.bumptech.glide.Glide;

//import pl.droidsonroids.gif.GifImageView;

import com.sunstar.agronet.R;
import com.sunstar.agronet.custom.LouisToast;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import pl.droidsonroids.gif.GifImageView;

/**
 * ToastMessage.java
 * @author louisgeek
 * 2015年1月27日下午4:08:07 
 */
public class ToastUtil {
	public static boolean isShow = true;
	public static Toast mToast;

	/**
	 * 短时间显示Toast 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
	}

	/**
	 * 短时间显示Toast
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 长时间显示Toast
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow){
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 长时间显示Toast
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow){
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 自定义显示Toast时间
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow){
			Toast.makeText(context, message, duration).show();
		}
	}

	/**
	 * 自定义显示Toast时间
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow){
			Toast.makeText(context, message, duration).show();
		}
	}
	public static void showLouisGifToast(Context context,String txtStr) {
	if(mToast == null) {  
		 mToast = Toast.makeText(context,
				 txtStr, Toast.LENGTH_LONG);
		 mToast.setGravity(Gravity.CENTER, 0, 0);
	   		   LinearLayout toastView = (LinearLayout) mToast.getView();
	   		  //## ImageView imageCodeProject = new ImageView(context);
	   		   GifImageView gifImageView=new GifImageView(context);
	   		int dip2px= SizeUtil.dip2px(context, 100);
			System.out.println("louis==uh==dip2px="+dip2px);
			gifImageView.setImageResource(R.drawable.superman_big);
			gifImageView.setLayoutParams(new LayoutParams(dip2px, dip2px));//注意此处
			/*Glide.with(context).load(R.drawable.superman_big)
			.override(dip2px,dip2px)
			.into(gifImageView);*/
	   		   toastView.addView(gifImageView, 0);
      //  mToast = Toast.makeText(MobileSendTopicActivity.this, text, Toast.LENGTH_SHORT);  
    } else {  
       mToast.setText(txtStr);    
       // mToast.setDuration(Toast.LENGTH_SHORT);  
    }  
	mToast.show();
}
	public static void showLouisToastLoadingGif(Context context,String txtStr) {
		if(mToast == null) {  
			 mToast = Toast.makeText(context,
					 txtStr, Toast.LENGTH_LONG);
			//设置Toast显示位置(起点位置,水平向右位移,垂直向下位移) toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 200);
			 //Toast显示位置，以横向和纵向的百分比计算，参数均为float类型(水平位移正右负左，竖直位移正上负下)
			 mToast.setGravity(Gravity.CENTER, 0, 0);
		   		  LinearLayout toastView = (LinearLayout) mToast.getView();
		   		  //## ImageView imageCodeProject = new ImageView(context);
		   		   GifImageView gifImageView=new GifImageView(context);
		   		int dip2px= SizeUtil.dip2px(context, 100);
				System.out.println("louis==uh==dip2px="+dip2px);
				gifImageView.setImageResource(R.drawable.superman_big);
				gifImageView.setLayoutParams(new LayoutParams(dip2px, dip2px));//注意此处
				/*Glide.with(context).load(R.drawable.superman_big)
				.override(dip2px,dip2px)
				.into(gifImageView);*/
		   		   toastView.addView(gifImageView, 0);
	      //  mToast = Toast.makeText(MobileSendTopicActivity.this, text, Toast.LENGTH_SHORT);  
	    } else {  
	     mToast.setText(txtStr);    
	       // mToast.setDuration(Toast.LENGTH_SHORT);  
	    }  
		mToast.show();
	}
	public static void showLouisToast(Context context,String txtStr) {
		LouisToast louisToast=new LouisToast(context);
		
	louisToast.show(txtStr, LouisToast.LENGTH_MAX);
}
	public static void showLouisToast4Gif(Context context,String txtStr) {
	LouisToast louisToast=new LouisToast(context,R.drawable.superman);
	louisToast.show(txtStr, LouisToast.LENGTH_MAX);
}
}
