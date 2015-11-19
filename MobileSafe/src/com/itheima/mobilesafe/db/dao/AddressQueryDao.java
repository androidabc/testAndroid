package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressQueryDao {
	private static String path = "/data/data/com.itheima.mobilesafe/files/address.db";

	/**
	 * ��������صĲ�ѯ
	 * 
	 * @param number
	 *            Ҫ��ѯ�ĵ绰����
	 * @return ���غ��������
	 */
	public static String queryAddress(String number) {
		String address = number;
		// file:///android_asset/address.db
		// �޷�ʶ�����·��
		// webview-�����
		SQLiteDatabase date = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);

		// �����ֻ��������������
		// �ֻ�������������ʽ 13,14,15,16,17,18���ֻ�����11λ

		if (number.matches("^1[345678]\\d{9}$")) {
			Cursor cursor = date
					.rawQuery(
							"select location from data2 where id= (select outkey from data1 where id= ?)",
							new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				String location = cursor.getString(0);
				address = location;
			}
		} else {

			switch (number.length()) {
			case 3:// 110,119,112,114
				address = "�˾��绰";

				break;
			case 4:// 5556
				address = "ģ����";

				break;
			case 5:// 10086
				address = "�ͷ��绰";

				break;

			case 6:// 10086
				address = "���غ���";

				break;

			case 7:// 10086
				address = "���غ���";

				break;
			case 8:// 10086
				address = "���غ���";

				break;

			default:

				if (number.startsWith("0") && number.length() >= 10) {
					// ��;:010-3888838
					Cursor cursor = date.rawQuery(
							"select location from data2 where area = ?",
							new String[] { number.substring(1, 3) });
					if (cursor.moveToNext()) {
						String location = cursor.getString(0);
						address = location.substring(0, location.length() - 2);

					}

					// ��;:0817-3888838
					cursor = date.rawQuery(
							"select location from data2 where area = ?",
							new String[] { number.substring(1, 4) });
					if (cursor.moveToNext()) {
						String location = cursor.getString(0);
						address = location.substring(0, location.length() - 2);
					}
					cursor.close();
				}
				break;
			}
		}

		return address;
	}

}