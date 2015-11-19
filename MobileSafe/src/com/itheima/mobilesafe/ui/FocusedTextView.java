package com.itheima.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * 一出生就有焦点
 * @author afu
 *
 */
public class FocusedTextView extends TextView {

	//自定义样式
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	//在布局文件使用的时候自动用它
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//在代码里创建
	public FocusedTextView(Context context) {
		super(context);
	}
	
	//当前控件并没有获得焦点，我只是欺骗Android系统，让它以我获得焦点的方式去渲染
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
	

}
