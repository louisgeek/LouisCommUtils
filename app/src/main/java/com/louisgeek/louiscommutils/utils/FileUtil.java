/**
 * @version 1.1  2015年4月12日
 */
package com.louisgeek.louiscommutils.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sunstar.agronet.activity.MainActivity;
import com.sunstar.agronet.activity.UploadPhotoActivity;
import com.sunstar.agronet.custom.LouisApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;

/**
 * 文件操作工具类
 * 
 * @author louisgeek 2015年2月12日下午2:51:19
 */
public class FileUtil {

	private static final String TAG = "FileUtil";
	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";
	public static final int NO_LOGIN_FROM_PERSON_PHOTO = 1314520;

	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

	/**
	 * android上传文件到服务器 Base64
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param RequestURL
	 *            请求的rul
	 * @return 返回响应的内容
	 * @throws Exception
	 */
	public static String uploadFileHttpPostBase64(String filePath, String filename, String RequestURL)
			throws Exception {
		File file = new File(filePath);
		Bitmap bitmap = null;
		if (filePath != null && filePath.length() > 0) {
			bitmap = ImageUtil.zoomBitmapFromFileWithWidthHeight(file, 400, 400);
			return uploadFileHttpPostBase64Inner(bitmap, filename, RequestURL);
		} else {
			return null;
		}

	}

	public static String uploadFileHttpPostBase64(Bitmap bitmap, String filename, String RequestURL) throws Exception {
		if (bitmap != null) {
			bitmap = ImageUtil.zoomBitmapWithWidthHeight(bitmap, 400, 400);
			return uploadFileHttpPostBase64Inner(bitmap, filename, RequestURL);
		} else {
			return null;
		}

	}

	public static String uploadFileHttpPostBase64Inner(Bitmap bitmap, String filename, String RequestURL)
			throws Exception {
		byte[] imgBytes = ImageUtil.Bitmap2Bytes(bitmap);
		String Base64Str = Base64.encodeToString(imgBytes, Base64.DEFAULT);
		// base64ToBitmap(Base64Str, "XXX"+System.currentTimeMillis(),
		// "png");//存储图片
		String str = "";
		try {
			HttpPost httpRequest = new HttpPost(RequestURL);
			// ######httpRequest.setHeader("Cookie",
			// LouisApplication.generalCookieStr);
			/*
			 * NameValuePair实现请求参数的封装
			 */
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// params.add(new BasicNameValuePair("uid", uid));
			// 用户名密码
			params.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_LOGINID,
					LouisApplication.getAppNowUserLoginID()));
			params.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_PASSWORD,
					LouisApplication.getAppNowUserPassWord()));
			// params.add(new
			// BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_TYPE,LouisApplication.WEB_POST_USER_VALUE_TYPE));
			//
			params.add(new BasicNameValuePair("filename", filename));
			// params.add(new
			// BasicNameValuePair("base64img",URLEncoder.encode(Base64Str,"UTF-8")));//下面转换
			params.add(new BasicNameValuePair("base64img", Base64Str));
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				Log.d("Code", httpResponse.getStatusLine().toString());
				return "Code111";
			} else {
				HttpEntity httpEntity = httpResponse.getEntity();
				str = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("-------------------" + str);
				if (str.equals("nologin")) {
					MainActivity.instance.generalHandler.sendEmptyMessage(NO_LOGIN_FROM_PERSON_PHOTO);
					return "userNoLogin";
				} else {
					return SUCCESS;
				}

			}
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace())
				Log.d("Exception", s.toString());
			Log.d("Exception", e.getLocalizedMessage());
			return "Exception222";
		}
	}

	/**
	 * 
	 * @param base64Data
	 * @param imgName
	 * @param imgFormat
	 *            图片格式
	 */
	public static void base64ToBitmap(String base64Data, String imgName, String imgFormat) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

		File myCaptureFile = new File(Environment.getExternalStorageDirectory(), imgName + "." + imgFormat);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(myCaptureFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isTu = bitmap.compress(CompressFormat.PNG, 100, fos);
		if (isTu) {
			// fos.notifyAll();
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param RequestURL
	 *            请求的rul
	 * @return 返回响应的内容
	 */
	public static String uploadFile(File file, String RequestURL) {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			if (file != null) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				OutputStream outputSteam = conn.getOutputStream();

				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png
				 */

				sb.append(
						"Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				Log.d(TAG, "response code:" + res);
				if (res == 200) {
					return SUCCESS;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}

	// 从路径获取文件名
	public static String getFileNameFromPathAndName(String pathAndName) {
		int start = pathAndName.lastIndexOf("/");
		int end = pathAndName.lastIndexOf(".");
		if (start != -1 && end != -1) {
			return pathAndName.substring(start + 1, end);
		} else {
			return null;
		}
	}

	// 通过路径生成Base64文件
	public static String getBase64FileFromPath(String path) {
		String base64 = "";
		try {
			File file = new File(path);
			byte[] buffer = new byte[(int) file.length() + 100];
			@SuppressWarnings("resource")
			int length = new FileInputStream(file).read(buffer);
			base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}
	/*    *//**
			 * 取得size大小单位显示
			 *//*
				 * public static String getGbMbKbBString(long size) { // TODO
				 * 自动生成的方法存根 String sizeStr=""; DecimalFormat df = new
				 * DecimalFormat("#.00"); if (size < 1024) { sizeStr =
				 * df.format((double) size) + "B"; } else if (size < 1048576) {
				 * sizeStr = df.format((double) size / 1024) + "K"; } else if
				 * (size < 1073741824) { sizeStr = df.format((double) size /
				 * 1048576) + "M"; } else { sizeStr = df.format((double) size /
				 * 1073741824) + "G"; } return sizeStr; }
				 */

	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 */
	public static long getFolderSizeOld(File file) {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 递归删除 文件/文件夹
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {

		// Log.i(TAG, "delete file path=" + file.getAbsolutePath());
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
		}
	}
	/*	    *//**
				 * File webviewDBDir = new
				 * File(context.getFilesDir().getAbsolutePath().replace("files",
				 * "databases")+"/webview.db");
				 *//*
				 * public static long getFileCacheSize(Context context, String
				 * cacaheDirname) { long size = 0;// 字节 // 自定义cacaheDirname File
				 * appCacheDir = new
				 * File(context.getFilesDir().getAbsolutePath() +
				 * cacaheDirname); getFolderSize(appCacheDir); return size; }
				 * public static long getFileCacheSize(Context context, File
				 * appCacheFile) { long size = 0;// 字节 if
				 * (appCacheFile.exists()) { size +=getFolderSize(appCacheFile);
				 * } return size; }
				 */

	/**
	 * 获取文件指定文件的指定单位的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sizeType
	 *            获取大小的类型1为B、2为KB、3为MB、4为GB
	 * @return double值的大小
	 */
	public static double getAutoFileOrFilesSize(String filePath, int sizeType) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFolderSize(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize, sizeType);
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFolderSize(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize);
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFolderSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFolderSize(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小 取得size大小单位显示
	 * 
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	public long getListCount(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getListCount(flist[i]);
				size--;
			}
		}
		return size;

	}

	/**
	 * 转换文件大小,指定转换的类型
	 * 
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	public static double FormetFileSize(long fileS, int sizeType) {
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
		case SIZETYPE_B:
			fileSizeLong = Double.valueOf(df.format((double) fileS));
			break;
		case SIZETYPE_KB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
			break;
		case SIZETYPE_MB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
			break;
		case SIZETYPE_GB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
			break;
		default:
			break;
		}
		return fileSizeLong;
	}
}
