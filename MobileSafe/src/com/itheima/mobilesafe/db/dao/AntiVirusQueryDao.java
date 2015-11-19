package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntiVirusQueryDao {
	private static String path = "/data/data/com.itheima.mobilesafe/files/antivirus.db";

	/**
	 * ��ѯ���ݿ��Ƿ��ǲ���
	 * @param signatures ǩ����Ϣ
	 * @return ����null�������������������ֵ���ǲ���
	 */
	public static String isAntivirus(String signatures) {
		String result = null;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select desc from datable where md5 = ?", new String[]{signatures});
		if(cursor.moveToNext()){
			result = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return result;
		
	}

}
