package com.itheima.mobilesafe.activitys;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.mobilesafe.R;

public class CleanCacheActivity extends Activity {
	protected static final int SCANING = 0;
	protected static final int SCANING_FINISH = 1;
	public static final int SHOW_CACHE_INFO = 2;
	private TextView tv_status;
	private ProgressBar progressBar1;
	private LinearLayout container;
	
	private PackageManager pm;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SCANING:
				String name = (String) msg.obj;
				tv_status.setText(name);
				
				break;

			case SCANING_FINISH:
				tv_status.setText("扫描完毕");
				break;
			case SHOW_CACHE_INFO:
				

				final CacheInfo cacheInfo = (CacheInfo) msg.obj;
				View view = View.inflate(CleanCacheActivity.this, R.layout.list_cache_item, null);
				ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
				TextView tv_cache_info = (TextView) view.findViewById(R.id.tv_cache_info);
				ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				
				iv_icon.setImageDrawable(cacheInfo.icon);
				tv_name.setText(cacheInfo.name);
				tv_cache_info.setText(Formatter.formatFileSize(CleanCacheActivity.this, cacheInfo.cacheSize));
				iv_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Method methods [] = PackageManager.class.getMethods();
						for(Method method: methods){
							if("deleteApplicationCacheFiles".equals(method.getName())){
								try {
									method.invoke(pm, cacheInfo.packName,new IPackageDataObserver.Stub() {
										
										@Override
										public void onRemoveCompleted(String packageName, boolean succeeded)
												throws RemoteException {
											// TODO Auto-generated method stub
											
										}
									});
								} catch (Exception e) {
									// TODO Auto-generated catch block

									Intent intent = new Intent();
//									09-04 08:11:49.258: I/ActivityManager(62): Starting: 
//										Intent { act=android.settings.APPLICATION_DETAILS_SETTINGS 
//										dat=package:com.android.browser flg=0x10000000
//										 cmp=com.android.settings/.applications.InstalledAppDetails } from pid 199
									intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
									intent.setData(Uri.parse("package:"+cacheInfo.packName));
									startActivity(intent);
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				
				container.addView(view, 0);
				
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_cache);
		tv_status = (TextView) findViewById(R.id.tv_status);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		container = (LinearLayout) findViewById(R.id.container);
		
		new Thread(){
			public void run() {
				
				
				pm = getPackageManager();
				List<PackageInfo> packInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
				progressBar1.setMax(packInfos.size());
				int progress = 0;
				for(PackageInfo packInfo:packInfos){
					SystemClock.sleep(50);
					String name = packInfo.applicationInfo.loadLabel(pm).toString();
					Message msg = Message.obtain();
					msg.obj = name;
					msg.what = SCANING;
					handler.sendMessage(msg);
					progress ++;
					progressBar1.setProgress(progress);
					
					
					try {
						Method method = PackageManager.class.getMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
						
						method.invoke(pm, packInfo.packageName,new MyIPackageStatsObserver());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
				Message msg = Message.obtain();
				msg.what = SCANING_FINISH;
				handler.sendMessage(msg);
				
				
				
			};
		}.start();
	}
	
	
	class MyIPackageStatsObserver extends IPackageStatsObserver.Stub{

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			
			long cacheSize = pStats.cacheSize;
			if(cacheSize > 0){
				
				try {
					CacheInfo cacheInfo = new CacheInfo();
					cacheInfo.cacheSize = cacheSize;
					cacheInfo.icon = pm.getApplicationInfo(pStats.packageName, 0).loadIcon(pm);
					cacheInfo.name = pm.getApplicationInfo(pStats.packageName, 0).loadLabel(pm).toString();
					cacheInfo.packName = pStats.packageName;
					Message msg = Message.obtain();
					msg.obj = cacheInfo;
					msg.what = SHOW_CACHE_INFO;
					handler.sendMessage(msg);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
	}
	
	class CacheInfo{
		long cacheSize;
		String name;
		Drawable icon;
		String packName;
	}
	
	//清除全部缓存
	public void cleanCache(View view){
		try {
			Method methods []= PackageManager.class.getMethods();
			for(Method method:methods){
				if("freeStorageAndNotify".equals(method.getName())){
					method.invoke(pm, Integer.MAX_VALUE,new IPackageDataObserver.Stub() {
						
						@Override
						public void onRemoveCompleted(String packageName, boolean succeeded)
								throws RemoteException {
							System.out.println(succeeded);
							
						}
					});
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	


}
