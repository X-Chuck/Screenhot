package com.example.screen.customView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.screen.data.MyPoint;
import com.example.screen.data.edit.WrittenWords;
import com.example.screen.frament.EditPictureFragment;
import com.example.screen.listener.OnWrittenWordsChange;
import com.xsj_Screen.screen.R;

public class EditWrittenWrodsView extends Edit{
	
	protected WrittenWords conten;
	EditPictureFragment fragment;
	Bitmap closeButton;
	Bitmap tiaozButton;
	
	public WrittenWords getConten() {
		return conten;
	}
	
	public EditWrittenWrodsView(long width, long heght){
		this.wdith = width;
		this.hight = heght;
		MyPoint contentCode  = new MyPoint(wdith/2, heght/2, 100000, 10000,-10000,-100000); 
		conten = new WrittenWords(contentCode);
//		initBitmap();
		conten.setOnWrittenWordsChange(listener);
	}
	
	private void initBitmap(){
		Bitmap closeButtons = BitmapFactory.decodeResource(view.getResources(), R.drawable.screenhot_close_gray);
		
		int width = closeButtons.getWidth();
		int scal = width/(conten.getCloseButtonR_V() * 5);
		BitmapFactory.Options options = new Options();
		options.inSampleSize = scal;
		closeButton = BitmapFactory.decodeResource(view.getResources(), R.drawable.screenhot_close_gray, options);
		closeButton = bitmapScale(0.2f, 0.2f, closeButton);
		tiaozButton = BitmapFactory.decodeResource(view.getResources(), R.drawable.screenhot_double_jiantou,options);
		tiaozButton = bitmapScale(0.2f, 0.2f, tiaozButton);
		
	}
	
	private Bitmap bitmapScale(float x,float y,Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.postScale(x, y);
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix,true);
		return bitmap2;
		
	}
	
	public void setFragment(EditPictureFragment fragment){
		this.fragment = fragment;
	}
	
	
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(this.getColor());
		paint.setAlpha((int) getAlpha());
		paint.setAntiAlias(true);
//		Canvas canvas = new Canvas();
		if(conten == null){
			Log.d("text", "conten == null");
		}
		//画关闭按钮
		if(closeButton == null){
			initBitmap();
		}
		canvas.rotate((float) (conten.getRotationAngle()  * 57.29578));
		
		canvas.drawBitmap(closeButton, conten.getCloseButton().x, conten.getCloseButton().y, paint);
		//画调整按钮
		
		canvas.drawBitmap(tiaozButton, conten.getAdjustButton().x, conten.getAdjustButton().y, paint);
		//画方框
//		Log.d("dou", "" + conten.getContentCore().x + "  " + conten.getContentCore().y);
		float x = conten.getPosition().x;
//		float x = (float) (conten.getPosition().x * (1 + Math.sin(conten.getRotationAngle())));
		float y = conten.getPosition().y;
//		float y = (int)(conten.getPosition().y * (1 + Math.cos(conten.getRotationAngle())));
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(x, y, x + conten.getWidth(), y + conten.getHeight(), paint);
		//画文字
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(conten.getSize());
		canvas.drawText(conten.getContent(), x, y + conten.getHeight() * 4/5f, paint);
	}
	
	OnWrittenWordsChange listener = new OnWrittenWordsChange() {
		
		@Override
		public void onViewClose() {
			// TODO Auto-generated method stub
			if(fragment!= null){
				fragment.closeTextEdit();
			}
		}
		
		@Override
		public void onContentChange(String content) {
			// TODO Auto-generated method stub
			view.invalidate();
		}
	};

	@Override
	public void OnTouch(MyPoint touchPoint, int action) {
		// TODO Auto-generated method stub
		conten.OnTouch(touchPoint, action);
	}

	@Override
	public void drawFixed(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(this.getColor());
		paint.setAlpha((int) getAlpha());
//		Canvas canvas = new Canvas();
		if(conten == null){
			Log.d("text", "conten == null");
		}
		//画关闭按钮
		if(closeButton == null){
			initBitmap();
		}
		canvas.rotate((float) (conten.getRotationAngle()  * 57.29578));
		
		float x = conten.getPosition().x;
		float y = conten.getPosition().y;
		//画文字
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(conten.getSize());
		canvas.drawText(conten.getContent(), x, y + conten.getHeight() * 4/5f, paint);
	}

}
