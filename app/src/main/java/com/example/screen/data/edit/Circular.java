package com.example.screen.data.edit;
/**
 * 圆
 */
import android.view.MotionEvent;

import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnSquareChange;

public class Circular extends Content{
	private int R;
	private MyPoint centerOfCircle;
	private OnSquareChange listener;
	
	public void setOnSquareChange(OnSquareChange listener){
		this.listener  = listener;
	}
	
	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public MyPoint getCenterOfCircle() {
		return centerOfCircle;
	}

	public void setCenterOfCircle(MyPoint centerOfCircle) {
		this.centerOfCircle = centerOfCircle;
	}

	public void OnTouch(MyPoint point, int action){
		if(point == null){
			return;
		}
		switch(action){
		case MotionEvent.ACTION_DOWN:
			this.position = point;
			break;
		case MotionEvent.ACTION_MOVE:
			getTheRandCenter(point);
			if(listener != null){
				listener.OnChange();
			}
			break;
		case MotionEvent.ACTION_UP:
			getTheRandCenter(point);
			if(listener != null){
				listener.OnChange();
			}
			break;
		}
	}
	
	/**
	 * 计算圆形位置和半径
	 * @param point
	 */
	private void getTheRandCenter(MyPoint point){
		width = (int) Math.abs(point.x - position.x);
		height = (int) Math.abs(point.y - position.y);
		if(width > height){
			this.R = height/2;
		}else{
			this.R = width/2;
		}
		if(centerOfCircle == null){
			centerOfCircle = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		centerOfCircle.x = (point.x + position.x)/2;
		centerOfCircle.y = (point.y + position.y)/2;
	}
}
