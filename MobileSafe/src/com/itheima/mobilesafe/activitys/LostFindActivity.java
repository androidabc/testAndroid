package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.mobilesafe.R;

public class LostFindActivity extends Activity {

	private SharedPreferences sp;
	
	private TextView tv_safenumber;
	private ImageView iv_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		super.onCreate(savedInstanceState);

		// �Ƿ����ù������򵼣����û�����þ���ת�������򵼵�һ��ҳ�棬����������ֻ�����ҳ��
		boolean configed = sp.getBoolean("configed", false);
		if(configed){
			setContentView(R.layout.activity_lostfind);
			tv_safenumber = (TextView) findViewById(R.id.tv_safenumber);
			iv_status = (ImageView) findViewById(R.id.iv_status);
			//���ð�ȫ����
			tv_safenumber.setText(sp.getString("safenumber", "5556"));
			if(sp.getBoolean("protectting", false)){
				//���������Ѿ�����
				iv_status.setImageResource(R.drawable.lock);
			}else{
				//��������û�п���
				iv_status.setImageResource(R.drawable.unlock);
			}
		}else{
			//ת�������򵼵�һ��ҳ��
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
			//�رյ�ǰҳ��
			finish();
		}
		
		
		
	}
	//����¼�-���½���������ҳ��
	public void reEnterSetting(View view){
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
	}

}
