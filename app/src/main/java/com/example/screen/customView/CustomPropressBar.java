package com.example.screen.customView;

import com.xsj_Screen.screen.R;
import com.xsj_Screen.screen.R.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomPropressBar extends View{

	public CustomPropressBar(Context context) {
		this(context, null);
	}
	public CustomPropressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
		pointColor = ta.getColor(R.styleable.CustomProgressBar_defaultcolor, context.getResources().getColor(color.screenhot_btn_gray));
		atPointColor = ta.getColor(R.styleable.CustomProgressBar_activitycolor, Color.RED);
	}


	
	int r;
	int pointColor;
	int atPointColor;
	double angle = 0;
	int pointNun = 12;
	int tag = 1;
	int radius = 10;
	boolean isLoop = true;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		super.onDraw(canvas);
//		canvas = new Canvas();
		int width = this.getWidth();
		int height = this.getHeight();
//		Log.d("par", "width = " + width);
//		Log.d("par", "height = " + height);
		width = width/2;
		height = height/2;
		r = width/3;
		Paint p1 = new Paint();
		p1.setColor(pointColor);
		for(int i = 1; i <= pointNun; i++){
			if(tag > 1){
				if(Math.PI *2*i/pointNun <= angle){
					double x = width + Math.sin(Math.PI*2*i/pointNun) * r;
					double y = height + Math.cos(Math.PI*2*i/pointNun) * r;
					canvas.drawCircle((float)x, (float)y, radius, p1);
				}
			}else{
				if(Math.PI *2*i/pointNun >= angle ){
					double x = width + Math.sin(Math.PI*2*i/pointNun) * r;
					double y = height + Math.cos(Math.PI*2*i/pointNun) * r;
					canvas.drawCircle((float)x, (float)y, radius, p1);
				}
			}
		}
		Paint p2 = new Paint();
		p2.setColor(atPointColor);
		double x = width + Math.sin(angle) * r;
		double y = height + Math.cos(angle) * r;
		canvas.drawCircle((float)x, (float)y, radius + 2, p2);
	}
	
	CustonThread thread;
	public void start(){
		if(thread == null){
			thread = new CustonThread();
		}
		this.isLoop = true;
		thread.start();
	}
	public void stop(){
		this.isLoop = false;
	}
	
	private class CustonThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isLoop){
				angle += Math.PI/30;
				if(angle > Math.PI* 2){
					angle = 0;
					if(tag == 1){
						tag = 2;
						Log.d("par", "变");
					}else{
						tag = 1;
					}
				}
				CustomPropressBar.this.postInvalidate();
				try {
					Thread.sleep((long)(16 * (1 - (Math.cos(angle) + 1)/3)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	

	

}
