package com.itheima.mobilesafe.activitys;

import com.itheima.mobilesafe.R;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SlidingDrawer;

public class TrafficManagerActivity extends Activity {
	private SlidingDrawer sd;
	int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_manager);
		sd = (SlidingDrawer) findViewById(R.id.sd);
		sd.open();
		sd.getHandle().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (flag == 0) {
					sd.open();
					flag = 1;
				}else {
					sd.close();
					flag = 0;
				}
			}
		});
		
		if (flag == 0) {
			sd.open();
			flag = 1;
		}else {
			sd.close();
			flag = 0;
		}

		// //流量统计的总量的接口
		// TrafficStats.getMobileRxBytes();//得到2G+3G的下载的总流量
		// TrafficStats.getMobileTxBytes();//得到2G+3G的上传的总流量；
		//
		//
		// TrafficStats.getTotalRxBytes();//手机+wifi的下载的总流量
		// TrafficStats.getTotalTxBytes();//手机+wifi的上传的总流量
		//
		//
		// //计算某一个应用的流量
		// TrafficStats.getUidRxBytes(uid);//得到某个应用的下载的流量
		// TrafficStats.getUidTxBytes(uid);//得到某个应用的上传的流量；

	}
}
