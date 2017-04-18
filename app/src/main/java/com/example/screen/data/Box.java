package com.example.screen.data;

import android.view.MotionEvent;

public class Box {
	
	enum Side{
		none,
		left,
		right,
		top,
		buttom,
		all,
		leftAndtop,
		leftAndbuttom,
		rightAndtop,
		rightAndbuttom
	}
	public static final float MINWIDTH = 60;
	public static final float MINHEIGHT = 60;
	public static final float JUDGINGDISTANCE = 30;
	private MyPoint topLeftPoint;
	private MyPoint topRightPoint;
	private MyPoint buttomLeftPoint;
	private MyPoint buttomRightPoint;
	private Side canMoveSide = Side.none;
	private float MaxWidth;
	private float MaxHeith;
	private MyPoint LastPoint;
	
	public Box(){
		
	}
	
	public Box(MyPoint topLeft, MyPoint topRight, MyPoint buttomLeft, MyPoint buttomRigh){
		this.topLeftPoint = topLeft;
		this.topRightPoint = topRight;
		this.buttomLeftPoint = buttomLeft;
		this.buttomRightPoint = buttomRigh;
	}
	
	public Box(MyPoint topLeft,float width, float height,float MaxWidth, float MaxHeight){
		if(topLeft == null){
			return;
		}
		this.MaxHeith = MaxHeight;
		this.MaxWidth = MaxWidth;
		this.topLeftPoint = topLeft;
		MyPoint topRightPoint = new MyPoint(this.topLeftPoint.x + width, this.topLeftPoint.y,this.MaxWidth,this.MaxHeith);
		this.topRightPoint = topRightPoint;
		MyPoint buttomLeftPoint = new MyPoint(this.topLeftPoint.x , this.topLeftPoint.y + height,this.MaxWidth,this.MaxHeith);
		this.buttomLeftPoint = buttomLeftPoint;
		MyPoint buttomRightPoint = new MyPoint(this.topLeftPoint.x + width, this.topLeftPoint.y + height,this.MaxWidth,this.MaxHeith);
		this.buttomRightPoint = buttomRightPoint;
	}
	
	public void OnTouch(MyPoint touchPoint ,int action){
		if(touchPoint == null){
			return;
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			judgmentCanMoveSide(touchPoint);
			break;
		case MotionEvent.ACTION_MOVE:
			onMoveTouch(touchPoint);
			break;
		case MotionEvent.ACTION_UP:
			ResetMoveSide();
			break;

		default:
			break;
		}
		
	}
	
	private void onMoveTouch(MyPoint touchPoint){
		if(canMoveSide == Side.all){
			MoveAllBox(touchPoint);
		}else if(canMoveSide == Side.none){
			
		}
		else{
			ModifyTheSize(touchPoint);
		}
	}
	
	
	private void ModifyTheSize(MyPoint touchPoint){
		if(canMoveSide == Side.top || canMoveSide == Side.leftAndtop || canMoveSide == Side.rightAndtop){
			VerticalMove(touchPoint,Side.top);
		}
		if(canMoveSide == Side.buttom || canMoveSide == Side.leftAndbuttom || canMoveSide == Side.rightAndbuttom){
			VerticalMove(touchPoint, Side.buttom);
		}
		if(canMoveSide == Side.left || canMoveSide == Side.leftAndtop || canMoveSide == Side.leftAndbuttom){
			LevelMove(touchPoint, Side.left);
		}
		if(canMoveSide == Side.right || canMoveSide == Side.rightAndtop || canMoveSide == Side.rightAndbuttom){
			LevelMove(touchPoint, Side.right);
		}
		LastPoint = touchPoint;
	}
	
	/**
	 * 垂直移动某些边
	 * @param touchPoint
	 * @param model
	 */
	private void VerticalMove(MyPoint touchPoint, Side model){
		if(model == Side.top){
			this.topLeftPoint.VectorMoveY(LastPoint, touchPoint);
			this.topRightPoint.VectorMoveY(LastPoint, touchPoint);
			float boxHeight = getBoxHeight();
			if(boxHeight < MINHEIGHT){
				topLeftPoint.y  = buttomLeftPoint.y - MINHEIGHT;
				topRightPoint.y = buttomRightPoint.y - MINHEIGHT;
			}
		}
		if(model == Side.buttom){
			this.buttomLeftPoint.VectorMoveY(LastPoint, touchPoint);
			this.buttomRightPoint.VectorMoveY(LastPoint, touchPoint);
			float boxHeight = getBoxHeight();
			if(boxHeight < MINHEIGHT){
				buttomLeftPoint.y = topLeftPoint.y + MINHEIGHT;
				buttomRightPoint.y = topRightPoint.y + MINHEIGHT;
			}
		}
	}
	
	/**
	 * 水平移动某些边
	 * @param touPoint
	 * @param model
	 */
	private void LevelMove(MyPoint touPoint, Side model){
		if(model == Side.left){
			this.topLeftPoint.VectorMoveX(LastPoint, touPoint);
			this.buttomLeftPoint.VectorMoveX(LastPoint, touPoint);
			float boxWidth = getBoxWidth();
			if(boxWidth < MINWIDTH){
				this.topLeftPoint.x = this.topRightPoint.x - MINWIDTH;
				this.buttomLeftPoint.x = this.buttomRightPoint.x - MINWIDTH;
			}
		}
		if(model == Side.right){
			this.topRightPoint.VectorMoveX(LastPoint, touPoint);
			this.buttomRightPoint.VectorMoveX(LastPoint, touPoint);
			float boxWidth = getBoxWidth();
			if(boxWidth < MINWIDTH){
				this.topRightPoint.x = this.topLeftPoint.x + MINWIDTH;
				this.buttomRightPoint.x = this.buttomLeftPoint.x + MINWIDTH;
			}
		}
	}
	
	/**
	 * 移动某些边
	 * @param touchPoint
	 * @param points
	 */
	private void MoveSomeSide(MyPoint touchPoint , MyPoint[] points){
		if(touchPoint == null){
			return;
		}
		for(MyPoint point : points){
			point.VectorMove(LastPoint, touchPoint);
		}
		LastPoint = touchPoint;
	}
	
	
	/**
	 * 移动整个方框
	 * @param touchPoint
	 */
	private void MoveAllBox(MyPoint touchPoint){
		if(touchPoint == null){
			return;
		}
		if(this.canMoveSide == Side.all){
			float boxHeight = getBoxHeight();
			float boxWidth = getBoxWidth();
			MyPoint[] points = {this.topLeftPoint,
								this.topRightPoint,
								this.buttomLeftPoint,
								this.buttomRightPoint};
			MoveSomeSide(touchPoint, points);
			if(boxHeight != getBoxHeight()){
				if(this.topLeftPoint.y == 0){
					this.buttomLeftPoint.y = boxHeight;
					this.buttomRightPoint.y = boxHeight;
				}
				if(this.buttomLeftPoint.y == this.MaxHeith){
					this.topLeftPoint.y = this.MaxHeith - boxHeight;
					this.topRightPoint.y = this.MaxHeith - boxHeight;
				}
			}
			if(boxWidth != getBoxWidth()){
				if(this.topLeftPoint.x == 0){
					this.topRightPoint.x = boxWidth;
					this.buttomRightPoint.x = boxWidth;
				}
				if(this.topRightPoint.x == this.MaxWidth){
					this.topLeftPoint.x = this.MaxWidth - boxWidth;
					this.buttomLeftPoint.x = this.MaxWidth - boxWidth;
				}
				
			}
			this.topLeftPoint.BackUp(topLeftPoint.x, topLeftPoint.y);
			this.topRightPoint.BackUp(topRightPoint.x, topRightPoint.y);
			this.buttomLeftPoint.BackUp(buttomLeftPoint.x, buttomLeftPoint.y);
			this.buttomRightPoint.BackUp(buttomRightPoint.x, buttomRightPoint.y);
		}
	}
	
	
	private void ResetMoveSide(){
		this.canMoveSide = Side.none;
	}
	
	/**
	 * 判读哪一条边可以移动
	 * @param touchPoint
	 */
	private void judgmentCanMoveSide(MyPoint touchPoint){
		float disLeft = this.topLeftPoint.getXDistance(touchPoint);
		float disRight = this.topRightPoint.getXDistance(touchPoint);
		float disTop = this.topLeftPoint.getYDistance(touchPoint);
		float disbuttom = this.buttomLeftPoint.getYDistance(touchPoint);
		if(disLeft <= JUDGINGDISTANCE && disTop <= JUDGINGDISTANCE){
			this.canMoveSide = Side.leftAndtop;
		}
		else if(disLeft <= JUDGINGDISTANCE && disbuttom <= JUDGINGDISTANCE){
			this.canMoveSide = Side.leftAndbuttom;
		}
		else if(disRight <= JUDGINGDISTANCE && disTop <= JUDGINGDISTANCE){
			this.canMoveSide = Side.rightAndtop;
		}
		else if(disRight <= JUDGINGDISTANCE && disbuttom <= JUDGINGDISTANCE){
			this.canMoveSide = Side.rightAndbuttom;
		}
		else if(disLeft <= JUDGINGDISTANCE){
			this.canMoveSide = Side.left;
		}
		else if(disRight <= JUDGINGDISTANCE){
			this.canMoveSide = Side.right;
		}
		else if(disTop <= JUDGINGDISTANCE){
			this.canMoveSide = Side.top;
		}
		else if(disbuttom <= JUDGINGDISTANCE){
			this.canMoveSide = Side.buttom;
		}
		else if(touchPoint.x > this.topLeftPoint.x && touchPoint.y > this.topLeftPoint.y && touchPoint.x < this.buttomRightPoint.x && touchPoint.y < this.buttomRightPoint.y){
			this.canMoveSide = Side.all;
		}
		else{
			this.canMoveSide = Side.none;
			this.LastPoint = null;
		}
		this.LastPoint = touchPoint;
	}
	
	/**
	 * 获取方框宽度
	 * @return
	 */
	public float getBoxWidth(){
		if(topLeftPoint == null || topRightPoint == null){
			return 0;
		}
		float xDistance = topLeftPoint.getXDistance(topRightPoint);
		return xDistance;
	}
	
	/**
	 * 获取方框高度
	 * @return
	 */
	public float getBoxHeight(){
		if(topLeftPoint == null || buttomLeftPoint == null){
			return 0;
		}
		float yDistance = topLeftPoint.getYDistance(buttomLeftPoint);
		return yDistance;
	}

	public MyPoint getTopLeftPoint() {
		return topLeftPoint;
	}

	public void setTopLeftPoint(MyPoint topLeftPoint) {
		this.topLeftPoint = topLeftPoint;
	}

	public MyPoint getTopRightPoint() {
		return topRightPoint;
	}

	public void setTopRightPoint(MyPoint topRightPoint) {
		this.topRightPoint = topRightPoint;
	}

	public MyPoint getButtomLeftPoint() {
		return buttomLeftPoint;
	}

	public void setButtomLeftPoint(MyPoint buttomLeftPoint) {
		this.buttomLeftPoint = buttomLeftPoint;
	}

	public MyPoint getButtomRightPoint() {
		return buttomRightPoint;
	}

	public void setButtomRightPoint(MyPoint buttomRightPoint) {
		this.buttomRightPoint = buttomRightPoint;
	}
	
	

}
