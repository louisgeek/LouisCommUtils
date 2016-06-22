/**
 * @version 1.1  2015年4月23日
 */
package com.louisgeek.louiscommutils.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Base64;

import com.sunstar.agronet.bean.ChannelItemBean;
import com.sunstar.agronet.bean.CountriesBean;
import com.sunstar.agronet.custom.LouisApplication;

/**
 * 流操作工具类
 * @author louisgeek
 * 2015年1月23日下午1:44:58 
 */
public  class StreamUtil {
	   private final static String UserId = "agronet";
     	
     	private final static String UserPassword = "lksdfsdG3X0h8T9";
	 /**
     * 读取流中的数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readByteArray(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        //如果字节流中的数据不等于-1，就说明一直有，然后循环读出
        while( (len=inStream.read(buffer)) !=-1){
            //将读出的数据放入内存中
            outputStream.write(buffer);
            
        }
        inStream.close();
        return outputStream.toByteArray();
    }
/*	//把Stream转换成String
 	public static String getStringFromStream(InputStream is) {
 		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;

			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "/n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();	
 	}*/
    
	/**
	 * 文本的写入操作
	 * @param filePath   文件路径。 加文件名 <br> 
	 *                 例如：../a/a.txt
	 * @param content  写入内容
	 */
	public static void writeOutputStream(String filePath, String content) {
		BufferedWriter bufw = null;
		try {
			bufw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath)));
			bufw.write(content);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (bufw != null) {
				try {
					bufw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    /**
     * 流的读取操作 path
     * @param path  
     * @return String
     */
	public static String getStringFromInputStreamByPath(String path) {
		BufferedReader bufr = null;
		try {
			bufr = new BufferedReader(new InputStreamReader(
					new FileInputStream(path)));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = bufr.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufr != null) {
				try {
					bufr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
  /**
   * 获取url服务器端格式的数据InputStream
   * @param path
   * @param cookieStr
   * @return
   * @throws Exception
   */
    public static InputStream getInStreamDateByPathUrl(String path,String cookieStr) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
       // conn.setRequestProperty("Cookie", "jsessionid=xxxxx;param1=a"); 
        conn.setRequestProperty("Cookie", cookieStr); 
      
        
        if(conn.getResponseCode()==200){
            InputStream inStream=conn.getInputStream();
            return inStream;
        }
        return null;
    }
	 /** 
     * POST请求操作  例子
	 * @return 
     *  
     */  
    public static InputStream getLouisInStreamDateByPathUrl(String specUrl) {  
  
        try {  
  
            // 请求的地址  
            // 根据地址创建URL对象  
            URL url = new URL(specUrl);  
            // 根据URL对象打开链接  
            HttpURLConnection urlConnection = (HttpURLConnection) url  
                    .openConnection();  
            // 设置请求的方式  
            urlConnection.setRequestMethod("POST");  
            // 设置请求的超时时间  
            urlConnection.setReadTimeout(20000);  
            urlConnection.setConnectTimeout(20000);  
            // 传递的数据  和queryString 格式其实是一样的
          /*  String data = "username=" + URLEncoder.encode(userName, "UTF-8")  
                    + "&userpass=" + URLEncoder.encode(userPass, "UTF-8");  */
            String data = LouisApplication.WEB_POST_USER_KEY_LOGINID+"=" + URLEncoder.encode(LouisApplication.getAppNowUserLoginID(), "UTF-8")  
                    + "&"+LouisApplication.WEB_POST_USER_KEY_PASSWORD+"=" + URLEncoder.encode(LouisApplication.getAppNowUserPassWord(), "UTF-8"); 
        
            // 设置请求的头  
            urlConnection.setRequestProperty("Connection", "keep-alive");  
            // 设置请求的头  
            urlConnection.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            // 设置请求的头  
            urlConnection.setRequestProperty("Content-Length",  
                    String.valueOf(data.getBytes().length));  
            // 设置请求的头  
            urlConnection  
                    .setRequestProperty("User-Agent",  
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  
  
            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出  
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入  
                                            //setDoInput的默认值就是true  
            //获取输出流  
            OutputStream os = urlConnection.getOutputStream();  
            os.write(data.getBytes());  
            os.flush();  
            if (urlConnection.getResponseCode() == 200) {  
                // 获取响应的输入流对象  
                InputStream is = urlConnection.getInputStream();  
              
                return is;
             
  
            } else {  
                LogUtil.e("链接失败.........code="+urlConnection.getResponseCode());  
                return null;
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;
        }  
    }
      /**
       * HttpClient修改密码  [登录修改待验证]
       * @param url
       * @param json
       * @param cookieStr
       * @return
       */
      public static String postChangePwdByHttpClientPathUrl(String url,String password){  
      	String result="";
          HttpClient client = new DefaultHttpClient();  
          HttpPost post = new HttpPost(url);  
          //####post.setHeader("Cookie", cookieStr);
          try {  
       // 为httpPost设置HttpEntity对象  
          List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
          //用户名密码
          parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_LOGINID,LouisApplication.getAppNowUserLoginID()));  
          //parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_PASSWORD,LouisApplication.getAppNowUserPassWord()));  
        //  parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_TYPE,LouisApplication.WEB_POST_USER_VALUE_TYPE));  
          //
          parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_PASSWORD,password));  
          HttpEntity entityOld = new UrlEncodedFormEntity(parameters);  
       
 
              post.setEntity(entityOld);  
                 
               HttpResponse res = client.execute(post);  
               if(res.getStatusLine() != null && res.getStatusLine().getStatusCode() == 200){  
                   HttpEntity entity = res.getEntity(); 
                   if (entity != null) {
                   	/*Toast.makeText(
                   	Main.this,
                   	"服务器处理返回结果：" + getInputStream(entity.getContent()),
                   	Toast.LENGTH_SHORT).show();*/
                  	 result=getStringFromInputStream(entity.getContent());//返回是服务器定义的  true
                   		//System.out.println("louis==22服务器处理返回结果：" + getInputStream(entity.getContent()));
                   	} else {
                   		result="louis==222没有返回相关数据";
                   	//Toast.makeText(Main.this, "没有返回相关数据", Toast.LENGTH_SHORT).show();
                   	System.out.println("louis==222没有返回相关数据");
                   	
                   	}
                  // String charset = EntityUtils.getContentCharSet(entity);  
                  // response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));  
               }  else {
              	 result="louis==222发送失败，可能服务器忙，请稍后再试"+res.getStatusLine().getStatusCode();
              	 System.out.println("louis==222发送失败，可能服务器忙，请稍后再试"+res.getStatusLine().getStatusCode());
  			}
           } catch (Exception e) {  
               throw new RuntimeException(e);  
           }  
          return result;
       }  

    /**
     * 获取  新cookie  改变夜间模式
     * @param path
     * @param cookieStr
     * @return
     * @throws Exception
     */
      public static String getCookieByHttpURLConnFromPathUrl(String path,String oldCookie) throws Exception{
    	  String newCookie="";
    	  String cookieStr = "";  
          String cookieVal = "";  
          String key = null; 
          URL url = new URL(path);
          HttpURLConnection conn = (HttpURLConnection)url.openConnection();
          conn.setConnectTimeout(5000);
          conn.setRequestMethod("GET");
         // conn.setRequestProperty("Cookie", "jsessionid=xxxxx;param1=a"); 
          conn.setRequestProperty("Cookie", oldCookie); 
         
          if(conn.getResponseCode()==200){
        	  InputStream inStream=conn.getInputStream();
        	  
        	/*//获取cookie 方法1
        	  Map<String,List<String>> map=conn.getHeaderFields(); 
        	  Set<String> set=map.keySet(); 
        	  for (Iterator iterator = set.iterator();iterator.hasNext();) 
        	  { 
        		  String key = (String)iterator.next(); 
        	  if (key!=null&&key.equals("Set-Cookie")) 
        	  { 
        		  System.out.println("key=" + key+",开始获取cookie"); 
        	  List<String> list = map.get(key); 
        	  StringBuilder builder = new StringBuilder(); 
        	  for (String str : list)
        	  { 
        		  builder.append(str.substring(0, str.indexOf(";")+1)); 
        	  } 
        	  newCookie=builder.toString();
        	  }
        	  }*/
        	
        	//取cookie   方法2
              for(int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++){  
                  if(key.equalsIgnoreCase("set-cookie")){  
                      cookieVal = conn.getHeaderField(i);  
                      cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
                      cookieStr = cookieStr + cookieVal + ";";  
                  }  
              }  
          }
          newCookie=cookieStr;
          LogUtil.i("newCookie====="+newCookie);
          return newCookie;
      }
    /**
     * 流的读取操作
     * @param inputStream
     * @return
     * @throws IOException
     */
      public static String getStringFromInputStream(InputStream inputStream) throws IOException {
    	if (inputStream == null)
    	return null;
    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
    	int len = 0;
    	byte[] buf = new byte[1024];
    	while ((len = inputStream.read(buf)) != -1) {
    	bout.write(buf, 0, len);
    	}
    	inputStream.close();
    	return URLDecoder.decode(new String(bout.toByteArray()), "utf-8");
    	}
 	 /** 
       * POST请求操作
       *  
       * @param userName 
       * @param userPass 
       */  
      public static String doLouisPostSendJsonToServer(String specUrl,String jsonStr) {  
    	  // 
      	String jsonBase64 =Base64.encodeToString(jsonStr.getBytes(), Base64.NO_WRAP);//转换不换行 //base64编码  web站解码
          try {  
    
              // 请求的地址  
              // 根据地址创建URL对象  
              URL url = new URL(specUrl);  
              // 根据URL对象打开链接  
              HttpURLConnection urlConnection = (HttpURLConnection) url  
                      .openConnection();  
              // 设置请求的方式  
              urlConnection.setRequestMethod("POST");  
              // 设置请求的超时时间  
              urlConnection.setReadTimeout(20000);  
              urlConnection.setConnectTimeout(20000);  
              // 传递的数据  和queryString 格式其实是一样的
            /*  String data = "username=" + URLEncoder.encode(userName, "UTF-8")  
                      + "&userpass=" + URLEncoder.encode(userPass, "UTF-8");  */
              String data = LouisApplication.WEB_POST_USER_KEY_LOGINID+"=" + URLEncoder.encode(LouisApplication.getAppNowUserLoginID(), "UTF-8")  
                      + "&"+LouisApplication.WEB_POST_USER_KEY_PASSWORD+"=" + URLEncoder.encode(LouisApplication.getAppNowUserPassWord(), "UTF-8")
                      +"&jsonBase64=" + URLEncoder.encode(jsonBase64, "UTF-8"); 
              /**
               * map.put("UserId", UserId);
		map.put("UserPassword",MD5Util.encode(UserId+"UZ"+UserPassword+"UZ"+CurrentTime) );
		map.put("CurrentTime",CurrentTime );
               */
           
//              // 设置自定义的请求的头  
//              urlConnection.setRequestProperty("UserId", UserId);  
//              String CurrentTime=DateUtil.getZhongGuoTime();
//              urlConnection.setRequestProperty("UserPassword", MD5Util.encode(UserId+"UZ"+UserPassword+"UZ"+CurrentTime));  
//              urlConnection.setRequestProperty("CurrentTime", CurrentTime);  
              
              // 设置请求的头  
              urlConnection.setRequestProperty("Connection", "keep-alive");  
              // 设置请求的头  
              urlConnection.setRequestProperty("Content-Type",  
                      "application/x-www-form-urlencoded");  
              // 设置请求的头  
              urlConnection.setRequestProperty("Content-Length",  
                      String.valueOf(data.getBytes().length));  
              // 设置请求的头  
              urlConnection  
                      .setRequestProperty("User-Agent",  
                              "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  
    
              urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出  
              urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入  
                                              //setDoInput的默认值就是true  
              //获取输出流  
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
                  System.out.println("链接失败.........");  
                  return null;
              }  
          } catch (Exception e) {  
              e.printStackTrace();  
              return null;  
          }  
      }
      
    /**
     * HttpClient   HttpClient   HttpClient [登录修改待验证]
     * @param url
     * @param json
     * @param cookieStr
     * @return
     */
    public static String sendJsonToServerOld(String url,String json){  
    	String result="";
        HttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(url);  
        //####2015年8月19日14:04:42 post.setHeader("Cookie", cookieStr);
       // JSONObject response = null;  
        try {  
        	//待验证
        	 // 为httpPost设置HttpEntity对象  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            //用户名密码
            parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_LOGINID,LouisApplication.getAppNowUserLoginID()));  
            parameters.add(new BasicNameValuePair(LouisApplication.WEB_POST_USER_KEY_PASSWORD,LouisApplication.getAppNowUserPassWord()));  
            //
            HttpEntity entityOld = new UrlEncodedFormEntity(parameters);  
            post.setEntity(entityOld); 
            // 
        	String jsonBase64 =Base64.encodeToString(json.getBytes(), Base64.NO_WRAP);//转换不换行 //base64编码  web站解码
            StringEntity s = new StringEntity(jsonBase64);  
            s.setContentEncoding("UTF-8");  
            s.setContentType("application/json");  
            post.setEntity(s);  
            
            
            
             HttpResponse res = client.execute(post);  
             if(res.getStatusLine() != null && res.getStatusLine().getStatusCode() == 200){  
                 HttpEntity entity = res.getEntity(); 
                 if (entity != null) {
                 	/*Toast.makeText(
                 	Main.this,
                 	"服务器处理返回结果：" + getInputStream(entity.getContent()),
                 	Toast.LENGTH_SHORT).show();*/
                	 result=getStringFromInputStream(entity.getContent());//返回是服务器定义的  true
                 		//System.out.println("louis==22服务器处理返回结果：" + getInputStream(entity.getContent()));
                 	} else {
                 		result="louis==22没有返回相关数据";
                 	//Toast.makeText(Main.this, "没有返回相关数据", Toast.LENGTH_SHORT).show();
                 	System.out.println("louis==22没有返回相关数据");
                 	
                 	}
                // String charset = EntityUtils.getContentCharSet(entity);  
                // response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));  
             }  else {
            	 result="louis==222发送失败，可能服务器忙，请稍后再试"+res.getStatusLine().getStatusCode();
            	 System.out.println("louis==222发送失败，可能服务器忙，请稍后再试"+res.getStatusLine().getStatusCode());
			}
         } catch (Exception e) {  
             throw new RuntimeException(e);  
         }  
        return result;
     }  

    /**
     * 服务器接受乱码  暂时废弃
     * @param pathUrl
     * @param jsonStr
     */
    public static void sendJsonToServerTwo(String pathUrl,String jsonStr) {
    	HttpClient httpClient = new DefaultHttpClient();
    	try {
    	HttpPost httpPost = new HttpPost(pathUrl);
    	HttpParams httpParams = new BasicHttpParams();
    	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
    	//Gson gson = new Gson();
    	//String strgson.toJson(listDatas);
    	String str = jsonStr;
    	nameValuePair.add(new BasicNameValuePair("jsonString", URLEncoder
    	.encode(str, "utf-8")));
    	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
    	httpPost.setParams(httpParams);
    	/*Toast.makeText(Main.this, "发送的数据：\n" + str.toString(),
    	Toast.LENGTH_SHORT).show();*/
    	System.out.println("louis==发送的数据："+jsonStr);
    	httpClient.execute(httpPost);
    	HttpResponse response = httpClient.execute(httpPost);
    	StatusLine statusLine = response.getStatusLine();
    	if (statusLine != null && statusLine.getStatusCode() == 200) {
    	HttpEntity entity = response.getEntity();
    	if (entity != null) {
    	/*Toast.makeText(
    	Main.this,
    	"服务器处理返回结果：" + getInputStream(entity.getContent()),
    	Toast.LENGTH_SHORT).show();*/
    		System.out.println("louis==服务器处理返回结果：" + getStringFromInputStream(entity.getContent()));
    	} else {
    	//Toast.makeText(Main.this, "没有返回相关数据", Toast.LENGTH_SHORT).show();
    	System.out.println("louis==没有返回相关数据");
    	
    	}
    	} else {
    	/*Toast.makeText(Main.this, "发送失败，可能服务器忙，请稍后再试",
    	Toast.LENGTH_SHORT).show();*/
    	System.out.println("louis==发送失败，可能服务器忙，请稍后再试"+statusLine.getStatusCode());
    	}
    	} catch (Exception e) {
    	throw new RuntimeException(e);
    	}
    	}
}
