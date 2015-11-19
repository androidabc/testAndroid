package com.itheima.mobilesafe.activitys;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.BlackNumberDao;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

public class CallSmsSafeActivity extends Activity {

	public static final String TAG = "CallSmsSafeActivity";
	private BlackNumberDao dao;
	private ListView list_callsms_safe;
	private List<BlackNumberInfo> infos;
	private LinearLayout ll_loading;
	private int index = 0;

	private CallSmsAdapter adapter;

	private boolean isLoading = false;

	private Handler hanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (adapter == null) {
				adapter = new CallSmsAdapter();
				list_callsms_safe.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();// 通知适配器刷新页面
			}
			isLoading = false;
			// list_callsms_safe.setSelection(index);//不推荐
			ll_loading.setVisibility(View.INVISIBLE);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callsms_safe);
		list_callsms_safe = (ListView) findViewById(R.id.list_callsms_safe);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		dao = new BlackNumberDao(this);
		// 该为在子线程加载数据
		fillData();

		list_callsms_safe.setOnScrollListener(new OnScrollListener() {

			// 当滚动状态改变的时候回调这个方法
			// 静止-->滚动
			// 滚动-->静止
			// 手指滑动-->惯性滚动
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:// 静止状态
					if (isLoading) {
						Toast.makeText(getApplicationContext(),
								"正在加载中...稍微再试...", 1).show();
						return;
					}

					int postion = list_callsms_safe.getLastVisiblePosition();// 19
					int size = infos.size();// 20;

					if (index >= dao.getTotal()) {
						Toast.makeText(getApplicationContext(),
								"服务器没有数据了，亲...", 1).show();
						return;
					}
					if (postion == (size - 1)) {
						Toast.makeText(getApplicationContext(), "加载更多数据", 0)
								.show();
						index += 20;
						isLoading = true;
						fillData();

					}

					break;

				case OnScrollListener.SCROLL_STATE_FLING:// 滚动状态
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 手指滑动状态
					break;
				}

			}

			// 当滚动的时候回调这个方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

	}

	private void fillData() {
		ll_loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				if (infos == null) {
					infos = dao.findPart(index);
				} else {
					infos.addAll(dao.findPart(index));
				}

				hanlder.sendEmptyMessage(0);
			};
		}.start();
	}

	class CallSmsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		// 每一天View
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView != null) {
				Log.i(TAG, "使用历史缓存的View对象==" + position);
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				Log.i(TAG, "创建新的View对象==" + position);
				view = View.inflate(CallSmsSafeActivity.this,
						R.layout.list_callsms_item, null);
				// 当View对象一创建的时候，就把它里面的ID查找出来并且放入容器中（类）
				holder = new ViewHolder();
				holder.tv_number = (TextView) view.findViewById(R.id.tv_name);
				holder.tv_mode = (TextView) view.findViewById(R.id.tv_mode);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

				// 要把容器和对应的View关联起来，现在容器已经保存了View的层次结构
				view.setTag(holder);
			}
			// 进一步优化

			// 得到黑名单信息
			final BlackNumberInfo info = infos.get(position);
			holder.tv_number.setText(info.getNumber());
			String mode = info.getMode();
			if ("2".equals(mode)) {
				// 全部拦截
				holder.tv_mode.setText("短信+电话拦截");
			} else if ("0".equals(mode)) {
				// 电话拦截
				holder.tv_mode.setText("电话拦截");
			} else if ("1".equals(mode)) {
				// 短信拦截
				holder.tv_mode.setText("短信拦截");
			}
			
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.i(TAG, "点击的位置是"+position);
					
					//1.把数据库的对应的黑名单这条信息删除
					dao.delete(info.getNumber());
					//2.把当前列表的集合也要删除
					infos.remove(info);
					//3.刷新页面
					adapter.notifyDataSetChanged();
					
				}
			});
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	static class ViewHolder {
		TextView tv_number;
		TextView tv_mode;
		ImageView iv_delete;
	}

	// 点击事件-添加黑名单号码
	private AlertDialog alertDialog;

	public void addBlackNumber(View view) {
		AlertDialog.Builder builder = new Builder(this);
		alertDialog = builder.create();
		View contentView = View.inflate(this, R.layout.dialog_add_blacknumber,
				null);
		final EditText et_password = (EditText) contentView
				.findViewById(R.id.et_password);
		final RadioGroup rg_mode = (RadioGroup) contentView
				.findViewById(R.id.rg_mode);
		Button ok = (Button) contentView.findViewById(R.id.ok);
		Button cancel = (Button) contentView.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();

			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.得到电话号码和拦截模式
				String number = et_password.getText().toString().trim();
				int id = rg_mode.getCheckedRadioButtonId();
				String mode = "2";
				switch (id) {
				case R.id.rb_all:// 全部拦截
					mode = "2";
					break;

				case R.id.rb_number:// 电话拦截
					mode = "0";
					break;
				case R.id.rb_sms:// 短信拦截
					mode = "1";
					break;
				}

				// 2.电话号码判空
				if(TextUtils.isEmpty(number)){
					Toast.makeText(getApplicationContext(), "号码不能为空", 1).show();
					return ;
				}

				// 3.保存到数据库
				dao.add(number, mode);

				// 4.添加到当前页面的列表
				BlackNumberInfo object = new BlackNumberInfo();
				object.setNumber(number);
				object.setMode(mode);
				infos.add(0, object );

				// 5.刷新页面和消掉对话框
				//通知适配器更新页面-getCount()--getView();
				adapter.notifyDataSetChanged();
				alertDialog.dismiss();
				
				

			}
		});

		alertDialog.setView(contentView, 0, 0, 0, 0);
		alertDialog.show();

	}

}
