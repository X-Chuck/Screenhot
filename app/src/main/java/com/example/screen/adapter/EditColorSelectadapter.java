package com.example.screen.adapter;

import com.example.screen.customView.EditColorSelectButton;
import com.example.screen.listener.ColorSelectListener;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class EditColorSelectadapter extends BaseAdapter implements OnClickListener{
	private ColorSelectListener listener;

	private int[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.BLACK,Color.YELLOW,Color.GRAY,Color.WHITE,Color.DKGRAY,Color.CYAN};
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return colors.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return new Integer(colors[position]);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_edit_select_color_item, null);
		}
		EditColorSelectButton button = (EditColorSelectButton) convertView.findViewById(R.id.edit_color_select_button);
		button.setColor(colors[position]);
		button.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditColorSelectButton button = (EditColorSelectButton) v;
		if(listener != null){
			listener.onColorSelect(button.getColor());
		}
	}
	public void setOnColorSelectListener(ColorSelectListener listener){
		this.listener = listener;
	}

}
