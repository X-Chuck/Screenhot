package com.example.screen.service;

import com.example.screen.control.ScreenControler;
import com.example.screen.customView.AreaScreenView;
import com.example.screen.customView.FullScreenshotsButton;
import com.example.screen.customView.RegionOKButton;
import com.example.screen.customView.RegionScreenshotsButton;
import com.example.screen.customView.ToSettingViewButton;
import com.example.screen.message.MyWindowMessage;
import com.xsj_Screen.screen.R;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScreenService extends Service implements OnClickListener{

	MyWindowMessage winMsg = null;
	RelativeLayout floatRelativeLayout = null;//打开菜单按钮
	RelativeLayout areaScreenLayout = null;//区域截屏相关界面
	RelativeLayout menuLayout = null;
	Receiver receiver;
	AreaScreenView area;
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
		LayoutInflater inflater = LayoutInflater.from(getApplication());
		floatRelativeLayout = (RelativeLayout)inflater.inflate(R.layout.screenhot_float_window_button, null);
		//这一行不能放这
//		areaScreenLayout = (RelativeLayout) inflater.inflate(R.layout.float_area_screen, null);
		
		winMsg = new MyWindowMessage(getApplicationContext());
		LayoutParams params = winMsg.addView(floatRelativeLayout, PixelFormat.RGBA_8888, LayoutParams.FLAG_NOT_FOCUSABLE, Gravity.START | Gravity.TOP, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0f, 0.5f);
		
		//这一行不能放这
//		winMsg.addView(areaScreenLayout, PixelFormat.RGBA_8888, LayoutParams.FLAG_LAYOUT_IN_SCREEN, Gravity.START | Gravity.TOP, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0f, 0.0f);
		
		TextView screenshots = (TextView)floatRelativeLayout.findViewById(R.id.screenshots);
		screenshots.setOnTouchListener(new OnTouchListenerImp(params, winMsg.getWindowManager()));
		screenshots.setOnClickListener(this);
//		receiver = new Receiver();
//		final IntentFilter homeFilter = new IntentFilter("com.xsj.finsh_service");
//        this.registerReceiver(receiver, homeFilter);
        
        ScreenControler controler = ScreenControler.GetInstance();
        controler.setService(this);
        
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("clik", "delete");
//		unregisterReceiver(receiver);
		MyFinishSelf();
		super.onDestroy();
	}
	
	
	
	/**
	 * 创建区域截屏选择区域
	 */
	public void CreateAreSelect(){
		LayoutInflater inflater = LayoutInflater.from(getApplication());
		areaScreenLayout = (RelativeLayout) inflater.inflate(R.layout.screenhot_float_area_screen, null);
		winMsg.addView(areaScreenLayout, PixelFormat.RGBA_8888, LayoutParams.FLAG_LAYOUT_IN_SCREEN, Gravity.START | Gravity.TOP, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0f, 0.0f);
		RegionOKButton regionOk = (RegionOKButton) areaScreenLayout.findViewById(R.id.region_ok);
		ImageButton regionNo = (ImageButton) areaScreenLayout.findViewById(R.id.region_no);
		area = (AreaScreenView) areaScreenLayout.findViewById(R.id.areaselect);
		regionNo.setOnClickListener(this);
		regionOk.setService(this);
		regionOk.setArea(area);
	}
	
	/**
	 * 删除区域选择
	 */
	public void deleteAreaSelect(){
		if(areaScreenLayout == null){
			return;
		}
		winMsg.removeView(areaScreenLayout);
		areaScreenLayout = null;
		area = null;
	}
	
	/**
	 * 设置菜单按钮的可见情况
	 * @param b
	 */
	public void setMenuButtonVisibility(boolean b){
		if(b){
			floatRelativeLayout.setVisibility(View.VISIBLE);
		}else{
			floatRelativeLayout.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 创建菜单
	 * @param i
	 */
	@SuppressLint("InflateParams")
	public void createMenu(int i){
		if(menuLayout != null){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(getApplication());
		menuLayout = (RelativeLayout) inflater.inflate(R.layout.screenhot_float_relativelayout, null);
		if(i == 1){
			winMsg.addView(menuLayout, PixelFormat.RGBA_8888, LayoutParams.FLAG_NOT_FOCUSABLE, Gravity.START | Gravity.CENTER_VERTICAL, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0f, 0.0f);
		}else{
			winMsg.addView(menuLayout, PixelFormat.RGBA_8888, LayoutParams.FLAG_NOT_FOCUSABLE, Gravity.END | Gravity.CENTER_VERTICAL, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0f, 0.0f);
		}
		RelativeLayout layout = (RelativeLayout) menuLayout.findViewById(R.id.layout);
		RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) layout.getLayoutParams();
		if(i == 1)
			layoutParams.setMargins(-layoutParams.width*2/5, 0, 0, 0);
		else
			layoutParams.setMargins(0, 0, -layoutParams.width*2/5, 0);
			
		FullScreenshotsButton fullscreen = (FullScreenshotsButton) menuLayout.findViewById(R.id.fullscreen);
		RegionScreenshotsButton regionscreen = (RegionScreenshotsButton) menuLayout.findViewById(R.id.regionscreen);
		ToSettingViewButton settingview = (ToSettingViewButton) menuLayout.findViewById(R.id.settingview);
		menuLayout.findViewById(R.id.close_menu).setOnClickListener(this);
		
		fullscreen.setService(this);
		regionscreen.setService(this);
		settingview.setService(this);
		
		RelativeLayout.LayoutParams buttonLayoutParams = (android.widget.RelativeLayout.LayoutParams) fullscreen.getLayoutParams();
//		RelativeLayout.LayoutParams regionLayoutParams = (android.widget.RelativeLayout.LayoutParams) regionscreen.getLayoutParams();
//		RelativeLayout.LayoutParams settingLayoutParams = (android.widget.RelativeLayout.LayoutParams) settingview.getLayoutParams();
		int padding  = layout.getPaddingTop();
		
		fullscreen.CreateAnimate(i, 800, new PointF(layoutParams.width/2, buttonLayoutParams.height/2+ padding), new PointF((int)(layoutParams.width/2 + (Math.sin(5f/6 * Math.PI) * 50)), (int)(layoutParams.height/2 + (Math.cos(5f/6 * Math.PI) * 50))));
		regionscreen.CreateAnimate(i, 800, new PointF(layoutParams.width/2, buttonLayoutParams.height/2+ padding), new PointF((int)(layoutParams.width/2 + (Math.sin(3f/6 * Math.PI) * 50)), (int)(layoutParams.height/2 + (Math.cos(3f/6 * Math.PI) * 50))));
		settingview.CreateAnimate(i, 800, new PointF(layoutParams.width/2, buttonLayoutParams.height/2+ padding), new PointF((int)(layoutParams.width/2 + (Math.sin(1f/6 * Math.PI) * 50)), (int)(layoutParams.height/2 + (Math.cos(1f/6 * Math.PI) * 50))));
			
		fullscreen.startAnimate();
		regionscreen.startAnimate();
		settingview.startAnimate();
		
		
		
//		ani.start();
	}
	
	/**
	 * 删除菜单
	 */
	public void deleteMenu(){
		if(menuLayout == null){
			return;
		}
		winMsg.removeView(menuLayout);
		menuLayout = null;
	}
	
	public void MyFinishSelf(){
		if(winMsg != null){
			WindowManager manager = winMsg.getWindowManager();
//			((FullScreenshotsButton)floatRelativeLayout.findViewById(R.id.screenshots)).delete();
//			floatRelativeLayout.removeAllViews();
		if(areaScreenLayout != null){
			manager.removeView(areaScreenLayout);
		}
		if(menuLayout != null){
			manager.removeView(menuLayout);
		}
			manager.removeView(floatRelativeLayout);
			
			floatRelativeLayout = null;
			winMsg = null;
		}
//		this.stopSelf();
		
	}
	public class Receiver extends BroadcastReceiver{  
	    public void onReceive(Context context, Intent intent) {  
	        Bundle bundle = intent.getExtras(); 
	        boolean b = bundle.getBoolean("deleteService");
	        Log.i("click", "finish");
	        if(b){
	        	ScreenService.this.MyFinishSelf();
	        }
	        
	    }  
	}
	
	
	
	
	class OnTouchListenerImp implements OnTouchListener {
		private Long mLastClickTime;
		private float mX;
		private float mY;
		private int[] mLocation;
		private LayoutParams layoutParams;
		private WindowManager mag;

		public OnTouchListenerImp(LayoutParams layoutParams,WindowManager mag) {
			mLastClickTime = System.currentTimeMillis();
			mLocation = new int[2];
			this.layoutParams = layoutParams;
			this.mag = mag;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mX = event.getRawX();
				mY = event.getRawY();
				v.getRootView().getLocationOnScreen(mLocation);
				mLastClickTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				if (System.currentTimeMillis() - mLastClickTime < 200) {
					v.performClick();
				}
				mLastClickTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_MOVE:
				if (mX == event.getRawX() && mY == event.getRawY()) {
				} else {
//					if(allWindow.getVisibility() == View.VISIBLE){
//						allWindow.setVisibility(View.GONE);
//						allWindow.setVisibility(View.VISIBLE);
//					}
					layoutParams.x = mLocation[0] + (int) (event.getRawX() - mX);
					layoutParams.y = mLocation[1] + (int) (event.getRawY() - mY);
//					mApp.SetFloatBtnPosX(wmParams.x);
//					mApp.SetFloatBtnPosY(wmParams.y);
					// 刷新
					mag.updateViewLayout(v.getRootView(), layoutParams);
				}
				break;
			}
			return false;
		}
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.screenshots){
			WindowManager.LayoutParams layoutParams = (LayoutParams) floatRelativeLayout.getLayoutParams();
			if(layoutParams.x < winMsg.getWindowWidth()/2){
				createMenu(1);
			}else{
				createMenu(2);
			}
			setMenuButtonVisibility(false);
		}
		if(id == R.id.close_menu){
			deleteMenu();
			setMenuButtonVisibility(true);
		}
		
		if(id == R.id.region_no){
			deleteAreaSelect();
			setMenuButtonVisibility(true);
		}
	}
	
	
	

}
