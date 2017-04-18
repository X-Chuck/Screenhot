package com.example.screen.data.edit;
/**
 * 箭头
 */
import android.view.MotionEvent;

import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnSquareChange;

public class Arrow extends Content{
	private MyPoint start;
	private MyPoint end;
	private MyPoint arrow_one;
	private MyPoint arrow_two;
	private MyPoint arrow_cen;
	private OnSquareChange listener;
	public MyPoint getStart() {
		return start;
	}
	public void setArrow_cen(MyPoint arrow_cen) {
		this.arrow_cen = arrow_cen;
	}
	public MyPoint getArrow_cen() {
		return arrow_cen;
	}
	public void setStart(MyPoint start) {
		this.start = start;
	}
	public MyPoint getEnd() {
		return end;
	}
	public void setEnd(MyPoint end) {
		this.end = end;
	}
	public OnSquareChange getListener() {
		return listener;
	}
	public void setListener(OnSquareChange listener) {
		this.listener = listener;
	}
	
	public MyPoint getArrow_one() {
		return arrow_one;
	}
	public void setArrow_one(MyPoint arrow_one) {
		this.arrow_one = arrow_one;
	}
	public MyPoint getArrow_two() {
		return arrow_two;
	}
	public void setArrow_two(MyPoint arrow_two) {
		this.arrow_two = arrow_two;
	}
	public void OnTouch(MyPoint point, int action){
		if(point == null){
			return;
		}
		switch(action){
		case MotionEvent.ACTION_DOWN:
			this.start = point;
			break;
		case MotionEvent.ACTION_MOVE:
			this.end = point;
			getOther();
			if(listener != null){
				listener.OnChange();
			}
			break;
		case MotionEvent.ACTION_UP:
			this.end = point;
			if(listener != null){
				listener.OnChange();
			}
			break;
		}
	}
	
	private void getOther(){
		float angle = (float) ((float) Math.atan(((float)end.y - start.y)/(end.x - start.x)));
		if(end.x > start.x){
			angle += Math.PI;
		}
		if(arrow_one == null){
			arrow_one = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		arrow_one.x = (float) (end.x + Math.cos(angle + 0.6f)* 20);
		arrow_one.y = (float)(end.y + Math.sin(angle + 0.6f) * 20);
		if(arrow_two == null){
			arrow_two = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		arrow_two.x = (float) (end.x + Math.cos(angle - 0.6f)* 20);
		arrow_two.y = (float)(end.y + Math.sin(angle - 0.6f) * 20);
		if(arrow_cen == null){
			arrow_cen = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		arrow_cen.x = (float)(end.x + Math.cos(angle + Math.PI) * 30);
		arrow_cen.y = (float)(end.y + Math.sin(angle + Math.PI) * 30);
	}
	
}
