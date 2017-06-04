package com.example.haut_client;

import android.annotation.SuppressLint;
import android.hardware.Camera.Parameters;

import java.io.*;
import java.net.*;

public class ClientAction {

	public static String mac_address = "";
	public static boolean isLinked = false;

	// 登录
	// @SuppressLint("NewApi")
	public static String Login(String usr, char[] pwd, String ac_id)
			throws Exception {
		
		//密码加密，加密之后再进行转码，默认加密秘钥是1234567890
		String urlencode_pwd =URLEncoder.encode(pwdEncode(String.valueOf(pwd),"1234567890"),"UTF-8");
		String data = "action=login&username=" + usrEncode(usr) + "&password="
				+ urlencode_pwd
				+ "&drop=0&pop=1&type=2&n=117&mbytes=0&minutes=0&ac_id="
				+ ac_id + "&mac=" + mac_address;

		// 登录
		String response = HttpPost(
				"http://172.16.154.130:69/cgi-bin/srun_portal", data);
		if (response.contains("login_ok")) {
			isLinked = true;
			return "success";
		} else {
			isLinked = false;
			return response;
		}
	}

	// 此静态方法是发送登录信息给负责登录验证的服务器，得到服务器返回的数据buffer，返回字符串buffer
	public static String HttpPost(String url, String data) throws Exception {
		URL urlObj = new URL(null, url);
		URLConnection urlConnection = urlObj.openConnection();
		urlConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestProperty("Charset", "UTF-8");

		byte[] contentBytes = data.getBytes();
		httpURLConnection.setFixedLengthStreamingMode(contentBytes.length);

		OutputStream out = httpURLConnection.getOutputStream();

		
		try {
			out.write(contentBytes);
		} finally {
			out.flush();
		}

		// System.out.println("登录异常post3");
		// 这里buffer是接收用url传送给登录验证服务器返回的数据
		InputStream inputStream = httpURLConnection.getInputStream(); 
		int contentLength = httpURLConnection.getContentLength(); 
		contentLength = contentLength == -1 ? 4096 : contentLength; 
		byte[] buffer = new byte[contentLength]; 
		int offset = 0;
		while (true) {
			int remain = buffer.length - offset;
			if (remain <= 0) {
				int newSize = buffer.length * 2;
				byte[] newBuffer = new byte[newSize];
				System.arraycopy(buffer, 0, newBuffer, 0, offset);
				buffer = newBuffer;
				remain = buffer.length - offset;
			}
			int numRead = inputStream.read(buffer, offset, remain);
			if (numRead == -1) {
				break;
			}
			offset += numRead;
		} // System.out.println("登录异常post8");
		if (offset < buffer.length) {
			byte[] newBuffer = new byte[offset];
			System.arraycopy(buffer, 0, newBuffer, 0, offset);
			buffer = newBuffer;
		}
		return new String(buffer, "UTF-8");
	}

	// 获取登录信息
	public static String getUserInfo() {
		try {
			String response = HttpGet("http://172.16.154.130/cgi-bin/rad_user_info");
			if (!response.equals("not_online")) {
				// 已经登录过
				return "logined";
			} else {
				// 未登录
				isLinked = false;
				return "unlogin";
			}
		} catch (Exception e) {
			return "exception";
		}
	}

	// 得到当前在线信息，返回服务器返回的数据，，如果未登录则返回的数据时not_login
	public static String HttpGet(String url) throws Exception {
		// System.out.println("登录异常1");
		URL urlObj = new URL(null, url);
		// System.out.println("登录异常2");
		URLConnection urlConnection = urlObj.openConnection();
		// System.out.println("登录异常3");
		urlConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded"); // System.out.println("登录异常4");
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection; // System.out.println("登录异常5");
		httpURLConnection.setRequestMethod("GET"); // System.out.println("登录异常6");
		InputStream inputStream = urlConnection.getInputStream(); // System.out.println("登录异常7");
		int contentLength = urlConnection.getContentLength(); // System.out.println("登录异常8");
		contentLength = contentLength == -1 ? 4096 : contentLength; // System.out.println("登录异常9");
		byte[] buffer = new byte[contentLength]; // System.out.println("登录异常10");
		int offset = 0;
		while (true) {
			int remain = buffer.length - offset;
			if (remain <= 0) {
				int newSize = buffer.length * 2;
				byte[] newBuffer = new byte[newSize];
				System.arraycopy(buffer, 0, newBuffer, 0, offset);
				buffer = newBuffer;
				remain = buffer.length - offset;
			}
			int numRead = inputStream.read(buffer, offset, remain);
			if (numRead == -1) {
				break;
			}
			offset += numRead;
		}
		if (offset < buffer.length) {
			byte[] newBuffer = new byte[offset];
			System.arraycopy(buffer, 0, newBuffer, 0, offset);
			buffer = newBuffer;
		}
		return new String(buffer, "UTF-8");
	}

	// 注销
	public static String disconnect(String usr) throws Exception {
		String response = HttpPost(
				"http://172.16.154.130:69/cgi-bin/srun_portal",
				"action=logout&ac_id=1&username=" + usr + "&mac=" + mac_address
						+ "&type=2");
		isLinked = false;
		return response;
	}

	// 用户名加密
	public static String usrEncode(String usr)
			throws UnsupportedEncodingException {
		String rtn = "{SRUN3}\r\n";
		char[] usr_arr = usr.toCharArray();
		for (int i = 0; i < usr_arr.length; ++i) {
			rtn += (char) ((int) usr_arr[i] + 4);
		}
		return rtn;

	}

	

	// 密码加密方法
	public static String pwdEncode(String password, String key) {
		String password_crypto = "";
		for (int i = 0; i < password.length(); ++i) {
			int ki = password.charAt(i)
					^ key.charAt(key.length() - i % key.length() - 1);

			String _l = (char) ((ki & 0x0f) + 0x36) + "";

			String _h = (char) ((ki >> 4 & 0x0f) + 0x63) + "";
			if (i % 2 == 0) {
				password_crypto += _l + _h;
			} else {
				password_crypto += _h + _l;
			}
		}
		return password_crypto;
	}

}
