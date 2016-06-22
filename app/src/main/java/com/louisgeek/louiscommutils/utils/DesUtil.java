package com.louisgeek.louiscommutils.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class DesUtil {

	private static byte[] iv = { 0x12, 0x34, 0x56, 0x78, 0x56, 0x34, 0x12, 0x34  };//向量
	public final static String DESUTIL_KEY ="O8q4C5A2"; //加密解密密匙
	
	public static String encryptDES(String encryptString, String DesUtil_Key)throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(DesUtil_Key.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);

		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

		return Base64.encodeToString(encryptedData, Base64.NO_WRAP);

	}

	public static String decryptDES(String decryptString, String DesUtil_Key)throws Exception {

		byte[] byteMi = Base64.decode(decryptString, Base64.NO_WRAP);

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(DesUtil_Key.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData);

	}

}
