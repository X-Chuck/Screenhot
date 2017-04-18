package com.example.screen.customView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Stroke;
import com.example.screen.listener.OnSquareChange;

public class EditStrokeView extends Edit{

	private Stroke conten;
	public EditStrokeView(){
		conten = new Stroke();
		conten.setListener(listener);
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(conten == null){
			return;
		}
		Path path = conten.getPath();
		if(path == null){
			return;
		}
		Paint paint = new Paint();
		paint.setColor(getColor());
		paint.setAlpha((int)getAlpha());
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		int strokeWidth2 = getStrokeWidth();
		if(strokeWidth2 == 0){
			strokeWidth2 = 1;
		}
		paint.setStrokeWidth(strokeWidth2);
		canvas.drawPath(path, paint);
		MyPoint start = conten.getStart();
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(start.x, start.y, strokeWidth2/2, paint);
		MyPoint end = conten.getEnd();
		canvas.drawCircle(end.x, end.y, strokeWidth2/2, paint);
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
