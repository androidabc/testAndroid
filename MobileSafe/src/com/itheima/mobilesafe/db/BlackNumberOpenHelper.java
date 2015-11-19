package com.itheima.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

	public BlackNumberOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);
	}
	

	//���ص���ʱ�����ݿ��Ѿ������ˣ��ʺϴ�����
	@Override
	public void onCreate(SQLiteDatabase db) {
		//_id�����Զ������ģ�number���������룬mode����ģʽ0:���ص绰��1:���ض��ţ�2��ȫ������
		db.execSQL("create table blacknumber(_id integer primary key autoincrement,number varchar(20),mode varchar(2))");

	}

	//������ʱ��ص�1-->2
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
