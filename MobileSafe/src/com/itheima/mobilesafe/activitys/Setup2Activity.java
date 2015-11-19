package com.itheima.mobilesafe.activitys;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.ui.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {
	
	private SettingItemView bind_sim;
	//电话服务
	private TelephonyManager tm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		bind_sim = (SettingItemView) findViewById(R.id.bind_sim);
		String sim = sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			bind_sim.setChecked(false);
		}else{
			bind_sim.setChecked(true);
		}
		bind_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				String sim = tm.getSimSerialNumber();
				if(bind_sim.isChecked()){
					//设置为非勾选
					bind_sim.setChecked(false);
					editor.putString("sim", null);
				}else{
					//设置为勾选
					bind_sim.setChecked(true);
					editor.putString("sim", sim);
				}
				editor.commit();
				
			}
		});
	}

	

	@Override
	protected void showNext() {
		String sim = sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			Toast.makeText(this, "sim卡没有绑定", 1).show();
			return;
		}
		
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}

	@Override
	protected void showPre() {
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}

}
