package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.service.UpdateAppWidgetService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyAppWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive");
		super.onReceive(context, intent);

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		System.out.println("onUpdate");
		Intent intent = new Intent(context, UpdateAppWidgetService.class);
		context.startService(intent);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {
		// TODO Auto-generated method stub
		System.out.println("onAppWidgetOptionsChanged");
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
				newOptions);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		System.out.println("onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		System.out.println("onEnabled");
		Intent intent = new Intent(context, UpdateAppWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		System.out.println("onDisabled");
		Intent intent = new Intent(context, UpdateAppWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}

}
