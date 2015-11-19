package com.itheima.mobilesafe.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.itheima.mobilesafe.R;

public class SelectContactActivity extends Activity {
	private ListView list_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		list_contact = (ListView) findViewById(R.id.list_contact);

		final List<Map<String, String>> data = getContacts();//在主线程，子线程
		list_contact.setAdapter(new SimpleAdapter(this, data,
				R.layout.contact_item_view, new String[] { "name", "number" },
				new int[] { R.id.tv_name, R.id.tv_phone }));
		list_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//电话号码
				String phone = data.get(position).get("number");
				Intent intent = new Intent();
				intent.putExtra("number", phone);
				
				setResult(1, intent);
				//关闭当前页面
				finish();
				
			}
		});
	}

	/**
	 * 读取手机里面的联系人
	 * 
	 * @return
	 */
	private List<Map<String, String>> getContacts() {
		
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();

		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri datauri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		while (cursor.moveToNext()) {
			String contact_id = cursor.getString(0);
			if (contact_id != null) {
				Map<String, String> map = new HashMap<String, String>();
				Cursor cursorData = resolver.query(datauri, new String[] { "mimetype", "data1" },
						"raw_contact_id=?", new String[] { contact_id }, null);
				while(cursorData.moveToNext()){
					String mimetype = cursorData.getString(0);
					String data1 = cursorData.getString(1);
					if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						//当前data1 是电话号码
						map.put("number", data1);
					}else if("vnd.android.cursor.item/name".equals(mimetype)){
						//当前data1 是姓名
						map.put("name", data1);
					}
					System.out.println("mimetype=="+mimetype+"这个类型对应的数据,data1=="+data1);
				}
				
				maps.add(map);
			}

		}
		return maps;
	} 

}
