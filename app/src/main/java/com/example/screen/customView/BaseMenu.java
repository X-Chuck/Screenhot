package com.example.screen.customView;

import com.example.screen.animate.CircularAnimate;
import com.example.screen.service.ScreenService;

import android.app.Service;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BaseMenu extends LinearLayout{

	protected Service service;
	public boolean isClicking = false;
	CircularAnimate ani;
	public BaseMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public BaseMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void delete(){
		this.setOnClickListener(null);
//		this.service = null;
	}
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		delete();
		super.onDetachedFromWindow();
		
	}
	public void setService(Service service) {
		this.service = service;
	}
	
	public void CreateAnimate(int direction,long duration,PointF start, PointF end){
		ani = new CircularAnimate();
//		android.view.ViewGroup.LayoutParams = ((RelativeLayout)this.getParent()).getLayoutParams();
		android.view.ViewGroup.LayoutParams layoutParams =((RelativeLayout)this.getParent()).getLayoutParams();
		ani.setCenterOfCiircle(new PointF(layoutParams.width/2, layoutParams.height/2 ));
		ani.CreatevalueAnimator(start, end);
		ani.setDuration(duration);
		ani.setView(this);
		if(direction != 1)
			ani.setDirection(false);
	}
	
	public void startAnimate(){
		if(ani == null){
			return;
		}
		ani.start();
	}
	
	
	
	
	
	public static final int FINISHHANDLER = 0x119;
	
	/**
	 * 截屏完回调
	 * @author kingsoft
	 *
	 */
	protected class FinishCreenHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == FINISHHANDLER){
				((ScreenService)(BaseMenu.this.service)).setMenuButtonVisibility(true);
				BaseMenu.this.isClicking = false;
			}
		}
	}

}
