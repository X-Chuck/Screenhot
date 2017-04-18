package com.example.screen;

import android.app.Application;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.view.Window;

public class ShotApplication extends Application{
	private static ShotApplication instance;
	public static ShotApplication getInstance(){
		if (instance == null){
			instance = new ShotApplication();
		}
		return instance;
	}
	private int result;
	private Intent intent;
	private MediaProjectionManager mMediaProjectionManager;
	private Window window;
	public void setWindow(Window window) {
		this.window = window;
	}
	public Window getWindow() {
		return window;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public MediaProjectionManager getmMediaProjectionManager() {
		return mMediaProjectionManager;
	}
	public void setmMediaProjectionManager(
			MediaProjectionManager mMediaProjectionManager) {
		this.mMediaProjectionManager = mMediaProjectionManager;
	}
	

}
