package com.example.screen.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class HorizontListView extends LinearLayout{

	private BaseAdapter adapter;
	public HorizontListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public HorizontListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setAdapter(BaseAdapter adapter){
		this.adapter = adapter;
		show();
	}
	
	private void show(){
		if(this.adapter == null){
			return ;
		}
		this.removeAllViews();
		for(int index = 0; index < adapter.getCount(); index ++){
			this.addView(adapter.getView(index, null, this));
		}
	}

}
