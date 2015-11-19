package com.itheima.mobilesafe.test;

import java.util.Random;

import com.itheima.mobilesafe.db.BlackNumberOpenHelper;
import com.itheima.mobilesafe.db.dao.BlackNumberDao;

import android.test.AndroidTestCase;
import android.widget.Toast;

public class TestCreateDB extends AndroidTestCase {

	public void testCreateDB() {

		BlackNumberOpenHelper blackNumberOpenHelper = new BlackNumberOpenHelper(
				getContext());
		blackNumberOpenHelper.getReadableDatabase();

	}

	public void testAdd() {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		//13512345600-13512345699
		Random random  = new Random();
		for(int i = 0;i< 100; i++){
			dao.add("1351234560"+i, random.nextInt(3)+"");
		}
		
	}

	public void testDelete() {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.delete("119");

	}

	public void testUpdate() {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.update("119", "0");
	}

	public void testFind() {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		boolean result = dao.find("119");
		assertEquals(true, result);

	}

	public void testFindMode() {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		String result= dao.findMode("119");
		System.out.println("mode="+result);
		Toast.makeText(getContext(), result, 1).show();

	}

}
