package com.example.haut_client;

import android.annotation.SuppressLint;
import android.hardware.Camera.Parameters;

import java.io.*;
import java.net.*;

public class ClientAction {

	public static String mac_address = "";
	public static boolean isLinked = false;

	// ��¼
	// @SuppressLint("NewApi")
	public static String Login(String usr, char[] pwd, String ac_id)
			throws Exception {
		
		//������ܣ�����֮���ٽ���ת�룬Ĭ�ϼ�����Կ��1234567890
		String urlencode_pwd =URLEncoder.encode(pwdEncode(String.valueOf(pwd),"1234567890"),"UTF-8");
		String data = "action=login&username=" + usrEncode(usr) + "&password="
				+ urlencode_pwd
				+ "&drop=0&pop=1&type=2&n=117&mbytes=0&minutes=0&ac_id="
				+ ac_id + "&mac=" + mac_address;

		// ��¼
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

	// �˾�̬�����Ƿ��͵�¼��Ϣ�������¼��֤�ķ��������õ����������ص�����buffer�������ַ���buffer
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

		// System.out.println("��¼�쳣post3");
		// ����buffer�ǽ�����url���͸���¼��֤���������ص�����
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
		} // System.out.println("��¼�쳣post8");
		if (offset < buffer.length) {
			byte[] newBuffer = new byte[offset];
			System.arraycopy(buffer, 0, newBuffer, 0, offset);
			buffer = newBuffer;
		}
		return new String(buffer, "UTF-8");
	}

	// ��ȡ��¼��Ϣ
	public static String getUserInfo() {
		try {
			String response = HttpGet("http://172.16.154.130/cgi-bin/rad_user_info");
			if (!response.equals("not_online")) {
				// �Ѿ���¼��
				return "logined";
			} else {
				// δ��¼
				isLinked = false;
				return "unlogin";
			}
		} catch (Exception e) {
			return "exception";
		}
	}

	// �õ���ǰ������Ϣ�����ط��������ص����ݣ������δ��¼�򷵻ص�����ʱnot_login
	public static String HttpGet(String url) throws Exception {
		// System.out.println("��¼�쳣1");
		URL urlObj = new URL(null, url);
		// System.out.println("��¼�쳣2");
		URLConnection urlConnection = urlObj.openConnection();
		// System.out.println("��¼�쳣3");
		urlConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded"); // System.out.println("��¼�쳣4");
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection; // System.out.println("��¼�쳣5");
		httpURLConnection.setRequestMethod("GET"); // System.out.println("��¼�쳣6");
		InputStream inputStream = urlConnection.getInputStream(); // System.out.println("��¼�쳣7");
		int contentLength = urlConnection.getContentLength(); // System.out.println("��¼�쳣8");
		contentLength = contentLength == -1 ? 4096 : contentLength; // System.out.println("��¼�쳣9");
		byte[] buffer = new byte[contentLength]; // System.out.println("��¼�쳣10");
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

	// ע��
	public static String disconnect(String usr) throws Exception {
		String response = HttpPost(
				"http://172.16.154.130:69/cgi-bin/srun_portal",
				"action=logout&ac_id=1&username=" + usr + "&mac=" + mac_address
						+ "&type=2");
		isLinked = false;
		return response;
	}

	// �û�������
	public static String usrEncode(String usr)
			throws UnsupportedEncodingException {
		String rtn = "{SRUN3}\r\n";
		char[] usr_arr = usr.toCharArray();
		for (int i = 0; i < usr_arr.length; ++i) {
			rtn += (char) ((int) usr_arr[i] + 4);
		}
		return rtn;

	}

	

	// ������ܷ���
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
