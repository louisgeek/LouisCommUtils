package com.louisgeek.louiscommutils.utils;

import com.sunstar.agronet.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 网络设置工具类
 * 
 * @Author louisgeek 2015-1-15 9:11:03
 */
public class NetWorkUtil {
	private static Context context_networkUtil;
	private static int openSettingType_networkUtil;
	private static boolean update = false;
	private static Intent intent;
	public final static int CONNECTED_KEY_MOBILE = 100;
	public final static int CONNECTED_KEY_WIFI = 200;

	public NetWorkUtil(Context context) {
		// super();
		context_networkUtil = context;

	}

	/**
	 * 设置网络<br>
	 * 需要传入上下文对象activity
	 *
	 * @param context
	 * @return void
	 */
	public static void setNetwork(final Context context, final int openSettingType, String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					goToPhoneNetSetting(context, openSettingType);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					ToastUtil.showShort(context_networkUtil, "跳转失败，请去设置里手动打开");
					e.printStackTrace();
					dialog.cancel();
				}
			}
		});
		builder.setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}

	public static void goToPhoneNetSetting(Context context, int openSettingType)throws Exception {
		// TODO Auto-generated method stub
		// Intent intent = new
		// Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
		context_networkUtil = context;//
		openSettingType_networkUtil = openSettingType;
		if (openSettingType_networkUtil == CONNECTED_KEY_MOBILE) {
			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else if (openSettingType_networkUtil == CONNECTED_KEY_WIFI) {
			intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
		}
		context_networkUtil.startActivity(intent);

	}

	/**
	 * 网络是否可用<br>
	 * 需要传入上下文对象this.getApplicationContext()
	 *
	 * @param context
	 * @return boolean
	 */
	public static boolean isNetworkAvailableOld(Context context) {
		boolean isHas = false;
		// Context context = getApplicationContext();
		ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			isHas = false;
		} else// get all network info
		{
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == State.CONNECTED) {
						isHas = true;
						break;
					}
				}
			}
		}
		return isHas;
	}

	/**
	 * 检查当前网络是否可用 getApplicationContext()
	 * 
	 * @param context
	 * @return
	 */

	public static boolean isNetworkAvailable(Context context) {
		// Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					System.out.println(i + "==状态===" + networkInfo[i].getState());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == State.CONNECTED) {
						System.out.println(i + "louis=o===类型===" + networkInfo[i].getTypeName());

						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isNetworkConnected(Context context) {
		boolean isHas = false;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (!update && (wifi.isConnected() || gprs.isConnected()))// 有网但是还没刷新
		{
			update = true;
			System.out.println("111111111111111");
			isHas = true;
		} else if (!wifi.isConnected() && !gprs.isConnected())// 没网
		{
			update = false;
			isHas = false;
		}

		return isHas;
	}

	/**
	 * Wifi是否连接<br>
	 * 需要传入上下文对象this.getApplicationContext()
	 *
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 数据网络是否连接<br>
	 * 需要传入上下文对象this.getApplicationContext()
	 *
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取连接网络的类型<br>
	 * 需要传入上下文对象this.getApplicationContext()
	 *
	 * @param context
	 * @return 整型网络标识(zhi)
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	public static String GetNetworkType(Context context) {
		String strNetworkType = "";

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				strNetworkType = "WIFI";
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				String _strSubTypeName = networkInfo.getSubtypeName();

				Log.e("NetworkType", "Network getSubtypeName : " + _strSubTypeName);

				// TD-SCDMA networkType is 17
				int networkType = networkInfo.getSubtype();
				switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
															// 11
					strNetworkType = "2G";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
															// 14
				case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
															// 12
				case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
															// 15
					strNetworkType = "3G";
					break;
				case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
														// 13
					strNetworkType = "4G";
					break;
				default:
					// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
					if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA")
							|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
						strNetworkType = "3G";
					} else {
						strNetworkType = _strSubTypeName;
					}

					break;
				}

				Log.e("NetworkType", "Network getSubtype : " + Integer.valueOf(networkType).toString());
			}
		}

		Log.e("NetworkType", "Network Type : " + strNetworkType);

		return strNetworkType;
	}

	public static boolean isConnected(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		NetworkInfo netInfo = (NetworkInfo) bundle.get(ConnectivityManager.EXTRA_NETWORK_INFO);
		State state = netInfo.getState();
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (state == State.CONNECTED && activeNetInfo != null
				&& activeNetInfo.getType() != netInfo.getType()) {
			LogUtil.i("louis==isConnected");
			return true;

		} else {
			LogUtil.i("louis==isConnected_NOT");
			return false;
		}
	}

	// 只判断网络连接是否可用
	public static boolean isConnectionAvailable(Context cotext) {
		boolean isConnectionFail = true;
		ConnectivityManager connectivityManager = (ConnectivityManager) cotext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
				isConnectionFail = true;
			} else {
				isConnectionFail = false;
			}
		} else {
			LogUtil.i("louis==Can't get connectivitManager");
			// Log.e("Can't get connectivitManager");
		}
		return isConnectionFail;
	}

	public static boolean isNetworkAvailableNew(Context context) {
		ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean flag = false;
		if ((mWifi != null) && ((mWifi.isAvailable()) || (mMobile.isAvailable()))) {
			if ((mWifi.isConnected()) || (mMobile.isConnected())) {
				flag = true;
			}
		}
		return flag;
	}

	public static boolean isNetworkAvailableNew4Wifi(Context context) {
		ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean flag = false;
		if ((mWifi != null) && (mWifi.isAvailable())) {
			if (mWifi.isConnected()) {
				flag = true;
			}
		}
		return flag;
	}

	public static boolean isNetworkAvailableNew4Mobile(Context context) {
		ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean flag = false;
		if (mMobile.isAvailable()) {
			if (mMobile.isConnected()) {
				flag = true;
			}
		}
		return flag;
	}
}
