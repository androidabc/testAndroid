package com.itheima.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * md5���ܷ���
	 * 
	 * @param password
	 *            ����
	 * @return ����
	 */
	public static String encode(String password) {

		// ��ϢժҪ��
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			for (byte b : result) {
				// ÿ��byte��������
				int number = b & 0xff;// ������׼���ܣ�����ѧ������
				// ת����16����
				String numberStr = Integer.toHexString(number);
				if (numberStr.length() == 1) {
					buffer.append("0");

				}
				buffer.append(numberStr);
			}
			// �ͱ�׼��md5���ܵĽ��
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

}
