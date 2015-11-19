package com.itheima.mobilesafe.activitys;

import java.io.File;

import com.itheima.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class CleanSdcardActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_sdcard);
		//伪代码：大概原理的代码，通常用于和程序员交流编程思想或者面试的时候用到
		File file = Environment.getExternalStorageDirectory();
		File files[] = file.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				//查询数据库，如果有的话，就显示出来
				//用线性布局（LinearLayout）的这个addView(view,0);
				
			}
		}
		
		
	}

}
