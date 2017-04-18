package com.example.screen.control;

import android.content.Context;
import android.widget.FrameLayout;

import com.example.screen.customView.Edit;
import com.example.screen.customView.EditArrowView;
import com.example.screen.customView.EditCircleView;
import com.example.screen.customView.EditColorSelectButton;
import com.example.screen.customView.EditLineView;
import com.example.screen.customView.EditSquareView;
import com.example.screen.customView.EditView;
import com.example.screen.listener.OnAddListener;
import com.example.screen.listener.OnCompleteAnEdit;

import java.util.Stack;

public class EditGraphicalControler implements OnCompleteAnEdit {

	public enum Graphical{
		none,square,square_fill,circle,circle_fill,arrow,line
	}
	private Graphical graphical = Graphical.none;
	private Stack stack;
	private FrameLayout edit_layout;//编辑区
	private Context context;
	private EditColorSelectButton edit_custon_color;//当前颜色
	private EditView view;
	private OnAddListener listener;
	
	public EditGraphicalControler(Stack stack, FrameLayout edit_layout, EditColorSelectButton edit_custon_color, Context context, OnAddListener listener){
		this.stack = stack;
		this.edit_layout = edit_layout;
		this.context = context;
		this.edit_custon_color = edit_custon_color;
		this.listener = listener;
	}
	
	@Override
	public void OnCompleteEdit() {
		listener.onAdding();
		if(view != null){
			view.fixed();
			stack.push(view);
			view = null;
		}
		CrateGraphical();
	}
	
	/**
	 * 设置图形类型
	 * @param graphical
	 */
	public void setGraphical(Graphical graphical) {
		this.graphical = graphical;
	}
	
	public Graphical getGraphical() {
		return graphical;
	}
	
	
	/**
	 * 创建图形
	 */
	public void CrateGraphical(){
		if(stack == null){
			return;
		}
		if(view != null){
			edit_layout.removeView(view);
		}
		if(this.graphical == Graphical.square){
			view = addSquare();
		}
		else if(this.graphical == Graphical.square_fill){
			view = addSquareFill();
		}else if(this.graphical == Graphical.circle){
			view = addCircle();
		}else if(this.graphical == Graphical.circle_fill){
			view = addCircleFill();
		}else if(this.graphical == Graphical.arrow){
			view = addArrow();
		}else if(this.graphical == Graphical.line){
			view = addLine();
		}
		
		
	}
	
	public void ChangeColor(){
		if(view != null){
			Edit edit = view.getEdit();
			edit.setColor(edit_custon_color.getColor());
		}
	}
	
	/**
	 * 添加直线
	 * @return
	 */
	private EditView addLine(){
		if(edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditLineView edit = new EditLineView();
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}
	
	/**
	 * 添加空心矩形
	 */
	private EditView addSquare(){
		if(stack == null || edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditSquareView edit = new  EditSquareView(false);
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}
	
	/**
	 * 添加实心矩形
	 */
	private EditView addSquareFill(){
		if(stack == null || edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditSquareView edit = new  EditSquareView(true);
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}
	
	/**
	 * 添加空心圆形
	 * @return
	 */
	private EditView addCircle(){
		if(edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditCircleView edit = new EditCircleView(false);
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}
	
	/**
	 * 添加实心圆形
	 * @return
	 */
	private EditView addCircleFill(){
		if(edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditCircleView edit = new EditCircleView(true);
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}
	/**
	 * 添加空心圆形
	 * @return
	 */
	private EditView addArrow(){
		if(edit_layout == null || context == null || edit_custon_color == null){
			return null;
		}
		EditView view = new EditView(context);
		EditArrowView edit = new EditArrowView();
		edit.setColor(edit_custon_color.getColor());
		view.setEdit(edit);
		view.setOnCompleteAnEditListener(this);
		edit_layout.addView(view);
		return view;
	}

//	@Override
//	public void onGraphicalSelect(Graphical graphical) {
//		// TODO Auto-generated method stub
//		if(!stack.isEmpty()){
//			EditView peek = stack.peek();
//			if(!peek.isFixed()){
//				peek.fixed();
//			}
//		}
//		this.graphical = graphical;
//		this.CrateGraphical();
//	}
	
	
}
