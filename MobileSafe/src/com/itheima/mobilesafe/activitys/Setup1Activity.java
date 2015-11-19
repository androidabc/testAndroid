package com.itheima.mobilesafe.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.itheima.mobilesafe.R;

public class Setup1Activity extends BaseSetupActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		
	}

	

	protected void showNext() {
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}


	@Override
	protected void showPre() {
		// TODO Auto-generated method stub
		
	}

	

}
