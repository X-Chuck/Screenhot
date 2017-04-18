package com.example.screen.customView;

import com.example.screen.control.ScreenControler;
import com.example.screen.data.Box;
import com.example.screen.data.MyPoint;
import com.example.screen.service.ScreenService;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RegionOKButton extends BaseMenu{

	private ClickListener listener;
	private AreaScreenView area;
	public RegionOKButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public RegionOKButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		listener = new ClickListener();
		this.setOnClickListener(listener);
	}
	
	public void setArea(AreaScreenView area){
		this.area = area;
	}

	private class ClickListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			if(isClicking){
				return;
			}
			if(area == null){
				((ScreenService)service).deleteAreaSelect();
				((ScreenService)service).setMenuButtonVisibility(true);
				return;
			}
			// TODO Auto-generated method stub
			Log.i("click", "float windw click");
			isClicking = true;
			FinishCreenHandler handler = new FinishCreenHandler();
			ScreenControler controler = ScreenControler.GetInstance();
			Box box = area.getBox();
			MyPoint topLeftPoint = box.getTopLeftPoint();
			float boxWidth = box.getBoxWidth();
			float boxHeight = box.getBoxHeight();
			controler.RegionScreenShots(handler, (int)topLeftPoint.x, (int)topLeftPoint.y, (int)boxWidth, (int)boxHeight);
			((ScreenService)service).deleteAreaSelect();
		}

		
	}

	

}
