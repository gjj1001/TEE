package com.tee686.https;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.NameValuePair;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.util.Log;

public class HttpUtils {
	// 网络连接部分
	public static String postByHttpURLConnection(String strUrl,
			NameValuePair... nameValuePairs) {
		return CustomHttpURLConnection.PostFromWebByHttpURLConnection(strUrl,
				nameValuePairs);
	}

	public static String getByHttpURLConnection(String strUrl,
			NameValuePair... nameValuePairs) {
		return CustomHttpURLConnection.GetFromWebByHttpUrlConnection(strUrl,
				nameValuePairs);
	}

	public static String postByHttpClient(Context context,String strUrl,
			NameValuePair... nameValuePairs) {
		return CustomHttpClient.PostFromWebByHttpClient(context,strUrl, nameValuePairs);
	}

	public static String getByHttpClient(Context context,String strUrl,
			NameValuePair... nameValuePairs) throws Exception {
		return CustomHttpClient.getFromWebByHttpClient(context,strUrl, nameValuePairs);
	}

	/**把实例对象转换成json数据格式传输
	 * @param strUrl 请求地址
	 * @param entity 实例对象
	 * @return 请求结果
	 */
	public static String postByHttpURLConnection(String strUrl, Object entity) {
		//json格式数据网络传输		
		StringBuffer result = new StringBuffer();		
		HttpURLConnection conn;
		try {
			byte[] data = new ObjectMapper().writeValueAsBytes(entity);
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(4000);
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type",	"text/plain; charset=UTF-8");
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			OutputStream out = conn.getOutputStream();
			out.write(data);
			out.flush();
			out.close();
			if (conn.getResponseCode() == 200) {
				byte[] connbuffer = new byte[1024];
				InputStream in = conn.getInputStream();
				while (in.read(connbuffer) != -1) {
					result.append(new String(connbuffer, "utf-8"));
				}
				String info = result.toString();
				in.close();
				conn.disconnect();
				return info;
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// ------------------------------------------------------------------------------------------
	// 网络连接判断
	// 判断是否有网络
//	public static boolean isNetworkAvailable(Context context) {
//		return NetWorkHelper.isNetworkAvailable(context);
//	}

	// 判断mobile网络是否可用
	public static boolean isMobileDataEnable(Context context) {
		String TAG = "httpUtils.isMobileDataEnable()";
		try {
			return NetWorkHelper.isMobileDataEnable(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// 判断wifi网络是否可用
	public static boolean isWifiDataEnable(Context context) {
		String TAG = "httpUtils.isWifiDataEnable()";
		try {
			return NetWorkHelper.isWifiDataEnable(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// 设置Mobile网络开关
	public static void setMobileDataEnabled(Context context, boolean enabled) {
		String TAG = "httpUtils.setMobileDataEnabled";
		try {
			NetWorkHelper.setMobileDataEnabled(context, enabled);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}

	// 判断是否为漫游
	public static boolean isNetworkRoaming(Context context) {
		return NetWorkHelper.isNetworkRoaming(context);
	}
}
