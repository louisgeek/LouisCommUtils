/**
 * @version 1.0  2015年1月27日
 */
package com.louisgeek.louiscommutils.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.sunstar.agronet.activity.ChangePwdActivity;
import com.sunstar.agronet.custom.LouisApplication;

import android.util.Log;

/**
 * Http请求工具类
 * 
 * @author louisgeek 2015年1月27日下午3:01:00
 */
public class HttpUtil {
	private static final int TIMEOUT_IN_MILLIONS = 5000;

	public interface CallBack {
		void onRequestComplete(String result);
	}


	public static String doHttpConn4Louis(String urlStr) throws Exception{
		String resultJson = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(15 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", urlStr);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");

			conn.connect();
			if (conn.getResponseCode() == 200) {
				// 获取响应的输入流对象
				InputStream is = conn.getInputStream();
				// 创建字节输出流对象
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				// 定义读取的长度
				int len = 0;
				// 定义缓冲区
				byte buffer[] = new byte[1024];
				// 按照缓冲区的大小，循环读取
				while ((len = is.read(buffer)) != -1) {
					// 根据读取的长度写入到os对象中
					baos.write(buffer, 0, len);
				}
				// 释放资源
				is.close();
				baos.close();
				// 返回字符串
				resultJson = new String(baos.toByteArray());
			} else {
				throw new RuntimeException("url that you conneted has error ...");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultJson;
	}

	/**
	 * 异步的Get请求
	 * 
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * 
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack,
			final String cookieStr) throws Exception {
		new Thread() {
			public void run() {
				try {
					String result = doPost(urlStr, params, cookieStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

	}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}

		return null;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, String param, String cookieStr) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Cookie", cookieStr);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");

			// conn.setUseCaches(false); //2015-3-5 21:41:17
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

			if (param != null && !param.trim().equals("")) {
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 */
	public static void doMyGet(String GET_URL) throws IOException {
		// TODO 自动生成的方法存根
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL = GET_URL + "?username=" + URLEncoder.encode("fat manxxxxxxxxxxxxxxxxxxxxxxxxx", "utf-8");
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		System.out.println("Contents of get request");
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println("Contents of get request ends");
	}

	/**
	 * @return
	 * 
	 */
	public static String doMyPost(String POST_URL, String cookieStr) throws IOException {
		// TODO 自动生成的方法存根
		// Post请求的url，与get不同的是不需要带参数
		URL postUrl = new URL(POST_URL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setRequestProperty("Cookie", cookieStr);
		LogUtil.i("set进去的Cookie" + cookieStr);
		// Output to the connection. Default is
		// false, set to true because post
		// method must write something to the
		// connection
		// 设置是否向connection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true
		connection.setDoOutput(true);
		// Read from the connection. Default is true.
		connection.setDoInput(true);

		// Set the post method. Default is GET
		connection.setRequestMethod("POST");
		// Post cannot use caches
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// This method takes effects to
		// every instances of this class.
		// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
		// connection.setFollowRedirects(true);

		// This methods only
		// takes effacts to this
		// instance.
		// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);

		// Set the content type to urlencoded,
		// because we will write
		// some URL-encoded content to the
		// connection. Settings above must be set before connect!
		// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
		// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		// 进行编码
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		// 要注意的是connection.getOutputStream会隐含的进行connect。
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		// The URL-encoded contend
		// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
		String content = "Password=" + URLEncoder.encode("abcxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "utf-8");
		// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
		out.writeBytes(content);

		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		System.out.println("Contents of post request");
		while ((line = reader.readLine()) != null) {
			System.out.println("line===" + line);
			return line;
		}
		System.out.println("Contents of post request ends");
		reader.close();
		connection.disconnect();
		return null;
	}

	/**
	 * POST请求操作
	 * 
	 * @param userName
	 * @param userPass
	 * @throws IOException
	 */
	public static String doLouisPostBackStrWithLoginID(String specUrl) throws IOException {
		// 请求的地址
		// 根据地址创建URL对象
		URL url = new URL(specUrl);
		// 根据URL对象打开链接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置请求的方式
		urlConnection.setRequestMethod("POST");
		// 设置请求的超时时间
		urlConnection.setReadTimeout(20000);
		urlConnection.setConnectTimeout(20000);
		// 传递的数据 和queryString 格式其实是一样的
		/*
		 * String data = "username=" + URLEncoder.encode(userName, "UTF-8") +
		 * "&userpass=" + URLEncoder.encode(userPass, "UTF-8");
		 */
		String data = LouisApplication.WEB_POST_USER_KEY_LOGINID + "="
				+ URLEncoder.encode(LouisApplication.getAppNowUserLoginID(), "UTF-8") + "&"
				+ LouisApplication.WEB_POST_USER_KEY_PASSWORD + "="
				+ URLEncoder.encode(LouisApplication.getAppNowUserPassWord(), "UTF-8");
		// 设置请求的头
		urlConnection.setRequestProperty("Connection", "keep-alive");
		// 设置请求的头
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 设置请求的头
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
		// 设置请求的头
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

		urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
		urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
										// setDoInput的默认值就是true
		// 获取输出流
		OutputStream os = urlConnection.getOutputStream();
		os.write(data.getBytes());
		os.flush();
		if (urlConnection.getResponseCode() == 200) {
			// 获取响应的输入流对象
			InputStream is = urlConnection.getInputStream();
			// 创建字节输出流对象
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 定义读取的长度
			int len = 0;
			// 定义缓冲区
			byte buffer[] = new byte[1024];
			// 按照缓冲区的大小，循环读取
			while ((len = is.read(buffer)) != -1) {
				// 根据读取的长度写入到os对象中
				baos.write(buffer, 0, len);
			}
			// 释放资源
			is.close();
			baos.close();
			// 返回字符串
			String result = new String(baos.toByteArray());

			return result;

		} else {
			LogUtil.e("链接失败.........");
			// System.out.println("");
			return null;
		}

	}

	/**
	 * POST请求操作 例子 待改
	 * 
	 * @param userName
	 * @param userPass
	 */
	public void doLouisPost(String specUrl, String userName, String userPass) {

		try {

			// 请求的地址
			// 根据地址创建URL对象
			URL url = new URL(specUrl);
			// 根据URL对象打开链接
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// 设置请求的方式
			urlConnection.setRequestMethod("POST");
			// 设置请求的超时时间
			urlConnection.setReadTimeout(20000);
			urlConnection.setConnectTimeout(20000);
			// 传递的数据 和queryString 格式其实是一样的
			String data = "username=" + URLEncoder.encode(userName, "UTF-8") + "&userpass="
					+ URLEncoder.encode(userPass, "UTF-8");
			// 设置请求的头
			urlConnection.setRequestProperty("Connection", "keep-alive");
			// 设置请求的头
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置请求的头
			urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
			// 设置请求的头
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

			urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
			urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
											// setDoInput的默认值就是true
			// 获取输出流
			OutputStream os = urlConnection.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			if (urlConnection.getResponseCode() == 200) {
				// 获取响应的输入流对象
				InputStream is = urlConnection.getInputStream();
				// 创建字节输出流对象
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				// 定义读取的长度
				int len = 0;
				// 定义缓冲区
				byte buffer[] = new byte[1024];
				// 按照缓冲区的大小，循环读取
				while ((len = is.read(buffer)) != -1) {
					// 根据读取的长度写入到os对象中
					baos.write(buffer, 0, len);
				}
				// 释放资源
				is.close();
				baos.close();
				// 返回字符串
				String result = new String(baos.toByteArray());

			} else {
				System.out.println("链接失败.........");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
