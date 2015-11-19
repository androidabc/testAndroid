package com.itheima.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Xml;
import android.widget.ProgressBar;

public class SmsBackupUtils {
	
	//小B程序员想从频繁的修改UI中解脱出来
	public interface SmsBackupCallback{
		/**
		 * 短信备份前调用
		 * @param total 短信的总条数
		 */
		public void  beforeSmsbackup(int total);
		/**
		 * 短信备份中调用
		 * @param progress 短信备份的进度
		 */
		public void  progressSmsbackup(int progress);
	}
	
	
	/**
	 * 短信备份的方法
	 * @param context 上下文
	 * @param path 备份到那个路径
	 * @throws Exception 
	 */
	public static void smsBackup(Context context,String path,SmsBackupCallback callback ) throws Exception{
		
		//xml的序列化器
		XmlSerializer serializer = Xml.newSerializer();
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		serializer.setOutput(fos, "utf-8");//设置参数
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "smss");
		
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://sms");
		Cursor cursor = resolver.query(uri , new String[]{"address","date","type","body"}, null, null, null);
//		dialog.setMax(cursor.getCount());
//		progressBar.setMax(cursor.getCount());
		callback.beforeSmsbackup(cursor.getCount());
		int progress = 0;
		while(cursor.moveToNext()){
			serializer.startTag(null, "sms");
			
			serializer.startTag(null, "address");
			String address = cursor.getString(0);
			serializer.text(address);
			serializer.endTag(null, "address");
			
			serializer.startTag(null, "date");
			String date = cursor.getString(1);
			serializer.text(date);
			serializer.endTag(null, "date");
			
			serializer.startTag(null, "type");
			String type = cursor.getString(2);
			serializer.text(type);
			serializer.endTag(null, "type");
			
			serializer.startTag(null, "body");
			String body = cursor.getString(3);
			serializer.text(body);
			serializer.endTag(null, "body");
			
			serializer.endTag(null, "sms");
			progress ++;
//			dialog.setProgress(progress);
//			progressBar.setProgress(progress);
			callback.progressSmsbackup(progress);
			
			SystemClock.sleep(1000);
		}
		cursor.close();
		
		serializer.endTag(null, "smss");
		serializer.endDocument();
		
		
	}

}
