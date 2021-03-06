package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.service.AddressService;
import com.itheima.mobilesafe.service.CallSmsSafeService;
import com.itheima.mobilesafe.service.WatchDogService;
import com.itheima.mobilesafe.ui.SettingClickView;
import com.itheima.mobilesafe.ui.SettingItemView;
import com.itheima.mobilesafe.utils.ServiceStatusUtils;

/**
 * 设置中心
 * 
 * @author afu
 * 
 */
public class SettingActivity extends Activity {

	// 设置自动更新
	private SettingItemView siv_update;
	private SharedPreferences sp;

	// 设置来电显示
	private SettingItemView siv_show_address;
	private Intent showAddressIntent;

	// 设置归属地显示框背景
	private SettingClickView scv_change_bg;

	// 设置归属地显示框位置
	private SettingClickView scv_change_position;

	// 设置黑名单拦截
	private SettingItemView siv_blacknumber;
	private Intent blackNumberIntent;

	// 设置程序锁
	private SettingItemView siv_applock;
	private Intent watchDogIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			// 已经勾选
			siv_update.setChecked(true);
		} else {
			// 没有勾选
			siv_update.setChecked(false);
		}
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();

				if (siv_update.isChecked()) {
					// 设置为非勾选
					siv_update.setChecked(false);
					editor.putBoolean("update", false);
				} else {
					// 设置为勾选
					siv_update.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();

			}
		});
		// 设置来电显示
		siv_show_address = (SettingItemView) findViewById(R.id.siv_show_address);
		// 检验服务是否运行
		boolean isRunning = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima.mobilesafe.service.AddressService");
		siv_show_address.setChecked(isRunning);
		showAddressIntent = new Intent(this, AddressService.class);
		siv_show_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_show_address.isChecked()) {
					// 非勾选状态
					siv_show_address.setChecked(false);
					// 服务停止
					stopService(showAddressIntent);
				} else {
					// 勾选装状态
					siv_show_address.setChecked(true);
					// 服务启动
					startService(showAddressIntent);
				}

			}
		});

		// 设置归属地显示框的背景
		final String items[] = { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };
		scv_change_bg = (SettingClickView) findViewById(R.id.scv_change_bg);
		int which = sp.getInt("which", 0);
		scv_change_bg.setDesc(items[which]);
		scv_change_bg.setTitle("归属地提示框风格");
		scv_change_bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int tt = sp.getInt("which", 0);
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("归属地提示框风格");
				builder.setSingleChoiceItems(items, tt,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								Editor editor = sp.edit();
								editor.putInt("which", which);
								editor.commit();

								scv_change_bg.setDesc(items[which]);
								dialog.dismiss();

							}
						});

				builder.setNegativeButton("Cancel", null);
				builder.show();

			}
		});

		// 设置归属地显示框位置
		scv_change_position = (SettingClickView) findViewById(R.id.scv_change_position);
		scv_change_position.setTitle("归属地提示框位置");
		scv_change_position.setDesc("设置归属地提示框显示位置");
		scv_change_position.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						DragViewActivity.class);
				startActivity(intent);

			}
		});
		// 设置黑名单拦截
		siv_blacknumber = (SettingItemView) findViewById(R.id.siv_blacknumber);
		// 检验服务是否运行
		boolean isRunningBlacknumber = ServiceStatusUtils.isServiceRunning(
				this, "com.itheima.mobilesafe.service.CallSmsSafeService");
		siv_blacknumber.setChecked(isRunningBlacknumber);
		blackNumberIntent = new Intent(this, CallSmsSafeService.class);
		siv_blacknumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_blacknumber.isChecked()) {
					// 非勾选状态
					siv_blacknumber.setChecked(false);
					// 服务停止
					stopService(blackNumberIntent);
				} else {
					// 勾选装状态
					siv_blacknumber.setChecked(true);
					// 服务启动
					startService(blackNumberIntent);
				}

			}
		});

		// 设置程序锁
		siv_applock = (SettingItemView) findViewById(R.id.siv_applock);
		// 检验服务是否运行
		boolean isRunningWatchDog = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima.mobilesafe.service.WatchDogService");
		siv_applock.setChecked(isRunningWatchDog);
		watchDogIntent = new Intent(this, WatchDogService.class);
		siv_applock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_applock.isChecked()) {
					// 非勾选状态
					siv_applock.setChecked(false);
					// 服务停止
					stopService(watchDogIntent);
				} else {
					// 勾选装状态
					siv_applock.setChecked(true);
					// 服务启动
					startService(watchDogIntent);
				}

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isRunning = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima.mobilesafe.service.AddressService");
		siv_show_address.setChecked(isRunning);

		boolean isRunningBlacknumber = ServiceStatusUtils.isServiceRunning(
				this, "com.itheima.mobilesafe.service.CallSmsSafeService");
		siv_blacknumber.setChecked(isRunningBlacknumber);
	}

}
