package com.itheima.mobilesafe.activitys;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.itheima.mobilesafe.R;

public class Setup4Activity extends BaseSetupActivity {
	
	private CheckBox cb_protectting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup4);
		cb_protectting = (CheckBox) findViewById(R.id.cb_protectting);
		boolean protectting = sp.getBoolean("protectting", false);
		if(protectting){
			cb_protectting.setText("手机防盗保护已经开启");
			cb_protectting.setChecked(true);
		}else{
			cb_protectting.setText("手机防盗保护已经关闭");
			cb_protectting.setChecked(false);
		}
		cb_protectting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor = sp.edit();
				editor.putBoolean("protectting", isChecked);
				editor.commit();
				if(isChecked){
					cb_protectting.setText("手机防盗保护已经开启");
				}else{
					cb_protectting.setText("手机防盗保护已经关闭");
				}
				
			}
		});
	}

	@Override
	protected void showNext() {

		Editor editor = sp.edit();
		//设置向导已经设置
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent = new Intent(this, LostFindActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}

	@Override
	protected void showPre() {
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}

}
