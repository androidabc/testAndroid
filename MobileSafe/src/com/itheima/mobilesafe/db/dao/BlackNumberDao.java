package com.itheima.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.itheima.mobilesafe.db.BlackNumberOpenHelper;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

public class BlackNumberDao {
	
	
	private BlackNumberOpenHelper helper;
	
	
	public BlackNumberDao(Context context){
		helper = new BlackNumberOpenHelper(context);
	}
	/**
	 * ���Ӻ���������
	 * @param number ����������
	 * @param mode ����ģʽ0�����ص绰��1�����ض��ţ�2��ȫ������
	 */
	public void add(String number,String mode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert("blacknumber", null, values );
		db.close();
		
	}
	/**
	 * ɾ��һ����������Ϣ
	 * @param number Ҫɾ���ĺ���������
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * �޸�����ģʽ
	 * @param number Ҫ�޸ĵĵ绰����
	 * @param newmode �µ�����ģʽ
	 */
	public void update(String number,String newmode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newmode);
		db.update("blacknumber", values , "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * ����һ�������Ƿ����
	 * @param number Ҫ���ҵĺ���������
	 * @return true���ڣ�false������
	 */
	public boolean find(String number){
		boolean result = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query("blacknumber",null, "number=?", new String[]{number}, null, null, null);
		while(cursor.moveToNext()){
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	/**
	 * ��������ģʽ
	 * @param number Ҫ���ҵĺ���
	 * @return ��������ģʽ0���绰���أ�1���������أ�2��ȫ������
	 */
	public String findMode(String number){
		String result = "2";
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
		if(cursor.moveToNext()){
			String mode = cursor.getString(0);
			result = mode;
		}
		cursor.close();
		db.close();
		return result;
		
	}
	
	/**
	 * ȫ����������Ϣ
	 * @return
	 */
	public List<BlackNumberInfo> findAll(){
		SystemClock.sleep(3000);
		List<BlackNumberInfo> infos = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query("blacknumber", new String[]{"number","mode"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			BlackNumberInfo info= new BlackNumberInfo();
			String number = cursor.getString(0);
			info.setNumber(number);
			String mode = cursor.getString(1);
			info.setMode(mode);
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
		
	}
	/**
	 * ÿ�η���20������
	 * @param index ���Ǹ��±꿪������
	 * @return
	 */
	public List<BlackNumberInfo> findPart(int index){
		SystemClock.sleep(600);
		List<BlackNumberInfo> infos = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber order by _id desc limit 20 offset ?", new String[]{index+""});
		while(cursor.moveToNext()){
			BlackNumberInfo info= new BlackNumberInfo();
			String number = cursor.getString(0);
			info.setNumber(number);
			String mode = cursor.getString(1);
			info.setMode(mode);
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
		
	}
	/**
	 * �õ������ݶ�����
	 * @return
	 */
	public int getTotal(){
		int total = 0;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from blacknumber", null);
		while(cursor.moveToNext()){
			int count = cursor.getInt(0);
			total = count;
		}
		cursor.close();
		db.close();
		return total;
		
	}

}
