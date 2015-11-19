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
	 * 增加黑名单号码
	 * @param number 黑名单号码
	 * @param mode 拦截模式0，拦截电话；1，拦截短信；2，全部拦截
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
	 * 删除一条黑名单信息
	 * @param number 要删除的黑名单号码
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * 修改拦截模式
	 * @param number 要修改的电话号码
	 * @param newmode 新的拦截模式
	 */
	public void update(String number,String newmode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newmode);
		db.update("blacknumber", values , "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * 查找一条数据是否存在
	 * @param number 要查找的黑名单号码
	 * @return true存在，false不存在
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
	 * 查找拦截模式
	 * @param number 要查找的号码
	 * @return 返回拦截模式0，电话拦截；1，短信拦截；2，全部拦截
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
	 * 全部黑名单信息
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
	 * 每次返回20条数据
	 * @param index 从那个下标开发返回
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
	 * 得到总数据多少条
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
