package com.itheima.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 监听开机启动的广播
 * 
 * @author afu
 * 
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		// 1.得到保存的sim卡信息
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		// 防盗保护已经开启
		if (sp.getBoolean("protectting", false)) {
			String save_sim = sp.getString("sim", "") + "wo shia afu";
			// 2.得到当前的sim卡信息
			tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String real_sim = tm.getSimSerialNumber();
			// 3.比较是否相同
			if (save_sim.equals(real_sim)) {
				// 什么不用干
			} else {
				// 4.不相同发短信给安全号码
				System.out.println("sim卡已经变更.....");
				Toast.makeText(context, "sim卡已经变更.....", 1).show();
				SmsManager.getDefault().sendTextMessage(
						sp.getString("safenumber", ""), null,
						"sim change ... from afu ...", null, null);
			}

		}

	}

}
