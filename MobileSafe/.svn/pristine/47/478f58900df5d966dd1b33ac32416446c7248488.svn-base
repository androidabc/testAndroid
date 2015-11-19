package com.itheima.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommonNumberQueryDao {
	
	
	
	/**
	 * 得到分组的总数
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
	 * 得到某一组中孩子的总数
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
	 * 得到分组的名称
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
	 * 得到某组某一个孩子的名称
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
