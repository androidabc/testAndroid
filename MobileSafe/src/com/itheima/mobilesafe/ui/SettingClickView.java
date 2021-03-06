package com.itheima.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe.R;

/**
 * 特殊的布局文件，里面直接有了：
 * 
 * 两个TextView，一个ImageView ，一个View
 *
 */

public class SettingClickView extends RelativeLayout {

	private String desc_on;
	private String desc_off;
	
	
	private TextView tv_title;
	private TextView tv_desc;
	
	private void initView(Context context) {
		//把布局文件-->View对象，把对象加载在this==SettingItemView.this
		View.inflate(context, R.layout.setting_click_view, SettingClickView.this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		
	}
	//设置样式的时候
	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//在布局文件使用的时候执行这个方法
	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobilesafe", "title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobilesafe", "desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobilesafe", "desc_off");
		tv_title.setText(title);
		setDesc(desc_off);
	}

	

	//在代码里直接创建new的时候使用
	public SettingClickView(Context context) {
		super(context);
		initView(context);
	}
	
	
	/**
	 * 设置组合控件的描述信息
	 */
	
	public void setDesc(String text){
		tv_desc.setText(text);
	}
	
	/**
	 * 设置组合控件的标题
	 */
	
	public void setTitle(String text){
		tv_title.setText(text);
	}
	

}
