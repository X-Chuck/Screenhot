package com.example.screen.animate;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

public class CircularAnimate extends CustonBaseAnimate{
	private float angle = 0;//总的旋转角度
	private float angle_first = 0;
	private boolean direction = true;//旋转方向
	private PointF CenterOfCiircle;//圆心
	private float radius;//半径

	/**
	 * 设置转动的方向
	 * @param direction true为顺时针
	 */
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	/**
	 * 设置圆形坐标
	 * @param centerOfCiircle
	 */
	public void setCenterOfCiircle(PointF centerOfCiircle) {
		CenterOfCiircle = centerOfCiircle;
	}
	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		// TODO Auto-generated method stub
		
		PointF point = new PointF();
		float ang = angle * fraction;
		if(direction){
			ang = angle_first + ang; 
		}else{
			ang = angle_first - ang;
		}
		Log.d("ani", "ang = " + ang);
		int tagwidth = this.target.getLayoutParams().width;
		int tagHeight = this.target.getLayoutParams().height;
		point.x = (float) (CenterOfCiircle.x + (Math.sin(ang) * radius) -tagwidth/2);
		point.y = (float) (CenterOfCiircle.y - (Math.cos(ang) * radius) -tagHeight/2);
		
		Log.d("ani", "aaa = " + (Math.cos(ang) * radius));
		return point;
	}

	@Override
	protected TimeInterpolator getTimeInterpolator() {
		// TODO Auto-generated method stub
		TimeInterpolator time = new TimeInterpolator() {
			private float mFactor = 2.0f;
			@Override
			public float getInterpolation(float input) {
				// TODO Auto-generated method stub
				float result;
				if (mFactor == 1.0f) {
					result = (1.0f - ((1.0f - input) * (1.0f - input)));
				} else {
					result = (float)(1.0f - Math.pow((1.0f - input), 2 * mFactor));
				}
				return result;
			}
		};
		return time;
	}	
	
	@Override
	public void CreatevalueAnimator(PointF start, PointF end) {
		// TODO Auto-generated method stub
		super.CreatevalueAnimator(start, end);
		
		angle = Angle(CenterOfCiircle, start, end);
		if(LinearEquation(start, CenterOfCiircle, end) < 0){
			if(direction){
				angle = (float) (2 * Math.PI - angle);
			}
		}
		if(LinearEquation(start, CenterOfCiircle, end) > 0){
			if(direction == false){
				angle = (float)(2 * Math.PI - angle);
			}
		}
		Log.d("ani", "angle = " + angle);
		angle_first = Angle(CenterOfCiircle,start,new PointF(CenterOfCiircle.x, CenterOfCiircle.y-1));
		if(start.x < CenterOfCiircle.x){
			angle_first = -angle_first;
		}
		Log.d("ani", "angle_first = " + angle_first);
		this.radius = Distance(start, CenterOfCiircle);
	}
	
	/**
	 * 两点式判断点x是在直线上方还是直线下方
	 * @param one 点1
	 * @param two 点2
	 * @param X 要判断的点
	 * @return
	 */
	private float LinearEquation(PointF one, PointF two,PointF X){
		float y = (((X.x-two.x)/(one.x-two.x))*(one.y-two.y))+two.y;
		return X.y - y;
	}
	
	/**
	 * 获取两个点的距离
	 * @param one
	 * @param two
	 * @return
	 */
	private float Distance(PointF one, PointF two){
		float result = (float) Math.sqrt(Math.pow(one.x - two.x,2) + Math.pow(one.y - two.y, 2));
		return result;
	}
	
	
	/**
	 * 计算角的弧度
	 * @param cen
	 * @param first
	 * @param second
	 * @return
	 */
	private  float Angle(PointF cen, PointF first, PointF second) 
	{ 
	    float dx1, dx2, dy1, dy2; 
	    float angle; 
	    dx1 = first.x - cen.x; 
	    dy1 = first.y - cen.y; 
	    dx2 = second.x - cen.x; 
	    dy2 = second.y - cen.y; 
	    float c = (float)Math.sqrt(dx1 * dx1 + dy1 * dy1) * (float)Math.sqrt(dx2 * dx2 + dy2 * dy2); 
	    if (c == 0) return -1; 
	    angle = (float)Math.acos((dx1 * dx2 + dy1 * dy2) / c); 
	    return angle; 
	} 
	
}
