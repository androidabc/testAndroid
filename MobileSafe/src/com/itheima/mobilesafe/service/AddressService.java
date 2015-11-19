package com.itheima.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.AddressQueryDao;

public class AddressService extends Service {

	private TelephonyManager tm;
	private MyPhoneStateListener listener;
	private OutCallReceiver receiver;
	private SharedPreferences sp;

	// 窗体服务
	private WindowManager wm;
	private View view;
	private WindowManager.LayoutParams params;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// 监听来电
		listener = new MyPhoneStateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(1000);
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		// 代码注册广播接收者-监听去电
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消监听来电
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		// 取消注册
		unregisterReceiver(receiver);
		receiver = null;

	}

	// 服务里面的内部类-监听去电
	class OutCallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String number = getResultData();
			String address = AddressQueryDao.queryAddress(number);
			// Toast.makeText(context, address, 1).show();
			myToast(address);

		}

	}

	class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:// 电话打进来了
				String address = AddressQueryDao.queryAddress(incomingNumber);
				// Toast.makeText(getApplicationContext(), address, 1).show();
				myToast(address);

				break;
			case TelephonyManager.CALL_STATE_IDLE:// 电话被挂断了才空闲
				if (view != null) {
					wm.removeView(view);
					view = null;
				}

				break;
			default:
				break;
			}
		}

	}

	/**
	 * 自定义土司
	 * 
	 * @param address
	 */

	public void myToast(String address) {

		view = View.inflate(this, R.layout.show_address, null);
		TextView tv = (TextView) view.findViewById(R.id.tv_address);
		tv.setText(address);
		int which = sp.getInt("which", 0);
		// "半透明","活力橙","卫士蓝","金属灰","苹果绿"
		int ids[] = { R.drawable.call_locate_white,
				R.drawable.call_locate_orange, R.drawable.call_locate_blue,
				R.drawable.call_locate_gray, R.drawable.call_locate_green };
		view.setBackgroundResource(ids[which]);
		view.setOnTouchListener(new OnTouchListener() {
			int startX = 0;
			int startY = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				System.out.println("触摸了");
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下
					//1.记录第一次按下坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();

					break;

				case MotionEvent.ACTION_MOVE:// 移动
					//2.来到新的坐标
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					//3.计算偏移量
					int dX = newX - startX;
					int dY = newY - startY;
					//4.根据偏移量更新控件的位置
					//params.x = params.x +dX;
					//params.y = params.y + dY;
					params.x += dX;
					params.y += dY;
					
					if(params.x < 0){
						params.x = 0;
					}
					
					if(params.y < 0){
						params.y = 0;
					}
					
					if(params.x >wm.getDefaultDisplay().getWidth()- view.getWidth()){
						params.x = wm.getDefaultDisplay().getWidth()- view.getWidth();
					}
					
					if(params.y >wm.getDefaultDisplay().getHeight()-view.getHeight() ){
						params.y = wm.getDefaultDisplay().getHeight()-view.getHeight();
					}
					
					wm.updateViewLayout(view, params);
					
					//5.重新记录新坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_UP:// 离开
					
					Editor editor = sp.edit();
					editor.putInt("lastX", params.x);
					editor.putInt("lastY", params.y);
					editor.commit();

					break;
				}
				return true;
			}
		});
		params = new WindowManager.LayoutParams();
		params.gravity = Gravity.TOP+Gravity.LEFT;
		params.x = sp.getInt("lastX", 0);
		params.y = sp.getInt("lastY", 0);
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		/* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */
		| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		wm.addView(view, params);

	}

}
