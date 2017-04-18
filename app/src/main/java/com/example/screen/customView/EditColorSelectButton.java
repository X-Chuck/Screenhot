package com.example.screen.customView;
/**
 * 颜色选择按钮
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class EditColorSelectButton extends View{

	private int color = Color.RED;
	public EditColorSelectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public EditColorSelectButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		int width = this.getWidth();
		int height = this.getHeight();
		canvas.drawCircle(width/2, height/2, width/3 + 3, paint);
		paint.setColor(color);
		canvas.drawCircle(width/2, height/2, width/3, paint);
	}
	public void setColor(int color){
		this.color = color;
	}
	
	public int getColor(){
		return color;
	}

}
