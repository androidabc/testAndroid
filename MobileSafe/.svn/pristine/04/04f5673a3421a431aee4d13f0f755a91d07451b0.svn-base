package com.itheima.mobilesafe.activitys;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.SmsBackupUtils;
import com.itheima.mobilesafe.utils.SmsBackupUtils.SmsBackupCallback;

public class AToolsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	
	//点击事件-调转号码归属地查询页面
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this,NumberAddressQueryActivity.class);
		startActivity(intent);
	}
	
	public void smsBackup(View view){
		//短信备份
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			final File file = new File(Environment.getExternalStorageDirectory(), "smsbackup.xml");
			final ProgressDialog dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage("正在备份中,不要猴急...");
			dialog.show();
			new Thread(){
				public void run() {
					try {
						SmsBackupUtils.smsBackup(AToolsActivity.this, file.getAbsolutePath(), new SmsBackupCallback() {
							
							@Override
							public void progressSmsbackup(int progress) {
								dialog.setProgress(progress);
								
							}
							
							@Override
							public void beforeSmsbackup(int total) {
								dialog.setMax(total);
								
								
							}
						});
//						Toast.makeText(this, "备份成功，呵呵", 1).show();
						dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
//						Toast.makeText(this, "备份失败", 1).show();
						dialog.dismiss();
					}
				};
			}.start();
		}else{
			Toast.makeText(this, "sdcard不可以用", 1).show();
		}
		
	}
	//点击事件-常用号码查询
	public void commonNumberQuery(View view){
		Intent intent = new Intent(this,CommonNumberQueryActivity.class);
		startActivity(intent);
	}
	
	//点击事件-进入程序锁页面
	public void enterApplock(View view){
		Intent intent = new Intent(this,AppLockActivity.class);
		startActivity(intent);
		
	}
	

}
