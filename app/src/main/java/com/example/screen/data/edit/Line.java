package com.example.screen.data.edit;
/**
 * 直线
 */
import android.view.MotionEvent;

import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnSquareChange;

public class Line extends Content{
	private MyPoint start;
	private MyPoint end;
	private OnSquareChange listener;
	public MyPoint getStart() {
		return start;
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
	public void setListener(OnSquareChange listener) {
		this.listener = listener;
	}
	public OnSquareChange getListener() {
		return listener;
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
		case MotionEvent.ACTION_UP:
			this.end = point;
			if(listener != null){
				listener.OnChange();
			}
			break;
		}
	}
}
