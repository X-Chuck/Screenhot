package com.example.screen.customView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Square;
import com.example.screen.listener.OnSquareChange;

public class EditSquareView extends Edit{
	
	protected Square conten;
	private boolean fill;
	public EditSquareView(boolean fill){
		this.fill = fill;
		conten = new Square();
		conten.setListner(listener);
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(conten == null){
			return;
		}
		Paint paint = new Paint();
		paint.setColor(getColor());
		if(fill){
			paint.setStyle(Paint.Style.FILL);
		}else{
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
		}
//		paint.setAntiAlias(true);
		MyPoint topLefPoint = conten.getTopLefPoint();
		if(topLefPoint == null){
			return;
		}
		int width = conten.getWidth();
		int height = conten.getHeight();
		Rect rect = new Rect((int)topLefPoint.x, (int)topLefPoint.y, (int)topLefPoint.x + width, (int)topLefPoint.y + height);
		canvas.drawRect(rect, paint);
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
