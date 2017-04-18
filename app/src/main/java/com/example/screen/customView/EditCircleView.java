package com.example.screen.customView;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Circular;
import com.example.screen.listener.OnSquareChange;

public class EditCircleView extends Edit{

	protected Circular conten;
	private boolean fill;
	public EditCircleView(boolean fill){
		this.fill = fill;
		conten = new Circular();
		conten.setOnSquareChange(listener);
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(conten == null){
			return;
		}
		MyPoint centerOfCircle = conten.getCenterOfCircle();
		if(centerOfCircle == null){
			return;
		}
		int r = conten.getR();
		Paint paint = new Paint();
		paint.setColor(getColor());
		paint.setAntiAlias(true);
		if(!fill){
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
		}
		canvas.drawCircle(centerOfCircle.x, centerOfCircle.y, r, paint);
		
	}

	@Override
	public void drawFixed(Canvas canvas) {
		// TODO Auto-generated method stub
		draw(canvas);
	}

	@Override
	public void OnTouch(MyPoint touchPoint, int action) {
		// TODO Auto-generated method stub
		if(conten != null){
			conten.OnTouch(touchPoint, action);
		}
	}
	private OnSquareChange listener = new OnSquareChange() {
		
		@Override
		public void OnChange() {
			// TODO Auto-generated method stub
			if(view != null){
				view.invalidate();
			}
		}
	};

}
