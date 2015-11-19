package com.itheima.mobilesafe.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.CommonNumberQueryDao;

public class CommonNumberQueryActivity extends Activity {

	private ExpandableListView elv;
	private SQLiteDatabase db;
	private static String path = "/data/data/com.itheima.mobilesafe/files/commonnum.db";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_number_query);
		db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		elv = (ExpandableListView) findViewById(R.id.elv);
		elv.setAdapter(new CommonNumberAdapter());
		elv.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				TextView tv = (TextView) v;
				String number = tv.getText().toString().split("\n")[1];
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + number));
				startActivity(intent);

				return true;
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//¹Ø±ÕÊý¾Ý¿â
		if (db != null) {
			db.close();
		}
	}

	private class CommonNumberAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			return CommonNumberQueryDao.getGroupCount(db);
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return CommonNumberQueryDao.getChildCount(groupPosition,db);
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView view;
			if (convertView != null) {
				view = (TextView) convertView;
			} else {
				view = new TextView(getApplicationContext());
			}

			view.setTextColor(Color.RED);
			view.setTextSize(18);
			view.setText("           "
					+ CommonNumberQueryDao.getGroupName(groupPosition,db));
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView view;
			if (convertView != null) {
				view = (TextView) convertView;
			} else {
				view = new TextView(getApplicationContext());
			}
			view.setTextColor(Color.BLACK);
			view.setTextSize(16);
			view.setText(CommonNumberQueryDao.getChildName(groupPosition,
					childPosition,db));
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

}
