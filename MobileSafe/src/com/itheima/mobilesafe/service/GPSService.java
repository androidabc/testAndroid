package com.itheima.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSService extends Service {

	// 三种定位-LocationManager
	private LocationManager lm;
	private MyLocationListener listener;
	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// 实例化位置服务
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		// 监听位置改变
		listener = new MyLocationListener();
		// 条件
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 设置为最大精度
		// criteria.setAltitudeRequired(false);//不要求海拔信息
		// criteria.setBearingRequired(false);//不要求方位信息
		// criteria.setCostAllowed(true);//是否允许付费
		// criteria.setPowerRequirement(Criteria.POWER_LOW);//对电量的要求

		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates("gps", 0, 0, listener);
	}

	@Override
	public void onDestroy() {
		// 取消监听位置服务
		lm.removeUpdates(listener);
		listener = null;
		super.onDestroy();
	}

	private class MyLocationListener implements LocationListener {

		// 当位置发生变化时回调
		@Override
		public void onLocationChanged(Location location) {
			String longitude = "j:" + location.getLongitude() + "\n";
			String latitude = "w:" + location.getLatitude() + "\n";
			String accuracy = "a:" + location.getAccuracy() + "\n";
			// 保存最新位置
			// 保存前转换成火星坐标
			Editor editor = sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();

		}

		// 状态发生变化
		// 开启-关闭
		// 关闭-开启
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		// 某个位置提供者可以了
		@Override
		public void onProviderEnabled(String provider) {

		}

		// 某个位置提供者不可以了
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

}
