package com.itheima.mobilesafe.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import com.itheima.mobilesafe.activitys.EnterPwdActivity;
import com.itheima.mobilesafe.db.dao.ApplockDao;

public class WatchDogService extends Service {
	
	private boolean flag = false;
	private ActivityManager am;
	
	private ApplockDao dao;
	private Intent intent;
	
	private InnerReceiver receiver;
	private String stopProtectPackname;
	private List<String> packNames;
	
	private MyContentObserver contentObserver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		dao = new ApplockDao(this);
		intent = new Intent(this, EnterPwdActivity.class);
		//当在服务启动Activity的时候需要加载
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		packNames = dao.findAll();
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.itheima.mobilesafe.stopprotecting");
		registerReceiver(receiver, filter );
		//注册内容观察者
		Uri uri = Uri.parse("content://com.itheima.mobilesafe.dbchange");
		contentObserver = new MyContentObserver(new Handler());
		getContentResolver().registerContentObserver(uri, true, contentObserver);
		new Thread(){
			
			public void run() {
				flag = true;
				while(flag){
					
					//得到任务栈，从任务中得到最近打开的应用的信息
					RunningTaskInfo  taskinfo = am.getRunningTasks(1).get(0);
					String packName = taskinfo.topActivity.getPackageName();
//					System.out.println("看门狗开始巡逻...packName=="+packName);
					//if(dao.find(packName)){//查询数据库耗时-在内存中查询
					if(packNames.contains(packName)){
						
						if(packName.equals(stopProtectPackname)){
							//什么也不用改
						}else{
							//保护起来
							intent.putExtra("packname", packName);
							startActivity(intent);
						}
						
					}
					SystemClock.sleep(20);
				}
			};
		}.start();
	}
	
	class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			stopProtectPackname = intent.getStringExtra("packname");
			
		}
		
	}
	
	private class MyContentObserver extends ContentObserver{

		public MyContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			System.out.println(" 数据发生变化...");
			packNames = dao.findAll();
		}
		
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = false;
		unregisterReceiver(receiver);
		receiver = null;
		
		getContentResolver().unregisterContentObserver(contentObserver);
		contentObserver = null;
	}

}
