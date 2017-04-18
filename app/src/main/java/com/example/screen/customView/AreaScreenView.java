package com.example.screen.customView;

import com.example.screen.data.Box;
import com.example.screen.data.MyPoint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AreaScreenView extends View{
	
	public AreaScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}
	public AreaScreenView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}


	private float width;
	private float height;
	private Box box;
	private float defaultBoxWidth;
	private float defaultBoxHeight;
	
	
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		MyPoint topLeft = box.getTopLeftPoint();
		MyPoint topRight = box.getTopRightPoint();
		MyPoint buttomLeft = box.getButtomLeftPoint();
		MyPoint buttomRight = box.getButtomRightPoint();
		Paint paint = new Paint();
		paint.setStrokeWidth(5);
		paint.setARGB(255, 255, 255, 255);
//		paint.setStyle(Paint.Style.STROKE);
//		canvas.drawRect(left, top, right, bottom, paint)
		canvas.drawLine(topLeft.x, topLeft.y, topRight.x, topRight.y, paint);
		canvas.drawLine(topLeft.x, topLeft.y, buttomLeft.x, buttomLeft.y, paint);
		canvas.drawLine(buttomLeft.x, buttomLeft.y, buttomRight.x, buttomRight.y, paint);
		canvas.drawLine(topRight.x, topRight.y, buttomRight.x, buttomRight.y, paint);
		paint.setStrokeWidth(8);
		paint.setARGB(255, 0, 0, 255);
		canvas.drawLine(topLeft.x, topLeft.y, topLeft.x + Box.JUDGINGDISTANCE, topLeft.y, paint);
		canvas.drawLine(topLeft.x, topLeft.y, topLeft.x , topLeft.y + Box.JUDGINGDISTANCE, paint);
		canvas.drawLine(topRight.x, topRight.y, topRight.x - Box.JUDGINGDISTANCE, topRight.y, paint);
		canvas.drawLine(topRight.x, topRight.y, topRight.x, topRight.y + Box.JUDGINGDISTANCE, paint);
		canvas.drawLine(buttomLeft.x, buttomLeft.y, buttomLeft.x, buttomLeft.y - Box.JUDGINGDISTANCE, paint);
		canvas.drawLine(buttomLeft.x, buttomLeft.y, buttomLeft.x + Box.JUDGINGDISTANCE, buttomLeft.y, paint);
		canvas.drawLine(buttomRight.x, buttomRight.y, buttomRight.x - Box.JUDGINGDISTANCE, buttomRight.y, paint);
		canvas.drawLine(buttomRight.x, buttomRight.y, buttomRight.x, buttomRight.y - Box.JUDGINGDISTANCE, paint);
	}
	
	public Box getBox(){
		return box;
	}
	
	public void init(){
		this.width = this.getWidth();
		this.height = this.getHeight();
		defaultBoxHeight = this.height/2;
		defaultBoxWidth = this.width/2;
		MyPoint topLeft = new MyPoint(this.width/2-defaultBoxWidth/2, this.height/2 - defaultBoxHeight/2, this.width, this.height);
		box = new Box(topLeft, defaultBoxWidth, defaultBoxHeight, width, height);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		int width2 = getWidth();
		int height2 = getHeight();
		init();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		MyPoint point = new MyPoint(event.getX(), event.getY(), this.width	, this.height);
		box.OnTouch(point, action);
		invalidate();
//		return super.onTouchEvent(event);
		return true;
	}


}
