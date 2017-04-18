package com.example.screen.customView;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Content;

import android.graphics.Canvas;
import android.view.View;

public abstract class Edit {
	protected int color;
	protected Content conten;
	protected long alpha = 255;
	protected long wdith;
	protected long hight;
	protected View view;
	protected int strokeWidth = 10;
	
	public void setView(View view) {
		this.view = view;
	}
	public View getView() {
		return view;
	}
	public abstract void draw(Canvas canvas);
	public abstract void drawFixed(Canvas canvas);
	public abstract void OnTouch(MyPoint touchPoint ,int action);
	
	public void setColor(int color) {
		this.color = color;
	}
	public int getColor() {
		return color;
	}
	public void setAlpha(long alpha) {
		this.alpha = alpha;
	}
	public long getAlpha() {
		return alpha;
	}
	public void setWdith(long wdith) {
		this.wdith = wdith;
	}
	public void setHight(long hight) {
		this.hight = hight;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public int getStrokeWidth() {
		return strokeWidth;
	}
	
}
