package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommonNumberQueryDao {
	
	
	
	/**
	 * �õ����������
	 * 
	 */
	public static int getGroupCount(SQLiteDatabase db){
		int result = 0;
//		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor crusor = db.rawQuery("select count(*) from classlist", null);
		if(crusor.moveToNext()){
			result = crusor.getInt(0);
		}
		crusor.close();
//		db.close();
		return result;
	}
	
	
	/**
	 * �õ�ĳһ���к��ӵ�����
	 * 
	 */
	public static int getChildCount(int groupPosition,SQLiteDatabase db){
		int newgroupPosition = groupPosition+1;
		int result = 0;
//		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor crusor = db.rawQuery("select count(*) from table"+newgroupPosition, null);
		if(crusor.moveToNext()){
			result = crusor.getInt(0);
		}
		crusor.close();
//		db.close();
		return result;
	}
	
	
	/**
	 * �õ����������
	 * 
	 */
	public static String getGroupName(int groupPosition,SQLiteDatabase db){
		int newgroupPosition = groupPosition+1;
		String result = "";
//		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor crusor = db.rawQuery("select name from classlist where idx = ?", new String[]{newgroupPosition+""});
		if(crusor.moveToNext()){
			result = crusor.getString(0);
		}
		crusor.close();
//		db.close();
		return result;
	}
	
	
	
	/**
	 * �õ�ĳ��ĳһ�����ӵ�����
	 * 
	 */
	public static String getChildName(int groupPosition, int childPosition,SQLiteDatabase db){
		int newgroupPosition = groupPosition+1;
		int newchildPosition = childPosition +1;
//		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor crusor = db.rawQuery("select name,number from table"+newgroupPosition+" where _id = ?", new String[]{newchildPosition+""});
		String name = "";
		String number = "";
		if(crusor.moveToNext()){
			name = crusor.getString(0);
			number= crusor.getString(1);
		}
		crusor.close();
//		db.close();
		return name+"\n"+number;
	}

	

}
