package com.koolyun.koolwait.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.protocol.HTTP;

import com.koolyun.koolwait.utils.MyLog;

public class NetworkCommunication {

	public static final String TEST_URL = "http://food.koolyun.cn/koolfood-mposapi";
	public static final String FORMAL_URL = "http://food.koolyun.com/koolfood-mposapi";
	public static final String TEMP_URL = "http://192.168.88.114:8080/easyfood-mposapi";
	public static final String URL = FORMAL_URL;

	/**
	 * 连接超时
	 */
	private static int connTimeout = 10000;

	/**
	 * 读写超时
	 */
	private static int rdTimeout = 10000;

	/**
	 * GET请求
	 * 
	 * @param path
	 *            接口路径
	 * @return
	 */
	public static String getRequest(String path) {
		HttpURLConnection httpURLConnection = null;
		String result = null;
		URL url = null;
		try {
			url = new URL(URL + path);
			// 判断是http请求还是https请求
			if (url.getProtocol().toLowerCase(Locale.CHINESE).equals("https")) {
				initHttpsRequest();
				httpURLConnection = (HttpsURLConnection) url.openConnection();

			} else {
				MyLog.d("Https Connection");
				httpURLConnection = (HttpURLConnection) url.openConnection();
			}
			MyLog.i(":url", url.toString());
//			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(connTimeout);
			httpURLConnection.setReadTimeout(rdTimeout);
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			if (httpURLConnection.getResponseCode() == 200) {
				result = readStream(httpURLConnection.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null)
				httpURLConnection.disconnect();
		}
		return result;
	}

	/**
	 * POST请求
	 * 
	 * @param path
	 *            接口路径
	 * @param json
	 *            JSON格式数据
	 * @return
	 */
	public static String postRequest(String path, String json) {
		HttpURLConnection httpURLConnection = null;
		String result = null;
		URL url = null;
		try {
			url = new URL(URL + path);
			// 判断是http请求还是https请求
			if (url.getProtocol().toLowerCase(Locale.CHINESE).equals("https")) {
				initHttpsRequest();
				httpURLConnection = (HttpsURLConnection) url.openConnection();

			} else {
				MyLog.d("Http Connection");
				httpURLConnection = (HttpURLConnection) url.openConnection();
			}

			MyLog.i(":url", url.toString());
			// httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(connTimeout);
			httpURLConnection.setReadTimeout(rdTimeout);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			// httpURLConnection.setRequestProperty("Accept",
			// "application/json");
			httpURLConnection.setRequestProperty("Charset", HTTP.UTF_8);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setChunkedStreamingMode(0);
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// httpURLConnection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			// httpURLConnection.connect();

			// OutputStream postOutputStream =
			// httpURLConnection.getOutputStream();

			writeStream(httpURLConnection.getOutputStream(), json);

			if (httpURLConnection.getResponseCode() == 200) {
				result = readStream(httpURLConnection.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null)
				httpURLConnection.disconnect();
		}
		return result;
	}

	// 设置网络超时
	public static void setTimeout(int connectTimeout, int readTimeout) {
		connTimeout = connectTimeout;
		rdTimeout = readTimeout;
	}

	private static String readStream(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		MyLog.i(":response", buffer.toString());
		return buffer.toString();
	}

	private static void writeStream(OutputStream os, String jsonString) throws IOException {
		DataOutputStream out = new DataOutputStream(os);
		out.write(jsonString.getBytes());
		MyLog.i(":request", jsonString);
		out.flush();
		out.close();
	}

	static TrustManager[] xtmArray = new MyTrustManager[] { new MyTrustManager() };
	private static void initHttpsRequest() {
		try {
			MyLog.d("Https Connection");
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, xtmArray, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class MyHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static class MyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {

		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
