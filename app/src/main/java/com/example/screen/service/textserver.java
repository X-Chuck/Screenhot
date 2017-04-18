package com.example.screen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class textserver extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("server server", "打开");
		MyFtpServer server = MyFtpServer.GetInstance();
		boolean start = server.start(getBaseContext());
		if(start){
			Log.i("server server", "真的打开了");
		}
		return START_REDELIVER_INTENT;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		MyFtpServer server = MyFtpServer.GetInstance();
		server.stop();
		Log.i("server", "关闭了");
		super.onDestroy();
	}
}
