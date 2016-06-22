/**
 * @version 1.0  2015年2月25日
 */
package com.louisgeek.louiscommutils.utils;


import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;

/**
 * 对话框工具类
 * 
 * @author louisgeek 2015年2月25日上午10:17:08
 */
public class DialogUtil {
	
	/**
	 * 定义一个显示消息的对话框
	 * @param context
	 * @param msg
	 * @param goHome 回到主界面
	 * @param yesStr  "确定"
	 */
	public static void showDialog(final Context context, String msg, String yesStr,
								  boolean goHome, final Class<?> cls) {
		// 创建一个AlertDialog.Builder对象
		Builder builder = new Builder(context)
				.setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setPositiveButton(yesStr, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(context, cls);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}
			});
		} else {
			builder.setPositiveButton(yesStr, null);
		}
		builder.create().show();
	}


	/**
	 * 定义一个显示指定组件的对话框
	 * @param context
	 * @param view
	 * @param yesStr  "确定"
	 */
	public static void showDialog(Context context, View view,String yesStr) {
		new Builder(context).setView(view).setCancelable(false)
				.setPositiveButton(yesStr, null).create().show();
	}

	//==================//////////////////////////////////////////////===================
	/**
	 * 提问框的 Listener
	 */
	// 因为本类不是activity所以通过继承接口的方法获取到点击的事件
	public interface OnClickYesListener {
		abstract void onClickYes();
	}

	/**
	 * 提问框的 Listener
	 * 
	 */
	public interface OnClickNoListener {
		abstract void onClickNo();
	}

	/**
	 * 定义一个自定义显示的对话框
	 * @param context
	 * @param title  "提示"
	 * @param text   "是否确定"
	 * @param listenerYes
	 * @param listenerNo
	 * 使用方法  DialogUtil.showDialog(ShowDialogActivity.this, "提示", "是否确定", new OnClickYesListener() {  
                    public void onClickYes() {  
                        //点击确定干什么  
                    }  
                }, new OnClickNoListener() {  
                    public void onClickNo() {  
                        //点击取消干什么  
                    }  
                });  
	 */
	/*public static void showDialog(Context context, String title,
			String text, final OnClickYesListener listenerYes,
			final OnClickNoListener listenerNo) {

		Builder builder = new Builder(context);

		if (!StringUtil.isBlank(text)) {
			// 此方法为dialog写布局
			final TextView textView = new TextView(context);
			textView.setText(text);
			LinearLayout layout = new LinearLayout(context);
			layout.setPadding(10, 10, 10, 10);
			layout.addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			builder.setView(layout);
		}
		// 设置title
		builder.setTitle(title);
		// 设置确定按钮，固定用法声明一个按钮用这个setPositiveButton
		builder.setPositiveButton(context.getString(R.string.yes),
				new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 如果确定被电击
						if (listenerYes != null) {
							listenerYes.onClickYes();
						}
					}
				});
		// 设置取消按钮，固定用法声明第二个按钮要用setNegativeButton
		builder.setNegativeButton(context.getString(R.string.no),
				new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 如果取消被点击
						if (listenerNo != null) {
							listenerNo.onClickNo();
						}
					}
				});

		// 控制这个dialog可不可以按返回键，true为可以，false为不可以
		builder.setCancelable(false);
		// 显示dialog
		builder.create().show();

	}*/

	/**
	 * 处理字符的方法
	 * 
	 * @param str
	 * @return
	 */
	/*
	 * public static boolean isBlank(String str) { int strLen; if (str == null
	 * || (strLen = str.length()) == 0) { return true; } for (int i = 0; i <
	 * strLen; i++) { if ((Character.isWhitespace(str.charAt(i)) == false)) {
	 * return false; } } return true; }
	 */
	/**
	 * 自定义 类对话框activity
	 * @param FunctionCode
	 * @param TitleText
	 * @param ContentText
	 * @param YesText
	 * @param NoText
	 */
/*	public static void GoToDialogAskActivity(Activity activity,int FunctionCode,String TitleText,String ContentText,String YesText,String NoText) {
		// TODO Auto-generated method stub
	
		//启动类对话框
		Intent intent=new Intent(activity, DialogAskActivity.class);
		if(FunctionCode<0){
			FunctionCode=0;
		}
		if(TitleText==null){
			TitleText="温馨提示";
		}
		if(ContentText==null){
			ContentText="是否确定?";
		}
		if(YesText==null){
			YesText="是";
		}
		if(NoText==null){
			NoText="否";
		}
		intent.putExtra(DialogAskActivity.FUNCTION_CODE, FunctionCode);
		intent.putExtra(DialogAskActivity.TITLE_TEXT, TitleText);
		intent.putExtra(DialogAskActivity.CONTENT_TEXT,ContentText);
		intent.putExtra(DialogAskActivity.YES_TEXT, YesText);
		intent.putExtra(DialogAskActivity.NO_TEXT, NoText);
		activity.startActivity(intent);
		
	}
	
	public static void showProgressBarDialog(Context context) {
		// TODO Auto-generated method stub
		LouisProgressBarDialog progressBarDialog = new LouisProgressBarDialog(context,"加载中...",
				R.layout.dialog_progressbar, R.style.DialogProgressBarTheme);
		
		progressBarDialog.show();
	}*/
	/**
	 * 
	 * @param context
	 * @param title 默认"温馨提示"
	 * @param msg
	 * @param yesStr //传入LouisDialog.BIAO_JI_HIDE_STR代表不显示这个按钮
	 * @param noStr  //传入LouisDialog.BIAO_JI_HIDE_STR代表不显示这个按钮
	 * @param positiveButtonlistener
	 * @param exitBtnlistener
	 */
/*	public static void showLouisDialog(Context context,String title,String msg,String yesStr,String noStr,OnClickListener positiveButtonlistener,OnClickListener exitBtnlistener) {
		// TODO Auto-generated method stub
		if(title==null){
			title="温馨提示";
		}
		if(yesStr==null){
			yesStr="是";
		}else if(yesStr==""||yesStr.equals("")){//传入LouisDialog.BIAO_JI_HIDE_STR代表不显示这个按钮
			yesStr=LouisDialog.BIAO_JI_HIDE_STR;
		}
		
		if(noStr==null){
			noStr="否";
		}else if(noStr==""||noStr.equals("")){//传入LouisDialog.BIAO_JI_HIDE_STR代表不显示这个按钮
			noStr=LouisDialog.BIAO_JI_HIDE_STR;
		}
		 //构架一个builder来显示网页中的对话框  
		
		com.sunstar.agronet.custom.LouisDialog.Builder 
         builder = new com.sunstar.agronet.custom.LouisDialog.Builder(context);  
        builder.setTitle(title);  
        builder.setMessage(msg);  
        builder.setPositiveButton(yesStr, positiveButtonlistener);
        builder.setExitBtn(noStr, exitBtnlistener);
        LouisDialog louisDialog=builder.create();//builder.create().show();
        louisDialog.setCancelable(false);//不能点击返回键关闭
        louisDialog.setCanceledOnTouchOutside(false);//不能点击窗口外面关闭
        louisDialog.show();
	}
	public static LouisLoadingProgressDialog dialog;
	public synchronized static void showLoadingGif(Context context,String msgTxt){
		if(msgTxt==""||msgTxt==null||msgTxt.equals("")){
			msgTxt="正在加载中...";
		}
		hideLoadingGif();//先取消下 暂时解决办法 单例创建出现：上下文对象销毁报错  待优化
		//LouisLoadingProgressDialog dialog =new LouisLoadingProgressDialog(this, "正在加载中",R.anim.loading_img_anim);
		dialog=new LouisLoadingProgressDialog(context, R.drawable.loading_big_tuo,msgTxt,false,false);
		//LogUtil.i("louis==xx==dialog==null");
		dialog.show();	
	}
	public static void hideLoadingGif(){
		if(dialog!=null){
			dialog.dismiss();
		}
	}*/
}
