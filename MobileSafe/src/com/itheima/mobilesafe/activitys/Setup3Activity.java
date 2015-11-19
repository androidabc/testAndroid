package com.itheima.mobilesafe.activitys;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.mobilesafe.R;

public class Setup3Activity extends BaseSetupActivity {
	private EditText et_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_phone.setText(sp.getString("safenumber", ""));
	}

	@Override
	protected void showNext() {
		String number = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "还没有设置安全号码", 1).show();
			return;
		}

		Editor editor = sp.edit();
		editor.putString("safenumber", number);
		editor.commit();
		Intent intent = new Intent(this, Setup4Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);

	}

	@Override
	protected void showPre() {
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}

	public void selectContact(View view) {
		Intent intent = new Intent(this, SelectContactActivity.class);
		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			String number = data.getStringExtra("number").replace("-", "");
			et_phone.setText(number);
		}

	}

}
