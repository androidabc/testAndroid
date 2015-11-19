package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.service.GPSService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SMSReceiver extends BroadcastReceiver {

	private SharedPreferences sp ;
	@Override
	public void onReceive(Context context, Intent intent) {
		sp= context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Object [] pdus = (Object[]) intent.getExtras().get("pdus");
		
		for(Object pdu: pdus ){
			
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
			
			//谁发过来的
			String safenumber =sp.getString("safenumber", "5556");
			String sender = sms.getOriginatingAddress();//5556-1555555556
			if(sender.contains(safenumber)){
				
				String body = sms.getMessageBody();
				if("location".equals(body)){
					//得到手机的位置
					System.out.println("得到手机的位置");
					Intent intentGPSService  = new Intent(context,GPSService.class);
					context.startService(intentGPSService);
					
					String lastlocation = sp.getString("lastlocation", "");
					if(TextUtils.isEmpty(lastlocation)){
						SmsManager.getDefault().sendTextMessage(sender, null, "getting location....from afu", null, null);
					}else{
						SmsManager.getDefault().sendTextMessage(sender, null, lastlocation, null, null);
					}
					
					abortBroadcast();//把广播终止
				}else if("alarm".equals(body)){
					//播放报警音乐
					System.out.println("播放报警音乐");
					MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
//					player.setLooping(true);
					player.setVolume(1.0f, 1.0f);
					player.start();
					abortBroadcast();//把广播终止
				}else if("wipedata".equals(body)){
					//远程删除数据
					System.out.println("远程删除数据");
					abortBroadcast();//把广播终止
				}else if("lockscreen".equals(body)){
					//远程锁屏
					System.out.println("远程锁屏");
					abortBroadcast();//把广播终止
				}
				
			}
			
			
			
		}

	}

}
