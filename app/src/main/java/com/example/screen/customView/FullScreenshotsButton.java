package com.example.screen.customView;


import com.example.screen.control.ScreenControler;
import com.example.screen.service.ScreenService;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FullScreenshotsButton extends BaseMenu {

//	public boolean isClicking = false;
	private ClickListener listener;
//	public static final int FINISHHANDLER = 0x119;
	
//	/**
//	 * 截屏完回调
//	 * @author kingsoft
//	 *
//	 */
//	private class FinishCreenHandler extends Handler{
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			if(msg.what == FINISHHANDLER){
//				((ScreenService)(FullScreenshotsButton.this.service)).setMenuButtonVisibility(true);
//				FullScreenshotsButton.this.isClicking = false;
//			}
//		}
//	}
	
	private class ClickListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			if(isClicking){
				return;
			}
			// TODO Auto-generated method stub
			Log.i("click", "float windw click");
			isClicking = true;
			FinishCreenHandler handler = new FinishCreenHandler();
			ScreenControler controler = ScreenControler.GetInstance();
//			v.getRootView().setVisibility(View.GONE);
			((ScreenService)service).deleteMenu();
			controler.FullScreenshots(handler);
			
		}

		
	}
	
	@Override
		public void onWindowFocusChanged(boolean hasWindowFocus) {
			// TODO Auto-generated method stub
			super.onWindowFocusChanged(hasWindowFocus);
			if(getVisibility() == View.VISIBLE){
				startAnimate();
			}
		}
	
	@Override
		protected void onDetachedFromWindow() {
			// TODO Auto-generated method stub
			super.onDetachedFromWindow();
//			startAnimate();
		}
	

	public FullScreenshotsButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public FullScreenshotsButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		listener = new ClickListener();
		this.setOnClickListener(listener);
		
	}
	
	

	//监听长按
	
	

}
