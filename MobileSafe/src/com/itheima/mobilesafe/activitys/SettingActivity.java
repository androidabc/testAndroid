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
 * ��������
 * 
 * @author afu
 * 
 */
public class SettingActivity extends Activity {

	// �����Զ�����
	private SettingItemView siv_update;
	private SharedPreferences sp;

	// ����������ʾ
	private SettingItemView siv_show_address;
	private Intent showAddressIntent;

	// ���ù�������ʾ�򱳾�
	private SettingClickView scv_change_bg;

	// ���ù�������ʾ��λ��
	private SettingClickView scv_change_position;

	// ���ú���������
	private SettingItemView siv_blacknumber;
	private Intent blackNumberIntent;

	// ���ó�����
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
			// �Ѿ���ѡ
			siv_update.setChecked(true);
		} else {
			// û�й�ѡ
			siv_update.setChecked(false);
		}
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();

				if (siv_update.isChecked()) {
					// ����Ϊ�ǹ�ѡ
					siv_update.setChecked(false);
					editor.putBoolean("update", false);
				} else {
					// ����Ϊ��ѡ
					siv_update.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();

			}
		});
		// ����������ʾ
		siv_show_address = (SettingItemView) findViewById(R.id.siv_show_address);
		// ��������Ƿ�����
		boolean isRunning = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima.mobilesafe.service.AddressService");
		siv_show_address.setChecked(isRunning);
		showAddressIntent = new Intent(this, AddressService.class);
		siv_show_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_show_address.isChecked()) {
					// �ǹ�ѡ״̬
					siv_show_address.setChecked(false);
					// ����ֹͣ
					stopService(showAddressIntent);
				} else {
					// ��ѡװ״̬
					siv_show_address.setChecked(true);
					// ��������
					startService(showAddressIntent);
				}

			}
		});

		// ���ù�������ʾ��ı���
		final String items[] = { "��͸��", "������", "��ʿ��", "������", "ƻ����" };
		scv_change_bg = (SettingClickView) findViewById(R.id.scv_change_bg);
		int which = sp.getInt("which", 0);
		scv_change_bg.setDesc(items[which]);
		scv_change_bg.setTitle("��������ʾ����");
		scv_change_bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int tt = sp.getInt("which", 0);
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("��������ʾ����");
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

		// ���ù�������ʾ��λ��
		scv_change_position = (SettingClickView) findViewById(R.id.scv_change_position);
		scv_change_position.setTitle("��������ʾ��λ��");
		scv_change_position.setDesc("���ù�������ʾ����ʾλ��");
		scv_change_position.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						DragViewActivity.class);
				startActivity(intent);

			}
		});
		// ���ú���������
		siv_blacknumber = (SettingItemView) findViewById(R.id.siv_blacknumber);
		// ��������Ƿ�����
		boolean isRunningBlacknumber = ServiceStatusUtils.isServiceRunning(
				this, "com.itheima.mobilesafe.service.CallSmsSafeService");
		siv_blacknumber.setChecked(isRunningBlacknumber);
		blackNumberIntent = new Intent(this, CallSmsSafeService.class);
		siv_blacknumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_blacknumber.isChecked()) {
					// �ǹ�ѡ״̬
					siv_blacknumber.setChecked(false);
					// ����ֹͣ
					stopService(blackNumberIntent);
				} else {
					// ��ѡװ״̬
					siv_blacknumber.setChecked(true);
					// ��������
					startService(blackNumberIntent);
				}

			}
		});

		// ���ó�����
		siv_applock = (SettingItemView) findViewById(R.id.siv_applock);
		// ��������Ƿ�����
		boolean isRunningWatchDog = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima.mobilesafe.service.WatchDogService");
		siv_applock.setChecked(isRunningWatchDog);
		watchDogIntent = new Intent(this, WatchDogService.class);
		siv_applock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_applock.isChecked()) {
					// �ǹ�ѡ״̬
					siv_applock.setChecked(false);
					// ����ֹͣ
					stopService(watchDogIntent);
				} else {
					// ��ѡװ״̬
					siv_applock.setChecked(true);
					// ��������
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