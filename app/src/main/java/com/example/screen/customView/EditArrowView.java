package com.example.screen.customView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.Arrow;
import com.example.screen.listener.OnSquareChange;

public class EditArrowView extends Edit{

	protected Arrow conten;
	public EditArrowView(){
		conten = new Arrow();
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
		MyPoint arrow_one = conten.getArrow_one();
		MyPoint arrow_two = conten.getArrow_two();
		MyPoint arrow_cen = conten.getArrow_cen();
		if(arrow_cen == null || arrow_one == null || arrow_two == null){
			return;
		}
		Paint paint = new Paint();
		paint.setColor(getColor());
		paint.setStrokeWidth(10);
		paint.setAntiAlias(true);
		canvas.drawLine(start.x, start.y, end.x, end.y, paint);
		Path path = new Path();
		path.moveTo(arrow_cen.x,arrow_cen.y);
		path.lineTo(arrow_one.x, arrow_one.y);
		path.lineTo(arrow_two.x, arrow_two.y);
		path.close();
		canvas.drawPath(path, paint);
		
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
