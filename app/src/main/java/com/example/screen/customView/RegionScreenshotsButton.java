package com.example.screen.customView;

import com.example.screen.control.ScreenControler;
import com.example.screen.service.ScreenService;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RegionScreenshotsButton extends BaseMenu{

	private ClickListener listener;
	public RegionScreenshotsButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public RegionScreenshotsButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		listener = new ClickListener();
		this.setOnClickListener(listener);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasWindowFocus);
		if(getVisibility() == View.VISIBLE){
			startAnimate();
		}
	}
	
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
			((ScreenService)service).CreateAreSelect();
//			controler.FullScreenshots(handler);
			
		}

		
	}

}
