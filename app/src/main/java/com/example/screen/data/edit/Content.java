package com.example.screen.data.edit;

import com.example.screen.data.MyPoint;

public abstract class Content {
	protected int width;
	protected int height;
	protected MyPoint position;//内容位置
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public MyPoint getPosition() {
		return position;
	}
	public void setPosition(MyPoint position) {
		this.position = position;
	}
	
	

}
