package com.example.screen.customView;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Line;
import com.example.screen.listener.OnSquareChange;

public class EditLineView extends Edit{
	
	protected Line conten;
	
	public EditLineView(){
		this.conten = new Line();
		conten.setListener(listener);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(conten == null){
			return;
		}
		MyPoint start = conten.getStart();
		MyPoint end = conten.getEnd();
		if(start == null || end == null){
			return;
		}
		Paint paint = new Paint();
		paint.setColor(getColor());
		paint.setAntiAlias(true);
		paint.setStrokeWidth(10);
		canvas.drawLine(start.x, start.y, end.x, end.y, paint);
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
