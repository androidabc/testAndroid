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

	// ���ֶ�λ-LocationManager
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
		// ʵ����λ�÷���
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		// ����λ�øı�
		listener = new MyLocationListener();
		// ����
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// ����Ϊ��󾫶�
		// criteria.setAltitudeRequired(false);//��Ҫ�󺣰���Ϣ
		// criteria.setBearingRequired(false);//��Ҫ��λ��Ϣ
		// criteria.setCostAllowed(true);//�Ƿ���������
		// criteria.setPowerRequirement(Criteria.POWER_LOW);//�Ե�����Ҫ��

		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates("gps", 0, 0, listener);
	}

	@Override
	public void onDestroy() {
		// ȡ������λ�÷���
		lm.removeUpdates(listener);
		listener = null;
		super.onDestroy();
	}

	private class MyLocationListener implements LocationListener {

		// ��λ�÷����仯ʱ�ص�
		@Override
		public void onLocationChanged(Location location) {
			String longitude = "j:" + location.getLongitude() + "\n";
			String latitude = "w:" + location.getLatitude() + "\n";
			String accuracy = "a:" + location.getAccuracy() + "\n";
			// ��������λ��
			// ����ǰת���ɻ�������
			Editor editor = sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();

		}

		// ״̬�����仯
		// ����-�ر�
		// �ر�-����
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		// ĳ��λ���ṩ�߿�����
		@Override
		public void onProviderEnabled(String provider) {

		}

		// ĳ��λ���ṩ�߲�������
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

}