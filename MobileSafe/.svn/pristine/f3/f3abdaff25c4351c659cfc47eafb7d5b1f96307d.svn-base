package com.itheima.mobilesafe.activitys;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.itheima.mobilesafe.R;

public class CleanActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean);
		TabHost host = getTabHost();
		TabSpec spec1 = host.newTabSpec("缓存清理");
		TabSpec spec2 =  host.newTabSpec("sdcard清理");
		
		//设置内容和标签的名称
		spec1.setIndicator("缓存清理");
		spec1.setContent(new Intent(this,CleanCacheActivity.class));
		
		spec2.setIndicator("sdcard清理");
		spec2.setContent(new Intent(this, CleanSdcardActivity.class));
		
		host.addTab(spec1);
		host.addTab(spec2);
	}

}
