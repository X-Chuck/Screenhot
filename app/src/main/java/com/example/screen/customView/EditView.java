package com.example.screen.customView;

import com.example.screen.data.MyPoint;
import com.example.screen.interfaces.EditAttr;
import com.example.screen.listener.OnCompleteAnEdit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EditView extends View implements EditAttr{

	public EditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public EditView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private Edit edit;
	private boolean fixed = false;
	private OnCompleteAnEdit listener;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(edit == null){
			return;
		}
		if(fixed){
			edit.drawFixed(canvas);
		}else{
			edit.draw(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(!fixed){
			int action = event.getAction();
			edit.OnTouch(new MyPoint(event.getX(), event.getY(), 10000, 10000, -100000, -100000), action);
			this.invalidate();
		}
		if(listener != null  && event.getAction() == MotionEvent.ACTION_UP){
			listener.OnCompleteEdit();
		}
		return true;
	}
	
	public void setOnCompleteAnEditListener(OnCompleteAnEdit listener){
		this.listener = listener;
	}
	
	public void fixed(){
		this.fixed = true;
		listener = null;
		this.invalidate();
	}
	
	public boolean isFixed(){
		return this.fixed;
	}

	@Override
	public void setColor(int color)
	{
		edit.setColor(color);
	}

	public void setEdit(Edit edit){
		this.edit = edit;
		if(this.edit != null){
			edit.setView(this);
		}
	}
	
	public Edit getEdit(){
		return this.edit;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		this.edit = null;
		
	}

}
