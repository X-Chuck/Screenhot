package com.example.screen.data.edit;
/**
 * 文字
 */
import com.example.screen.data.MyPoint;
import com.example.screen.listener.OnWrittenWordsChange;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class WrittenWords extends Content implements TextWatcher{
	public final String defalutContent = "请输入文字";
	private String content = defalutContent; //文字内容
	private int size = 120; // 文字大小
	private final int MinSize = 30;
	private final int MaxSize = 300;
	private OnWrittenWordsChange listener; //文字内容改变监听器
	private MyPoint closeButton; //关闭按钮位置
	private MyPoint closeButton_V;
	private int closeButtonR = 80;//关闭按钮的感应半
	private int closeButtonR_V = 60;//关闭按钮的视图半径
	private MyPoint adjustButton; //调节大小按钮
	private MyPoint adjustButton_V;
	private int adjustButtonR = 80;
	private int adjustButtonR_V = 60;
	private MyPoint contentCore;//文字中心
	private MyPoint contentCore_V;
	private ThouchModel touchMode = ThouchModel.none;
	private double rotationAngle = 0;
	private double scaling = 1;
	private MyPoint position_V;
	public WrittenWords(MyPoint point){
		this.contentCore = point;
		this.contentCore_V = new MyPoint(point.x, point.y, 100000, 10000, -10000, -10000);
		this.contentCore_V = CalculationOfRatation(this.contentCore);
		setSize(this.size);
	}
	
	/**
	 * 设置文字位置
	 */
	public void setContentCore(MyPoint contentCore) {
		this.contentCore = contentCore;
		this.contentCore_V = new MyPoint(contentCore.x, contentCore.y, 100000, 10000, -10000, -10000);
	}
	public MyPoint getContentCore() {
		return contentCore_V;
	}
	
	public void setPosition(MyPoint position) {
		this.position = position;
	}
	
	
	
	public MyPoint getCloseButton() {
		return closeButton_V;
	}

	public void setCloseButton(MyPoint closeButton) {
		this.closeButton = closeButton;
	}

	public MyPoint getAdjustButton() {
		return adjustButton_V;
	}

	public void setAdjustButton(MyPoint adjustButton) {
		this.adjustButton = adjustButton;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setSize(int Size){
		if(Size <  this.MinSize){
			Size = this.MinSize;
		}
		if(Size >  this.MaxSize){
			Size = this.MaxSize;
		}
		this.size = Size;
		int len = content.length();
		int c_len = getchinaCount(content);
		this.width = size * c_len + ((size * (len - c_len)) * 16/30);
		this.height = (int) (size * 1.2f);
		CreateAndUpdateAdjustButton();
		CreateAndUpdateCloseButton();
		CreateAndUpdatePosition();
	}
	
	/**
	 * 获取字符串中中文个数
	 * @param str
	 * @return
	 */
	private int getchinaCount(String str){
		String regex = "[\u4e00-\u9fff]";
		int count = (" " + str + " ").split(regex).length - 1;
		return count;
	}
	
	public int getCloseButtonR_V() {
		return closeButtonR_V;
	}

	public void setCloseButtonR_V(int closeButtonR_V) {
		this.closeButtonR_V = closeButtonR_V;
	}

	public int getAdjustButtonR_V() {
		return adjustButtonR_V;
	}

	public void setAdjustButtonR_V(int adjustButtonR_V) {
		this.adjustButtonR_V = adjustButtonR_V;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	public double getScaling() {
		return scaling;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	public int getSize() {
		return size;
	}

	/**
	 * 创建和更新position按钮位置
	 */
	private void CreateAndUpdatePosition(){
		if(this.position == null){
			this.position = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
			this.position_V = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		if(this.contentCore == null){
			return;
		}
//		int c_len = (int) Math.sqrt(Math.pow(this.height/2, 2) + Math.pow(this.width/2,2));
//		position.x = (float) (contentCore.x - Math.cos(this.rotationAngle) * c_len);
		position.x = (float) (contentCore.x - this.width/2);
//		position.y = (float) (contentCore.y - Math.sin(this.rotationAngle) * c_len);
		position.y = (float) (contentCore.y - this.height/2);
//		position = CalculationOfRatation(position);
		position_V.x = (float) (contentCore_V.x - this.width/2);
		position_V.y = (float) (contentCore_V.y - this.height/2);
	}
	
	@Override
	public MyPoint getPosition() {
		// TODO Auto-generated method stub
		return this.position_V;
	}
	
	/**
	 * 创建和更新调整按钮位置
	 */
	private void CreateAndUpdateAdjustButton(){
		if(this.adjustButton == null){
			this.adjustButton = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
			this.adjustButton_V = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		if(this.contentCore == null){
			return;
		}
//		int c_len = (int) Math.sqrt(Math.pow(this.height/2, 2) + Math.pow(this.width/2,2));
		adjustButton.x = (float) (contentCore.x + this.width/2) - this.adjustButtonR_V/2;
		adjustButton.y = (float) (contentCore.y + this.height/2) - this.adjustButtonR_V/2;
		adjustButton_V.x = (float) (contentCore_V.x + this.width/2) - this.adjustButtonR_V/2;
		adjustButton_V.y = (float) (contentCore_V.y + this.height/2) - this.adjustButtonR_V/2;
//		adjustButton = CalculationOfRatation(adjustButton);
	}
	
	/**
	 * 创建和更新关闭按钮位置
	 */
	private void CreateAndUpdateCloseButton(){
		if(this.closeButton == null){
			this.closeButton = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
			this.closeButton_V = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		}
		if(this.contentCore == null){
			return;
		}
//		int c_len = (int) Math.sqrt(Math.pow(this.height/2, 2) + Math.pow(this.width/2,2));
		closeButton.x = (float) (contentCore.x - this.width/2) - this.closeButtonR_V/2;
		closeButton.y = (float) (contentCore.y - this.height/2) - this.closeButtonR_V/2;
		closeButton_V.x = (float) (contentCore_V.x - this.width/2) - this.closeButtonR_V/2;
		closeButton_V.y = (float) (contentCore_V.y - this.height/2) - this.closeButtonR_V/2;
//		closeButton = CalculationOfRatation(closeButton);
	}
	
	/**
	 * 计算旋转后坐标
	 * @param point
	 * @return
	 */
	private MyPoint CalculationOfRatation(MyPoint point){
		if(point == null){
			return null;
		}
		double r = point.getDistance(new MyPoint(0f, 0f));
		double atan = 0;
		if(point.x != 0 && point.y > 0){
			float ant = point.y/point.x;
			atan = Math.atan(ant);
			if(atan < 0){
				atan += Math.PI;
			}
		}
		else if(point.x != 0 && point.y < 0){
			float ant = point.y/point.x;
			atan = Math.atan(ant);
			if(point.x < 0){
				atan += Math.PI;
			}
		}
		else if(point.x != 0 && point.y == 0){
			if(point.x > 0){
				atan = 0;
			}
			else{
				atan = Math.PI;
			}
		}
		else if(point.x == 0){
			if(point.y > 0){
				atan = Math.PI/2;
			}else{
				atan = Math.PI  + Math.PI/2;
			}
		}
//		Log.d("atan", "atan1 =" + atan * 57.29578);
		atan = atan - this.rotationAngle;
//		Log.d("atan", "atan2 = " + atan * 57.29578);
		MyPoint mypoint = new MyPoint(0, 0, 10000, 10000, -10000, -10000);
		mypoint.x = (float) (r * Math.cos(atan));
		mypoint.y = (float) (r * Math.sin(atan));
		return mypoint;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	
	public void setOnWrittenWordsChange(OnWrittenWordsChange listener){
		this.listener = listener;
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		content = s.toString();
		if(content == null || content.equals("")){
			content = defalutContent;
		}
		setSize(this.size);
		
		if(listener != null){
			listener.onContentChange(content);
		}
	}
	
	private MyPoint lastThouch;
	private int R;
	public void OnTouch(MyPoint touchPoint ,int action){
		if(touchPoint == null){
			return;
		}
		switch(action){
		case MotionEvent.ACTION_DOWN:
			getThouchMode(touchPoint);
			lastThouch = touchPoint;
			R = this.width/2;
			break;
		case MotionEvent.ACTION_MOVE:
			if(this.touchMode == ThouchModel.move){
				MoveContent(touchPoint);
			}else if(this.touchMode == ThouchModel.rotationAndSize){
				RotationAndScaling(touchPoint);
			}
			break;
		case MotionEvent.ACTION_UP:
			sureClose(touchPoint);
			touchMode = ThouchModel.none;
			break;
		default:
			
		}
	}
	
	/**
	 * 确定关闭
	 * @param point
	 */
	public void sureClose(MyPoint point){
		if(touchMode == ThouchModel.close){
			float distance = point.getDistance(lastThouch);
			if(distance < this.closeButtonR){
				listener.onViewClose();
			}
		}
	}
	
	/**
	 * 旋转和缩放
	 * @param point
	 */
	public void RotationAndScaling(MyPoint point){
		if(point == null){
			return ;
		}
		if(point.x == this.contentCore.x){
			if(point.y >= this.contentCore.y){
				this.rotationAngle = Math.PI/2;
			}else{
				this.rotationAngle = Math.PI/2 + Math.PI;
			}
		}else{
			float k = (point.y - this.contentCore.y)/(point.x - this.contentCore.x);
			double atan = Math.atan(k);
			double atan2 = Math.atan(this.height / (float)this.width);
			this.rotationAngle = atan - atan2;
			if(point.x < this.contentCore.x){
				this.rotationAngle += Math.PI;
			}
		}
		float adjDistance = this.adjustButton.getDistance(this.contentCore);
		float pointDistance = point.getDistance(this.contentCore);
		float scalling = (pointDistance/adjDistance);
		this.contentCore_V = CalculationOfRatation(this.contentCore);
		int newSize = (int)(this.size * scalling);
		
		setSize(newSize);
	}
	
	/**
	 * 移动内容
	 * @param point
	 */
	public void MoveContent(MyPoint point){
		if(point == null || lastThouch == null || touchMode != ThouchModel.move){
			return;
		}
//		this.position.VectorMove(lastThouch, point);
//		this.closeButton.VectorMove(lastThouch, point);
//		this.adjustButton.VectorMove(lastThouch, point);
		this.contentCore.VectorMove(lastThouch, point);
		this.contentCore_V = CalculationOfRatation(this.contentCore);
		this.setSize(this.size);
		lastThouch = point;
	}
	
	/**
	 * 判断点击的类型
	 * @param point
	 */
	public void getThouchMode(MyPoint point){
		if(point == null){
			this.touchMode = ThouchModel.none;
		}
//		MyPoint topCenter = new MyPoint(this.contentCore.x, this.contentCore.y - this.height/2, 100000, 100000, -100000, -100000);
////		Log.d("button", "close " + close.x + " " + close.y + " " + Math.cos(this.rotationAngle) * this.width/2 + " " +Math.sin(this.rotationAngle) * this.width/2);
		int len = (int) Math.sqrt(Math.pow(this.width/2, 2) + Math.pow(this.height/2,2));
		double angle = Math.asin(this.height/2.0/len) + this.rotationAngle;
		MyPoint close = new MyPoint((float)(this.contentCore.x - Math.cos(angle) * len), (float)(this.contentCore.y - Math.sin(angle) * len), 100000, 1000000, -100000, -100000);
		
		float distance = point.getDistance(close);
//		Log.d("button", "close distance = " + distance);
		if(distance < this.closeButtonR){
			this.touchMode = ThouchModel.close;
			return;
		}
//		MyPoint buttonCenter = new MyPoint(this.contentCore.x, this.contentCore.y + this.height/2, 10000, 10000, -100000, -10000);
		MyPoint adjust = new MyPoint((float)(this.contentCore.x + Math.cos(angle) * len), (float)(this.contentCore.y + Math.sin(angle) * len), 100000, 1000000, -100000, -100000);
//		Log.d("button", "adjust " + adjust.x + " " + adjust.y);
		float distance2 = point.getDistance(adjust);
//		Log.d("button", "adjust distance = " + distance2);
		if(distance2 < this.adjustButtonR){
			this.touchMode = ThouchModel.rotationAndSize;
			return;
		}
		//判断是否在方框内
		
		float distance3 = point.getDistance(this.contentCore);
		if(distance3 > this.width/2){
			this.touchMode = ThouchModel.none;
			return;
		}
		if(this.rotationAngle%(Math.PI/2) == 0 && this.rotationAngle % Math.PI != 0){
			float abs = Math.abs(point.getDistance(this.contentCore));
			if(abs <= this.width/2){
				this.touchMode = ThouchModel.move;
				return;
			}
		}
		double k = Math.tan(this.rotationAngle);//斜率
		int intercept = 0;//截距
		if(Math.sin(this.rotationAngle) == 0){
			intercept = this.height/2;
		}
		intercept =  Math.abs((int) (this.height/2 / Math.cos(this.rotationAngle)));
		if(point.y >= k*(point.x - this.contentCore.x) + this.contentCore.y){
			if(point.y <= k*(point.x - this.contentCore.x) + this.contentCore.y + intercept){
				this.touchMode = ThouchModel.move;
			}else{
				this.touchMode = ThouchModel.none;
			}
			return;
		}
		if(point.y <= k*(point.x - this.contentCore.x) + this.contentCore.y){
			if(point.y >= k*(point.x - this.contentCore.x) + this.contentCore.y - intercept){
				this.touchMode = ThouchModel.move;
			}else{
				this.touchMode = ThouchModel.none;
			}
			return;
		}
		
		this.touchMode = ThouchModel.none;
	}
	
	
	
	enum ThouchModel{
		close,move,rotationAndSize,none
	}
}
