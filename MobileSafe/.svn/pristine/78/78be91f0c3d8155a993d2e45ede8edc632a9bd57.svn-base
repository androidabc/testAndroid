package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.itheima.mobilesafe.R;

public class DragViewActivity extends Activity {

	protected static final String TAG = "DragViewActivity";
	private ImageView iv_drag;
	private SharedPreferences sp;
	private WindowManager wm;
	private int mWidth;
	private int mHeight;

	private TextView tv_top;
	private TextView tv_bottom;
	private long firstClickTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_view);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_top = (TextView) findViewById(R.id.tv_top);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		mWidth = wm.getDefaultDisplay().getWidth();
		mHeight = wm.getDefaultDisplay().getHeight();
		iv_drag = (ImageView) findViewById(R.id.iv_drag);
		int lastX = sp.getInt("lastX", 0);
		int lastY = sp.getInt("lastY", 0);
		Log.i(TAG, "上次保存位置(" + lastX + "," + lastY + ")");
		if (lastY > mHeight / 2) {
			// 当前控件在底部
			tv_top.setVisibility(View.VISIBLE);
			tv_bottom.setVisibility(View.INVISIBLE);

		} else {
			// 当前控件在顶部
			tv_top.setVisibility(View.INVISIBLE);
			tv_bottom.setVisibility(View.VISIBLE);
		}

		// 在第一个阶段无法使用
		// iv_drag.layout(lastX, lastY, lastX+iv_drag.getWidth(),
		// lastY+iv_drag.getHeight());
		// 测量 --layout-->draw
		LayoutParams params = (LayoutParams) iv_drag.getLayoutParams();
		params.leftMargin = lastX;
		params.topMargin = lastY;
		iv_drag.setLayoutParams(params);
		Log.i(TAG, "高和宽(" + iv_drag.getWidth() + "," + iv_drag.getHeight()
				+ ")");
		iv_drag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击事件");
				if (firstClickTime > 0) {
					// 第二次点击的事件
					long secondClickTime = SystemClock.uptimeMillis();
					// 计算花了多少时间，单位时间内500
					long dTime = secondClickTime - firstClickTime;

					if (dTime < 500) {
						// 双击居中

						iv_drag.layout(mWidth / 2 - iv_drag.getWidth() / 2,
								iv_drag.getTop(),
								mWidth / 2 + iv_drag.getWidth() / 2,
								iv_drag.getBottom());
						
						
						Editor editor = sp.edit();
						editor.putInt("lastX", iv_drag.getLeft());
						editor.putInt("lastY", iv_drag.getTop());
						editor.commit();

						firstClickTime = 0;
						return;
					}

				}

				System.out.println("点击了");
				// 记录第一次点击的事件
				firstClickTime = SystemClock.uptimeMillis();

			}
		});

		iv_drag.setOnTouchListener(new OnTouchListener() {
			int startX = 0;
			int startY = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 手指第一次按下
					// 1.第一次按下的坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Log.i(TAG, "按下");

					break;

				case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动

					// 2.来到新的坐标
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();

					// 3.计算偏移量
					int dX = newX - startX;
					int dY = newY - startY;
					Log.i(TAG, "移动");
					// 4.根据偏移量更新空间位置

					int newl = iv_drag.getLeft() + dX;
					int newt = iv_drag.getTop() + dY;
					int newr = iv_drag.getRight() + dX;
					int newb = iv_drag.getBottom() + dY;

					if (newl < 0 || newt < 0 || newr > mWidth
							|| newb > mHeight - 40) {
						break;
					}

					if (newt > mHeight / 2) {
						tv_top.setVisibility(View.VISIBLE);
						tv_bottom.setVisibility(View.INVISIBLE);
						// 当前控件在底部
					} else {
						// 当前控件在顶部
						tv_top.setVisibility(View.INVISIBLE);
						tv_bottom.setVisibility(View.VISIBLE);
					}

					iv_drag.layout(newl, newt, newr, newb);

					// 5.重新记录坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();

					break;

				case MotionEvent.ACTION_UP:// 手指离开屏幕
					Editor editor = sp.edit();
					editor.putInt("lastX", iv_drag.getLeft());
					editor.putInt("lastY", iv_drag.getTop());
					editor.commit();
					Log.i(TAG, "离开");

					break;
				}

				return false;
			}
		});
	}

}
