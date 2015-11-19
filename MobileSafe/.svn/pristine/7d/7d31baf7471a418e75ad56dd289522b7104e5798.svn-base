package com.itheima.mobilesafe.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.itheima.mobilesafe.domain.AppInfo;
import com.itheima.mobilesafe.engine.AppInfoProvider;

public class TestAppInfoProvider extends AndroidTestCase {

	public void testGepAppInfos() {
		List<AppInfo> appInfos = AppInfoProvider.getAllAppInfos(getContext());
		for(AppInfo appInfo : appInfos){
			System.out.println(appInfo.toString());
		}

	}

}
