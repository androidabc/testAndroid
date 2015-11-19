package com.itheima.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStatusUtils {
	
	/**
	 * 校验某个服务是否运行着
	 * @param context 上下文
	 * @param serviceName 要校验的服务的全名称
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String serviceName){
		//ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> serviceInfos = am.getRunningServices(100);
		for(RunningServiceInfo serviceInfo : serviceInfos){
			
			String name = serviceInfo.service.getClassName();
			if(name.equals(serviceName)){
				return true;
			}
		}
		return false;
	}

}
