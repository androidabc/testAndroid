package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.waps.AppConnect;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.MD5Utils;

public class HomeActivity extends Activity {

	protected static final String TAG = "HomeActivity";

	private SharedPreferences sp;

	private GridView list_home;
	private static String[] names = { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���", "����ͳ��",
			"�ֻ�ɱ��", "��������", "�߼�����", "��������" };

	private static int ids[] = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_home);
		// ��ʼ��ͳ��������ͨ����������APP_ID, APP_PID
		AppConnect
				.getInstance("3f42a4b02948a51cc987af6b833d1720", "waps", this);

		list_home = (GridView) findViewById(R.id.list_home);
		// ����������
		list_home.setAdapter(new HomeAdapter());
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0:// �����ֻ�����
					showLostFindDialog();
					break;
				case 1:// ����ͨѶ��ʿҳ��
					intent = new Intent(HomeActivity.this,
							CallSmsSafeActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(HomeActivity.this,
							AppManagerActivity.class);
					startActivity(intent);
					break;

				case 3:// ������̹���
					intent = new Intent(HomeActivity.this,
							TaskManagerActivity.class);
					startActivity(intent);
					break;
				case 4:// ����ͳ��ҳ��
					intent = new Intent(HomeActivity.this,
							TrafficManagerActivity.class);
					startActivity(intent);
					break;
				case 5:// �ֻ�ɱ��
					intent = new Intent(HomeActivity.this,
							AntiVirusActivity.class);
					startActivity(intent);
					break;
				case 6:// ��������
					intent = new Intent(HomeActivity.this, CleanActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(HomeActivity.this, AToolsActivity.class);
					startActivity(intent);
					break;
				case 8:// ������������
					intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					startActivity(intent);

					break;

				default:
					break;
				}

			}
		});

		// ���������÷�ʽ
		LinearLayout layout = (LinearLayout) this
				.findViewById(R.id.AdLinearLayout);
		AppConnect.getInstance(this).showBannerAd(this, layout);

	}

	protected void showLostFindDialog() {
		// �Ƿ����ù����룬���û�����þ͵������öԻ��򣬷��򵯳�����Ի���
		if (isSetupPwd()) {
			// ��������Ի���
			showEnterPwddialog();
		} else {
			// ���þ͵������öԻ���
			showSetupPwdDialog();
		}

	}

	private AlertDialog dialog;

	/**
	 * ��������Ի���
	 */
	private void showSetupPwdDialog() {
		AlertDialog.Builder builder = new Builder(this);
		// �Ѳ����ļ�-->View����
		View view = View.inflate(this, R.layout.dialog_setup_password, null);
		final EditText et_password = (EditText) view
				.findViewById(R.id.et_password);
		final EditText et_password_confirm = (EditText) view
				.findViewById(R.id.et_password_confirm);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �Ի�������
				dialog.dismiss();

			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.�õ�����
				String password = et_password.getText().toString().trim();
				String password_confirm = et_password_confirm.getText()
						.toString().trim();
				// 2.�п�
				if (TextUtils.isEmpty(password)
						|| TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1).show();
					return;
				}
				// 3.�Ƚ������Ƿ���ͬ
				if (password.equals(password_confirm)) {
					// 4.��������
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.encode(password));// �������루���ܺ�ģ�
					editor.commit();
					// 5.�����ֻ�����ҳ��
					Log.i(TAG, "�����ֻ�����ҳ��");
					Intent intent = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(intent);
					dialog.dismiss();

				} else {
					Toast.makeText(getApplicationContext(), "���벻һ��", 1).show();
				}

			}
		});
		// builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}

	/**
	 * ��������Ի���
	 */
	private void showEnterPwddialog() {

		AlertDialog.Builder builder = new Builder(this);
		// �Ѳ����ļ�-->View����
		View view = View.inflate(this, R.layout.dialog_enter_password, null);
		final EditText et_password = (EditText) view
				.findViewById(R.id.et_password);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �Ի�������
				dialog.dismiss();

			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.�õ�����
				String password = et_password.getText().toString().trim();// ������Ǽ��ܵ�
				// �õ�ԭ�����������
				String safe_password = sp.getString("password", "");// �õ���ʱ����ܺ��
				// 2.�п�
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1).show();
					return;
				}
				// 3.�Ƚ������Ƿ���ͬ
				if (MD5Utils.encode(password).equals(safe_password)) {

					// 5.�����ֻ�����ҳ��
					Log.i(TAG, "�����ֻ�����ҳ��");
					Intent intent = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(intent);
					dialog.dismiss();

				} else {
					Toast.makeText(getApplicationContext(), "���벻һ��", 1).show();
				}

			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}

	/**
	 * �ж��Ƿ����ù�����
	 * 
	 * @return
	 */
	private boolean isSetupPwd() {
		String password = sp.getString("password", null);
		// if(TextUtils.isEmpty(password)){
		// return false;
		// }else{
		// return true;
		// }
		return !TextUtils.isEmpty(password);
	}

	private class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// �Ѳ����ļ�-->View
			View view = View.inflate(HomeActivity.this, R.layout.home_item,
					null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_name.setText(names[position]);
			iv_icon.setImageResource(ids[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}
