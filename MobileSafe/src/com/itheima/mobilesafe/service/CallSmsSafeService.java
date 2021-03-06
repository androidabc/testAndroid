package com.itheima.mobilesafe.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.itheima.mobilesafe.db.dao.BlackNumberDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

public class CallSmsSafeService extends Service {
	
	private InnerReceiver receiver;
	private BlackNumberDao dao;
	private TelephonyManager tm;
	private MyPhoneStateListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		dao = new BlackNumberDao(this);
		//监听短信
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(1000);
		registerReceiver(receiver, filter );
		//监听来电
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener  = new MyPhoneStateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		
		
	}
	
	class MyPhoneStateListener extends PhoneStateListener{

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://电话进来了
				
				//挂断电话
				endCall();//确实挂断电话了，但是生成呼叫记录需要一段时间，异步的，
//				deletCallLog(incomingNumber);
				Uri uri = Uri.parse("content://call_log/calls");
				getContentResolver().registerContentObserver(uri, true, new MyContentObserver(new Handler(),incomingNumber));
				
				break;

			default:
				break;
			}
		}
		
	}
	
	class MyContentObserver extends ContentObserver{

		private String number;
		public MyContentObserver(Handler handler,String number) {
			super(handler);
			this.number = number;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			getContentResolver().unregisterContentObserver(this);
			deletCallLog(number);
		}
		
		
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//取消监听
		unregisterReceiver(receiver);
		receiver = null;
		
		//取消监听来电
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
	}
	/**
	 * 删除呼叫记录
	 */
	public void deletCallLog(String number) {
		ContentResolver resolver = getContentResolver();
		Uri url = Uri.parse("content://call_log/calls");
		resolver.delete(url , "number=?", new String[]{number});
		
	}
	/**
	 * 挂断电话
	 */
	public void endCall() {
//		ServiceManager.getService(TELEPHONY_SERVICE);
		//1.得到ServiceManager字节码
		try {
			Class clazz = CallSmsSafeService.class.getClassLoader().loadClass("android.os.ServiceManager");
			//2.更加字节码得到方法getService
			Method method =clazz.getMethod("getService", String.class);
			//3.执行getService得到 IBinder b
			IBinder b =(IBinder) method.invoke(null, TELEPHONY_SERVICE);
			//4.aidl
			 
			ITelephony service = ITelephony.Stub.asInterface(b);
			//5.调用endCall方法
			service.endCall();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//监听短信
	class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Object [] pdus = (Object[]) intent.getExtras().get("pdus");
			for( Object pdu: pdus){
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
				String sender = sms.getOriginatingAddress();
				if(dao.find(sender)){
					String mode = dao.findMode(sender);
					if("2".equals(mode)||"1".equals(mode)){
						
						System.out.println("拦截到一条短信");
						abortBroadcast();//短信拦截了
					}
				}
				
				String body = sms.getMessageBody();
				if(body.contains("fapiao")){//做一个数据库
					System.out.println("拦截到一条广告的短信");
					abortBroadcast();
					
				}
			}
			
		}
		
	}

}
