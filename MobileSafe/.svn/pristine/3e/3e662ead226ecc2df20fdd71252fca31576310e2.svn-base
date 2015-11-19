package com.itheima.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.itheima.mobilesafe.domain.AppInfo;

public class AppInfoProvider {
	
	
	/**
	 * 得到手机所以应用的信息
	 * @param context 上下文
	 * @return
	 */

	public static List<AppInfo> getAllAppInfos(Context context) {
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		// PackageMager
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
		for (PackageInfo packInfo : packInfos) {
			AppInfo appInfo = new AppInfo();
			Drawable icon = packInfo.applicationInfo.loadIcon(pm);
			appInfo.setIcon(icon);
			String name = packInfo.applicationInfo.loadLabel(pm).toString()+packInfo.applicationInfo.uid;
			appInfo.setName(name);
			String packName = packInfo.packageName;
			appInfo.setPackName(packName);
			
			//这是一个标志，用来标识某个应用的状态
			int flage = packInfo.applicationInfo.flags;//应用程序交的答题卡
			if((flage&packInfo.applicationInfo.FLAG_SYSTEM)==0){
				//用户程序
				appInfo.setUser(true);
			}else{
				//系统应用
				appInfo.setUser(false);
			}
			
			if((flage&packInfo.applicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				//手机内存
				appInfo.setRom(true);
			}else{
				//外包存储
				appInfo.setRom(false);
			}
			

			appInfos.add(appInfo);
		}

		return appInfos;
	}

}
