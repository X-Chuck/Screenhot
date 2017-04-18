package com.example.screen.data;

import java.util.regex.Matcher;

public class MyPoint {

	public float x;
	public float y;
	private float MaxX;
	private float MaxY;
	private float MinX = 0;
	private float MinY = 0;
	private float BackUpX;
	private float BackUpY;
	public MyPoint(float x,float y){
		if(x < 0){
			x =  0;
		}
		if(y < 0){
			y = 0;
		}
		this.x = x;
		this.y = y;
	}
	public MyPoint(float x, float y,float MaxX, float MaxY){
		if(x < 0){
			x =  0;
		}
		if(y < 0){
			y = 0;
		}
		this.x = x;
		this.y = y;
		this.MaxX = MaxX;
		this.MaxY = MaxY;
	}
	
	public MyPoint(float x, float y, float MaxX, float MaxY,float MinX, float MinY){
		if(x < MinX){
			x = MinX;
		}
		if(x > MaxX){
			x = MaxX;
		}
		if(y < MinY){
			y = MinY;
		}
		if(y > MaxY){
			y = MaxY;
		}
		this.x = x;
		this.y = y;
		this.MaxX = MaxX;
		this.MaxY = MaxY;
		this.MinX = MinX;
		this.MinY = MinY;
	}
	
	public void setMinX(float minX) {
		MinX = minX;
	}
	public void setMinY(float minY) {
		MinY = minY;
	}
	public float getMinX() {
		return MinX;
	}
	public float getMinY() {
		return MinY;
	}
	public float getMaxX() {
		return MaxX;
	}

	public void setMaxX(float maxX) {
		MaxX = maxX;
	}

	public float getMaxY() {
		return MaxY;
	}

	public void setMaxY(float maxY) {
		MaxY = maxY;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * 得到与另外一个点的距离
	 * @param other
	 * @return
	 */
	public float getDistance(MyPoint other){
		if(other == null){
			return 0;
		}
		return (float)Math.sqrt(Math.pow(other.x-this.x, 2) + Math.pow(other.y - this.y, 2));
	}
	
	/**
	 * 得到与另一个点的x轴距离
	 * @param other
	 * @return
	 */
	public float getXDistance(MyPoint other){
		if(other == null){
			return 0;
		}
		return Math.abs(other.x - this.x);
	}
	
	/**
	 * 得到与另一个点的y轴距离
	 * @param other
	 * @return
	 */
	public float getYDistance(MyPoint other){
		if(other == null){
			return 0;
		}
		return Math.abs(other.y - this.y);
	}
	
	/**
	 * 按向量移动
	 * @param start 向量开始点
	 * @param end 向量终点
	 */
	public void VectorMove(MyPoint start, MyPoint end){
		if(start == null || end == null){
			return ;
		}
		float x = end.x - start.x;
		float y = end.y - start.y;
		BackUp(this.x, this.y);
		this.x += x;
		this.y += y;
		if(this.x < MinX)
			this.x = MinX;
		if(this.y < MinY)
			this.y = MinY;
		if(this.x > this.MaxX)
			this.x = this.MaxX;
		if(this.y > this.MaxY)
			this.y = this.MaxY;
		
	}
	
	/**
	 * 根据向量横向移动
	 * @param start
	 * @param end
	 */
	public void VectorMoveX(MyPoint start, MyPoint end){
		if(start == null || end == null){
			return;
		}
		float x = end.x - start.x;
		BackUp(this.x, this.y);
		this.x += x;
		if(this.x < MinX)
			this.x = MinX;
		if(this.x > this.MaxX){
			this.x = this.MaxX;
		}
	}
	
	/**
	 * 根据向量纵向移动
	 * @param start
	 * @param end
	 */
	public void VectorMoveY(MyPoint start, MyPoint end){
		if(start  == null || end == null){
			return ;
		}
		float y = end.y - start.y;
		
		BackUp(this.x,this.y);
		this.y += y;
		if(this.y < MinY){
			this.y = MinY;
		}
		if(this.y > this.MaxY){
			this.y = this.MaxY;
		}
	}
	
	/**
	 * 备份
	 * @param x
	 * @param y
	 */
	public void BackUp(float x, float y){
		this.BackUpX = x;
		this.BackUpY = y;
	}
	
	/**
	 * 回退
	 */
	public void CallBack(){
		this.x = this.BackUpX;
		this.y = this.BackUpY;
	}
}
