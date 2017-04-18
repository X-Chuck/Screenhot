package com.example.screen.customView;

import com.example.screen.SettingActivity;
import com.example.screen.service.ScreenService;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ToSettingViewButton extends BaseMenu{

	private ClickListener listener;
	public ToSettingViewButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public ToSettingViewButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	private void init(){
		listener = new ClickListener();
		this.setOnClickListener(listener);
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
//			FinishCreenHandler handler = new FinishCreenHandler();
//			ScreenControler controler = ScreenControler.GetInstance();
//			v.getRootView().setVisibility(View.GONE);
//			controler.FullScreenshots(handler);
			((ScreenService)service).deleteMenu();
			Intent intent = new Intent(service.getBaseContext(), SettingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			service.startActivity(intent);
			
		}

		
	}

	

}
