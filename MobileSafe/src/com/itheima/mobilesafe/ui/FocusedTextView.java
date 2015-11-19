package com.itheima.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * һ�������н���
 * @author afu
 *
 */
public class FocusedTextView extends TextView {

	//�Զ�����ʽ
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	//�ڲ����ļ�ʹ�õ�ʱ���Զ�����
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//�ڴ����ﴴ��
	public FocusedTextView(Context context) {
		super(context);
	}
	
	//��ǰ�ؼ���û�л�ý��㣬��ֻ����ƭAndroidϵͳ���������һ�ý���ķ�ʽȥ��Ⱦ
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
	

}
