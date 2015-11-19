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
	 * �õ��ֻ�����Ӧ�õ���Ϣ
	 * @param context ������
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
			
			//����һ����־��������ʶĳ��Ӧ�õ�״̬
			int flage = packInfo.applicationInfo.flags;//Ӧ�ó��򽻵Ĵ��⿨
			if((flage&packInfo.applicationInfo.FLAG_SYSTEM)==0){
				//�û�����
				appInfo.setUser(true);
			}else{
				//ϵͳӦ��
				appInfo.setUser(false);
			}
			
			if((flage&packInfo.applicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				//�ֻ��ڴ�
				appInfo.setRom(true);
			}else{
				//����洢
				appInfo.setRom(false);
			}
			

			appInfos.add(appInfo);
		}

		return appInfos;
	}

}
