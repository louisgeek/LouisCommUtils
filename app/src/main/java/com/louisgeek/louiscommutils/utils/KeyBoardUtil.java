/**
 * @version 1.0  2015年1月27日
 */
package com.louisgeek.louiscommutils.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘操作工具类
 * @author louisgeek
 * 2015年1月27日上午10:58:04 
 */
public class KeyBoardUtil {

	/**
	 * 打开软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
}
