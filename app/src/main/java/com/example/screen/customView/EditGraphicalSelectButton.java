package com.example.screen.customView;

import com.example.screen.control.EditGraphicalControler.Graphical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class EditGraphicalSelectButton extends View{

	private Graphical graphical = Graphical.none;
	public EditGraphicalSelectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public EditGraphicalSelectButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setGraphical(Graphical graphical) {
		this.graphical = graphical;
	}
	public Graphical getGraphical() {
		return graphical;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();
		if(graphical == Graphical.square){
			drawSquare(width, height, canvas);
		}
		else if(graphical == Graphical.square_fill){
			drawSquareFill(width, height, canvas);
		}else if(graphical == Graphical.circle){
			drawCircle(width, height, canvas);
		}else if(graphical == Graphical.circle_fill){
			drawCircleFill(width, height, canvas);
		}else if(graphical == Graphical.arrow){
			drawArrow(width, height, canvas);
		}else if(graphical == Graphical.line){
			drawLine(width, height, canvas);
		}
	}
	
	private void drawLine(int width,int height,Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
		canvas.drawLine(width * 5/6f, height/6f, width/6f , height * 5/6f , paint);
	}
	
	private void drawSquare(int width,int height,Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		int x = width/2 - width/3;
		int y = height/2 - height/3;
		canvas.drawRect(x, y, x + width*2/3, y + height*2/3, paint);
	}
	private void drawSquareFill(int width,int height,Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeWidth(3);
		int x = width/2 - width/3;
		int y = height/2 - height/3;
		canvas.drawRect(x, y, x + width*2/3, y + height*2/3, paint);
	}
	
	private void drawCircle(int width,int height,Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		canvas.drawCircle(width/2, height/2, width/3, paint);
	}
	private void drawCircleFill(int width,int height,Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeWidth(3);
		canvas.drawCircle(width/2, height/2, width/3, paint);
	}
	
	private void drawArrow(int width, int height, Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
		canvas.drawLine(width * 5/6f, height/6f, width/6f + 5, height * 5/6f - 5, paint);
		Path path = new Path();
		path.moveTo(width/6f, height * 5/6f);
		path.lineTo(width/6f + 10, height * 5/6f);
		path.lineTo(width/6f, height * 5/6f - 10);
		canvas.drawPath(path, paint);
	}

}
