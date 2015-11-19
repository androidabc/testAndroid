package com.itheima.mobilesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.activitys.HomeActivity;
import com.itheima.mobilesafe.service.UpdateAppWidgetService;
import com.itheima.mobilesafe.utils.StreamTools;

/**
 * ��һ��Ҳ��loginActivity
 * 
 * @author afu
 * 
 */

public class SplashActivity extends Activity {

	protected static final int ENTER_HOME = 1;

	protected static final int SHOW_UPDATE_DIALOG = 2;

	protected static final int URL_ERROR = 3;

	protected static final int NETWORK_ERROR = 4;

	protected static final int JSON_ERROR = 5;

	protected static final String TAG = "SplashActivity";

	private TextView tv_splash_version;

	private String description;
	private String apkurl;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME:// ������ҳ��
				enterHome();
				Log.i(TAG, "������ҳ��");

				break;
			case SHOW_UPDATE_DIALOG:// ���������Ի���
				Log.i(TAG, "���������Ի���");
				showUpdateDialog();
				break;
			case URL_ERROR:// URL����
				enterHome();
				Toast.makeText(SplashActivity.this, "URL����", 1).show();

				break;
			case NETWORK_ERROR:// �����쳣
				enterHome();
				Toast.makeText(getApplicationContext(), "�����쳣", 1).show();

				break;
			case JSON_ERROR:// JSON����������
				enterHome();
				Toast.makeText(getApplicationContext(), "JSON����������", 1).show();
				break;

			default:
				break;
			}
		}

	};

	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// ��ǰҳ��ر�
		finish();

	};

	protected void showUpdateDialog() {
		// this �ȼ��� SplashActivity.this
		// ����getApplicationContext()
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		builder.setTitle("�����°汾");
		// builder.setCancelable(false);//ǿ������
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
			}
		});
		builder.setMessage(description);
		builder.setNeutralButton("��������", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ����APK���滻��װ-sdcard
					FinalHttp http = new FinalHttp();
					http.download(apkurl,
							Environment.getExternalStorageDirectory()
									+ "/mobilesafe2.0.apk",
							new AjaxCallBack<File>() {

								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									// TODO Auto-generated method stub
									t.printStackTrace();
									Toast.makeText(getApplicationContext(),
											"����ʧ����", 1).show();
									super.onFailure(t, errorNo, strMsg);
								}

								@Override
								public void onLoading(long count, long current) {
									// TODO Auto-generated method stub
									tv_update_info.setVisibility(View.VISIBLE);
									int progress = (int) (current * 100 / count);
									tv_update_info.setText("���ؽ��ȣ�" + progress
											+ "%");

									super.onLoading(count, current);
								}

								@Override
								public void onSuccess(File t) {
									installApk(t);
									Toast.makeText(getApplicationContext(),
											"���سɹ�", 1).show();
									super.onSuccess(t);
								}

								// ��װӦ��
								private void installApk(File t) {
									// <intent-filter>
									// <action
									// android:name="android.intent.action.VIEW"
									// />
									// <category
									// android:name="android.intent.category.DEFAULT"
									// />
									// <data android:scheme="content" />
									// <data android:scheme="file" />
									// <data
									// android:mimeType="application/vnd.android.package-archive"
									// />
									// </intent-filter>
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.setDataAndType(Uri.fromFile(t),
											"application/vnd.android.package-archive");
									startActivity(intent);

								}

							});

				} else {
					Toast.makeText(getApplicationContext(), "sd�������ã�����", 1)
							.show();
				}

			}
		});
		builder.setPositiveButton("�´���˵", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// �����Ի���
				dialog.dismiss();
				// ������ҳ��
				enterHome();

			}
		});

		builder.show();

	}

	private TextView tv_update_info;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_update_info = (TextView) findViewById(R.id.tv_update_info);
		tv_splash_version.setText("�汾��" + getVersion());
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_root).startAnimation(aa);
		boolean update = sp.getBoolean("update", false);
		createShortcut();
		copyDB("address.db");
		copyDB("commonnum.db");
		copyDB("antivirus.db");
		if (update) {
			// �������
			checkUpdate();
		} else {
			// ������ҳ�棬�ӳ�2��
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}

	}

	//������ݼ�
	private void createShortcut() {
		
		if(sp.getBoolean("shortcutinstalled", false)){
			return;
		}
		Intent intent = new Intent();
		// ����
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		// ͼ��
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
				BitmapFactory.decodeResource(getResources(), R.drawable.app));
		// ����
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�ֻ���ʿ");
		
		
		//�������������ҵ�ʱ��Ҫ�Ҹ�ʲô
		Intent callNumber = new Intent();
		//ֱ�ӽ�����ҳ��,�Զ���Action
		callNumber.setAction("com.itheima.mobilesafe.enterhome");
		
		
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, callNumber);
		
		
		sendBroadcast(intent);
		
		Editor editor = sp.edit();
		editor.putBoolean("shortcutinstalled", true);
		editor.commit();
		
	}

	/**
	 * ��assetsĿ¼�µ�address.db������
	 * /data/data/com.itheima.mobilesafe/files/address.db";
	 */
	private void copyDB(String dbname) {
		InputStream is = null;
		FileOutputStream fos = null;
		File file = new File(getFilesDir(), dbname);
		if(file.exists()){
			System.out.println("���ݿ��Ѿ����ڲ���Ҫ����");
		}else{
			try {
				is = getAssets().open(dbname);
			
				fos = new FileOutputStream(file);
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		
	}

	/**
	 * ����
	 */
	private void checkUpdate() {

		new Thread() {
			public void run() {
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					URL url = new URL(getString(R.string.serverurl));
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();

					if (code == 200) {
						// ����ɹ�
						InputStream is = conn.getInputStream();
						// ����������
						String result = StreamTools.readFromStream(is);
						System.out.println("result=" + result);

						// ����json
						JSONObject obj = new JSONObject(result);
						// �������İ汾��
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");
						if (getVersion().equals(version)) {
							// �汾һ�£�û���°汾��������ҳ
							msg.what = ENTER_HOME;
						} else {
							// �����°汾�������Ի������û�ѡ���Ƿ�����
							msg.what = SHOW_UPDATE_DIALOG;
						}

					}

				} catch (MalformedURLException e) {
					msg.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = NETWORK_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = JSON_ERROR;
				} finally {
					long endTime = System.currentTimeMillis();
					long dTime = endTime - startTime;
					if (dTime < 2000) {
						SystemClock.sleep(2000 - dTime);
					}

					handler.sendMessage(msg);
				}

			};
		}.start();

	}

	/**
	 * ��̬��ȡ�汾��
	 */

	private String getVersion() {
		// PackgeManager//��������
		PackageManager pm = getPackageManager();
		// �õ������嵥�ļ���Ϣ
		try {
			PackageInfo packInfo = pm.getPackageInfo(getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

}