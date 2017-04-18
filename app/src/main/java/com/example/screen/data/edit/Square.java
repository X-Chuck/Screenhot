package com.example.screen.data.edit;
/**
 * 矩形
 */
import android.location.GpsStatus.Listener;
import android.view.MotionEvent;

import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnSquareChange;

public class Square extends Content{
	
	private OnSquareChange listner;
	private MyPoint topLeft;
	
	
	public void setListner(OnSquareChange listner) {
		this.listner = listner;
	}
	public OnSquareChange getListner() {
		return listner;
	}
	
	
	
	public MyPoint getTopLefPoint(){
		return this.topLeft;
	}
	
	public void OnTouch(MyPoint point, int action){
		if(point == null){
			return;
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			this.position = point;
			break;
		case MotionEvent.ACTION_MOVE:
			UpdataWidthAndHeight(point);
			if(listner != null){
				listner.OnChange();
			}
			break;
		case MotionEvent.ACTION_UP:
			UpdataWidthAndHeight(point);
			if(listner != null){
				listner.OnChange();
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 计算方框的宽高和左顶点
	 * @param point
	 */
	private void UpdataWidthAndHeight(MyPoint point){
		this.width = (int) Math.abs(point.x - this.position.x);
		this.height = (int) Math.abs(point.y - this.position.y);
		if(topLeft == null){
			topLeft = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		if(point.x > this.position.x){
			topLeft.x = position.x;
		}else{
			topLeft.x = point.x;
		}
		if(point.y > this.position.y){
			topLeft.y = this.position.y;
		}else{
			topLeft.y = point.y;
		}
	}
}
