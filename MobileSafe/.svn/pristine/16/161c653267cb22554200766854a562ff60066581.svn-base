package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.AddressQueryDao;
/**
 * 号码归属地的查询
 * @author afu
 *
 */
public class NumberAddressQueryActivity extends Activity {
	private EditText et_phone;
	private TextView tv_result;
	private Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_result = (TextView) findViewById(R.id.tv_result);
		et_phone.addTextChangedListener(new TextWatcher() {
			
			//当文本改变时回调这个方法
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()>=3){
					String address = AddressQueryDao.queryAddress(s.toString());
					tv_result.setText(address);
				}
				
			}
			//当文本改变前回调这个方法
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			//当文本改变后回调这个方法
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//点击事件
	public void numberAddressQuery(View view){
		//1.取出输入框的电话号码
		String phone = et_phone.getText().toString().trim();
		
		//2.判空
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(this, "号码不能为空", 1).show();
			
			//抖动效果
			 Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			 et_phone.startAnimation(shake);
			 /*shake.setInterpolator(new Interpolator() {
				
				@Override
				public float getInterpolation(float x) {
					// TODO Auto-generated method stub
					return y;
				}
			});*/
			 //振动效果
			// vibrator.vibrate(2000);//振动两秒
			 long[] pattern = {200,200,100,100,2000,2000,3000,3000};
			 //-1：振动一次，0重复振动，2
			 vibrator.vibrate(pattern , -1);
			 
			return;
		}else{
			//3.查询号码的归属地a:网络查询，b:本地数据库查询
			System.out.println("您要查询的号码为："+phone);
			String address = AddressQueryDao.queryAddress(phone);
			tv_result.setText(address);
		
		}
		
		
		
	}

}
