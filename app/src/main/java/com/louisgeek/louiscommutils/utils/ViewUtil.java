package com.louisgeek.louiscommutils.utils;

import java.util.ArrayList;
import java.util.List;

import com.sunstar.agronet.R;
import com.sunstar.agronet.tools.AndroidVersionTool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

/**
 * 2015-4-28 16:57:29
 * @author louisgeek
 *
 */
public class ViewUtil {
	
	private static long lastClickTime;
	
	// 修改整个界面所有控件的字体
 	public static void changeFonts(ViewGroup root,String path, Activity act) {  
        //path是字体路径
 		Typeface tf = Typeface.createFromAsset(act.getAssets(),path);  
        for (int i = 0; i < root.getChildCount(); i++) {  
            View v = root.getChildAt(i); 
            if (v instanceof TextView) {  
               ((TextView) v).setTypeface(tf);  
            } else if (v instanceof Button) {  
               ((Button) v).setTypeface(tf);  
            } else if (v instanceof EditText) {  
               ((EditText) v).setTypeface(tf);  
            } else if (v instanceof ViewGroup) {  
               changeFonts((ViewGroup) v, path,act);  
            } 
        }  
     }
 	
 	// 修改整个界面所有控件的字体大小
  	public static void changeTextSize(ViewGroup root,int size, Activity act) {  
         for (int i = 0; i < root.getChildCount(); i++) {  
             View v = root.getChildAt(i);  
             if (v instanceof TextView) {  
                ((TextView) v).setTextSize(size);
             } else if (v instanceof Button) {  
            	((Button) v).setTextSize(size);
             } else if (v instanceof EditText) {  
            	((EditText) v).setTextSize(size);  
             } else if (v instanceof ViewGroup) {  
                changeTextSize((ViewGroup) v,size,act);  
             }  
         }  
      }
  	
  	// 不改变控件位置，修改控件大小
// Adapter---getView方法中慎用
	public static void changeWH(View v,int W,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
	    params.width = W;
	    params.height = H;
	    v.setLayoutParams(params);
	}

	/**
	 * 修改普通View的宽<br>
	 * Adapter---getView方法中慎用
	 */
	public static void changeW(View v, int W) {
		LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.width = W;
		v.setLayoutParams(params);
	}
	
	// 修改控件的高
	// Adapter---getView方法中慎用
	public static void changeH(View v,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
	    params.height = H;
	    v.setLayoutParams(params);
	}


	//得到所有子view
    public static List<View> getAllChildView(View view) {
        List<View> allChildView = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View viewChild = vg.getChildAt(i);
                allChildView.add(viewChild);
                allChildView.addAll(getAllChildView(viewChild));
            }
        }
        return allChildView;
    }
  //得到所有子TextView
    public static List<TextView> getAllTextView(List<View> views) {
        List<TextView> allTextViewList = new ArrayList<TextView>();
        for (View view : views) {
            if (view instanceof CheckBox) {
            } else if (view instanceof TextView) {
                allTextViewList.add((TextView) view);
            }
        }
        return allTextViewList;
    }
    //得到所有子TextView  ChangeByLouis 
    public static List<TextView> getAllTextView(View parentView) {
    	List<View> views=getAllChildView(parentView);
        List<TextView> allTextViewList = new ArrayList<TextView>();
        for (View view : views) {
            if (view instanceof CheckBox) {
            } else if (view instanceof TextView) {
                allTextViewList.add((TextView) view);
            }
        }
        return allTextViewList;
    }
  //得到所有子ImageView
    public static List<ImageView> getAllImageView(List<View> views){
        List<ImageView> allImageViewList = new ArrayList<ImageView>();
        for (View view : views) {
            if (view instanceof CheckBox) {
            } else if (view instanceof ImageView) {
            	allImageViewList.add((ImageView) view);
            }
        }
        return allImageViewList;
    }
    //得到所有子ImageView  ChangeByLouis
    public static List<ImageView> getAllImageView(View parentView){
    	List<View> views=getAllChildView(parentView);
        List<ImageView> allImageViewList = new ArrayList<ImageView>();
        for (View view : views) {
            if (view instanceof CheckBox) {
            } else if (view instanceof ImageView) {
            	allImageViewList.add((ImageView) view);
            }
        }
        return allImageViewList;
    }
    //除  ViewGroup ViewGroup  TextView
    private static List<View> getViewListFromAllChildViews(List<View> allChildViews) {
        List<View> allViewsList = new ArrayList<View>();
        for (View child : allChildViews) {
        	//
            if ((child instanceof ViewGroup)||(child instanceof ViewGroup)||(child instanceof TextView)) {
            	//
            } else if (child instanceof View) {
                allViewsList.add(child);
            }
        }
        return allViewsList;
    }
  //得到所有View   //除  ViewGroup ViewGroup  TextView 
    public static List<View> getViewList(View view) {
        return getViewListFromAllChildViews(getAllChildView(view));
    }
    //得到所有View  ByTag 
    public static List<View> getViewListByTag(List<View> viewList,String tag){
        List<View> tagViewList=new ArrayList<View>();
        for(View view:viewList){
            if(view.getTag()!=null&&view.getTag().equals(tag)){
                tagViewList.add(view);
            }
        }
        return tagViewList;
    }
    /**
     * changeTextViewTextColor
     * @param allTextViewList
     * @param textColor    getResources().getColor(R.color.text_night);
     */
   
    public  static  void changeAllTextViewTextColor(List<TextView> allTextViewList,int textColor) {
	// TODO Auto-generated method stub
    	 if (allTextViewList != null&&textColor!=0) {
    	        for (TextView textView : allTextViewList) {
    	            textView.setTextColor(textColor);
    	        }
    	    }
    }
    /**
     * 
     * @param allViewList
     * @param drawable  getResources().getDrawable(weightLineColor)  NewApi
     * @param color    getResources().getColor(R.color.text_night);   
     */
    
	public  static  void changeAllViewBackground(List<View> allViewList,Drawable drawable,int color) {
    	// TODO Auto-generated method stub
    	   for (View view:allViewList){
    	        	if(drawable!=null){
    	        		// view.setBackground(drawable);
    	        		 AndroidVersionTool.setBackgroundOfVersion(view,drawable);
    	        	}else {
    	        		 view.setBackgroundColor(color);
					}
    	    }
        }
    /**
     * 
     * @param allTextViewList
     * @param drawable  getResources().getDrawable(weightLineColor)  NewApi
     * @param color    getResources().getColor(R.color.text_night);   
     */
    
	public  static  void changeAllTextViewBackground(List<TextView> allTextViewList,Drawable drawable,int color) {
    	// TODO Auto-generated method stub
    	   for (TextView mTextView:allTextViewList){
    	        	if(drawable!=null){
    	        		//mTextView.setBackground(drawable);
    	        		 AndroidVersionTool.setBackgroundOfVersion(mTextView,drawable);
    	        	}else {
    	        		mTextView.setBackgroundColor(color);
					}
    	    }
        }
    
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();  
        long timeD = time - lastClickTime; 
        LogUtil.i("louis==xxxq==time:"+time);
        LogUtil.i("louis==xxxq==timeD:"+timeD);
        if ( 0 < timeD && timeD < 800) {  //500 卡一点就2次了
            return true;     
        }     
        lastClickTime = time;     
        return false;     
    }  
}
