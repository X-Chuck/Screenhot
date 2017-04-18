package com.example.screen.animate;


import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.PointF;
import android.view.View;


abstract class CustonBaseAnimate implements CustonAnimate , TypeEvaluator<PointF>{
	protected ValueAnimator valueAnimator;
	protected int DefalutDuration = 1000;
	protected View target;
	public void CreatevalueAnimator(PointF start, PointF end){
		valueAnimator = ValueAnimator.ofObject(this, start	, end);
		valueAnimator.setInterpolator(getTimeInterpolator());
		valueAnimator.addUpdateListener(getAnimatorUpdataListener());
		valueAnimator.setDuration(DefalutDuration);
		valueAnimator.setTarget(target);
	}
	
	protected AnimatorUpdateListener getAnimatorUpdataListener(){
		AnimatorUpdateListener listener = new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				PointF pointF = (PointF)animation.getAnimatedValue();
				target.setX(pointF.x);
				target.setY(pointF.y);
			}
		};
		return listener;
	}
	
	/**
	 * 开始动画
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		if(valueAnimator != null){
			valueAnimator.start();
		}
	}
	/**
	 * 设置动画的对象
	 */
	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		this.target  = view;
		if(valueAnimator != null){
			valueAnimator.setTarget(this.target);
		}
	}
	/**
	 * 设置动画的时长 （毫秒）
	 */
	@Override
	public void setDuration(long duration) {
		// TODO Auto-generated method stub
		if(valueAnimator != null){
			valueAnimator.setDuration(duration);
		}
	}
	/**
	 * 设置重复次数
	 */
	@Override
	public void setRepeatCount(int count) {
		// TODO Auto-generated method stub
		if(valueAnimator != null){
			valueAnimator.setRepeatCount(count);
		}
	}
	/**
	 * 设置重复的类型
	 */
	@Override
	public void setRepeatModel(int value) {
		// TODO Auto-generated method stub
		if(valueAnimator != null){
			valueAnimator.setRepeatMode(value);
		}
	}
	
	/**
	 * 判断动画是否正在运行
	 */
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		if(valueAnimator != null){
			return valueAnimator.isRunning();
		}
		return false;
	}
	
	protected abstract  TimeInterpolator getTimeInterpolator();
}
