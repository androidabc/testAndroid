package com.itheima.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

	public BlackNumberOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);
	}
	

	//当回调的时候数据库已经创建了，适合创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		//_id主键自动增长的，number黑名单号码，mode拦截模式0:拦截电话，1:拦截短信，2：全部拦截
		db.execSQL("create table blacknumber(_id integer primary key autoincrement,number varchar(20),mode varchar(2))");

	}

	//升级的时候回调1-->2
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
