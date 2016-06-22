package com.louisgeek.louiscommutils.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class DeviceUtil {
	/**
	 * 获取应用程序的IMEI号
	 */
	public static String getIMEI(Context context) {
		if (context == null) {
			LogUtil.e("getIMEI  context为空");
		}
		TelephonyManager telecomManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telecomManager.getDeviceId();
		LogUtil.i("IMEI标识：" + imei);
		return imei;
	}
	//判断当前设备是否为手机
	public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }
	/**
	 * 获取设备的系统版本号
	 */
	public static int getDeviceSDK() {
		int sdk = android.os.Build.VERSION.SDK_INT;
		LogUtil.i("设备版本：" + sdk);
		return sdk;
	}

	/**
	 * 获取设备的型号
	 */
	public static String getDeviceName() {
		String model = android.os.Build.MODEL;
		LogUtil.i("设备型号：" + model);
		return model;
	}

	public static String getMacAddress(Context context) {
		String macAddress;
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		macAddress = info.getMacAddress();
		if (null == macAddress) {
			return "";
		}
		macAddress = macAddress.replace(":", "");
		return macAddress;
	}
	//判断当前手机是否处于锁屏(睡眠)状态
	public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }
}
