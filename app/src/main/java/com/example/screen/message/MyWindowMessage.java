package com.example.screen.message;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;

public class MyWindowMessage {
	private WindowManager mag;
	private int windowWidth;
	private int windowHight;
	public MyWindowMessage(Context context){
		mag =  (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		windowWidth = mag.getDefaultDisplay().getWidth();
		windowHight = mag.getDefaultDisplay().getHeight();
	}
	/**
	 * 添加控件到windowMessage中
	 * @param view 要添加的控件
	 * @param format winParams.format的值(设置图片格式)
	 * @param flags winParams.flags的值(设置浮动窗口不可聚焦)
	 * @param gravity winParams.gravity的值(调整悬浮窗显示的停靠位置)
	 * @param width winParams.width的值(设置悬浮窗口长宽数据)
	 * @param height winParams.height的值(设置悬浮窗口长宽数据)
	 * @param xPersent winParams.x的百分比
	 * @param yPersent winParams.y的百分比
	 */
	public LayoutParams addView(View view,int format,int flags,int gravity,int width,int height,float xPersent,float yPersent){
		if(mag == null){
			return null;
		}
		LayoutParams winParams = new WindowManager.LayoutParams();
		if(android.os.Build.VERSION.SDK_INT < 19){
			winParams.type = LayoutParams.TYPE_PHONE;
		}else{
			winParams.type = LayoutParams.TYPE_TOAST;
		}
		winParams.format = format;
		winParams.flags = flags;
		winParams.gravity = gravity;
		winParams.width = width;
		winParams.height =  height;
		winParams.x = (int)(windowWidth*xPersent);
		winParams.y = (int)(windowHight*yPersent);
		mag.addView(view, winParams);
		return winParams;
	}
	
	/**
	 * 移除view
	 * @param v
	 */
	public void removeView(View v){
		if(v == null){
			return;
		}
		mag.removeView(v);
	}
	
	public WindowManager getWindowManager(){
		return mag;
	}
	
	public int getWindowWidth(){
		windowWidth = mag.getDefaultDisplay().getWidth();
		return windowWidth;
	}
	public int getWindowHeight(){
		windowHight = mag.getDefaultDisplay().getHeight();
		return windowHight;
	}
	
}
