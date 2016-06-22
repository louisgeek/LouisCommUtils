package com.louisgeek.louiscommutils.utils;

import java.io.UnsupportedEncodingException;

import android.util.Base64;



public class Base64Util {

	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };
	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
			60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
			-1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
			38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
			-1, -1 };

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 */
	public static String encodeToString(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;
		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4)
						| ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4)
					| ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2)
					| ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		LogUtil.i("Base64加密结果：" + sb.toString());
		return sb.toString();
	}
private static boolean isBase64JiaMi(String str) {
	// TODO Auto-generated method stub
	//去空格，回车，换行，制表符
//	str.replace("\\s", "").replace("\n", "").replace("\r", "").replace("\t", "");
	try {
		byte[] newData=Base64.decode(str,Base64.NO_WRAP);
		String newStr = Base64.encodeToString(newData,Base64.NO_WRAP);
		if (newStr.equals(str)||newStr==str) {
			 LogUtil.i("louisBase64", "加密过："+str);
			return true;
		}else{
			 LogUtil.i("louisBase64","未加密："+ str);
		    return false;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		 LogUtil.i("louisBase64","未加密："+ str);
		return false;
	}
}
	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str) {
		try {
			byte[] decodeStr = decodePrivate(str);
			LogUtil.i("Base64解密结果：" + new String(decodeStr));
			return decodeStr;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.e("Base64Error", "直接返回原字符串的getBytes()，错误:"+e.getMessage());
			return str.getBytes();//
		}
		//return new byte[] {};
	}
	/**
	 * 解密    测试通过！！！！！！！
	 * @param data
	 * @param Base64Flags
	 * @return
	 */
	public static String decodeStringToString(String dataStr,int Base64Flags){
		String newData="";
		if(isBase64JiaMi(dataStr)){
			byte[] data=decode(dataStr);
			newData=new String(data);//byte==>String
		}else {
			newData=dataStr;
		}
		
	 return	newData;
	}
	
	private static byte[] decodePrivate(String str)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		byte[] data = null;
		data = str.getBytes("US-ASCII");
		int len = data.length;
		int i = 0;
		int b1, b2, b3, b4;
		while (i < len) {

			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1)
				break;

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1)
				break;
			sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			do {
				b3 = data[i++];
				if (b3 == 61)
					return sb.toString().getBytes("iso8859-1");
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1)
				break;
			sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			do {
				b4 = data[i++];
				if (b4 == 61)
					return sb.toString().getBytes("iso8859-1");
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1)
				break;
			sb.append((char) (((b3 & 0x03) << 6) | b4));
		}
		return sb.toString().getBytes("iso8859-1");
	}
	/**
	 * 加密
	 * @param data
	 * @param Base64Flags
	 * @return
	 */
	public static String encodeStringToString(String dataStr,int Base64Flags){
		byte[] data=dataStr.getBytes();
	 return	encodeToString(data);
	}
	
	/**
	 * 采用底层加密
	 * @param data
	 * @param Base64Flags
	 * @return
	 */
	//Base64.DEFAULT
	public static byte[] encodeBySystem(byte[] data,int Base64Flags){
	 return	Base64.encode(data, Base64Flags);
	}
	/**
	 * 采用底层加密成字符串
	 * @param data
	 * @param Base64Flags
	 * @return
	 */
	//Base64.DEFAULT
	public static String encodeToStringBySystem(byte[] data,int Base64Flags){
		
		return Base64.encodeToString(data, Base64Flags);
	}
	/**
	 * 采用底层加密成字符串
	 * @param data
	 * @param Base64Flags
	 * @return
	 */
	//Base64.DEFAULT
	public static String encodeStringToStringBySystem(String dataStr,int Base64Flags){
		byte[] data=dataStr.getBytes();
		return Base64.encodeToString(data, Base64Flags);
	}
	/**
	 * 采用底层解码密
	 * @param data
	 * @param Base64Flags
	 * @return
	 *//*
	//Base64.DEFAULT
	public static byte[] decodeBySystem(byte[] data,int Base64Flags){
			try {
				return Base64.decode(data, Base64Flags);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.e("Base64Error", "直接返回byte[]数据data，错误:"+e.getMessage());
				return data;//
			}
	}
	*//**
	 * 采用底层解码密字符串
	 * @param dataStr
	 * @param Base64Flags
	 * @return
	 *//*
	//Base64.DEFAULT
	public static byte[] decodeStringBySystem(String dataStr,int Base64Flags){
		byte[] data=null;
		String newData="";
	try {
		data=Base64.decode(dataStr, Base64Flags);
		newData=new String(data);//byte==>String
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		LogUtil.e("Base64Error", "直接返回原字符getBytes()，错误:"+e.getMessage());
		newData= dataStr;
	}
	if(newData.equals("")||newData==""){
		newData=dataStr;//防止抛出异常失效
	}
	return newData.getBytes();
	}
	*//**
	 * 采用底层解码密字符串后转成字符串
	 * @param dataStr
	 * @param Base64Flags
	 * @return
	 *//*
	//Base64.DEFAULT
	public static String decodeStringBySystemToString(String dataStr,int Base64Flags){
		byte[] data=null;
		String newData="";
		try {
			data=Base64.decode(dataStr, Base64Flags);
			newData =new String(data);//byte==>String
		} catch (Exception e) {
			// TODO: handle exception
			newData=dataStr;
			LogUtil.e("Base64Error", "直接返回原字符，错误:"+e.getMessage());
		}
		if(newData.equals("")||newData==""){
			newData=dataStr;//防止抛出异常失效
		}
		 return newData;
	}
	*/
}
