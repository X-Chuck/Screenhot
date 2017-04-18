package com.example.screen.data.edit;

import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnSquareChange;

import android.graphics.Path;
import android.view.MotionEvent;

public class Stroke extends Content{

	private Path path;
	private MyPoint start;
	private MyPoint end;
	private OnSquareChange listener;
	public Path getPath() {
		return path;
	}
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
	public void setPath(Path path) {
		this.path = path;
	}
	public OnSquareChange getListener() {
		return listener;
	}
	public void setListener(OnSquareChange listener) {
		this.listener = listener;
	}
	public void OnTouch(MyPoint point, int action){
		if(point == null){
			return;
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if(start == null){
				start = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
			}
			if(end == null){
				end = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
			}
			path = new Path();
			path.moveTo(point.x, point.y);
			start.x = point.x;
			start.y = point.y;
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			path.lineTo(point.x, point.y);
			end.x = point.x;
			end.y = point.y;
			if(listener != null){
				listener.OnChange();
			}
			break;

		default:
			break;
		}
	}
}
