package com.example.screen.adapter;

import com.example.screen.control.EditGraphicalControler.Graphical;
import com.example.screen.customView.EditGraphicalSelectButton;
import com.example.screen.listener.OnGraphicalSelectListener;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

public class EditGraphicalSelectAdapter extends BaseAdapter implements OnClickListener{

	private Graphical[] graphical = {Graphical.square,Graphical.square_fill,Graphical.circle,Graphical.circle_fill,Graphical.arrow,Graphical.line};
	private OnGraphicalSelectListener listener;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return graphical.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return graphical[position];
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
			convertView = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_edit_add_graphical_item, null);
		}
		EditGraphicalSelectButton button = (EditGraphicalSelectButton) convertView.findViewById(R.id.edit_graphical_select_button);
		button.setGraphical(graphical[position]);
		button.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditGraphicalSelectButton button  = (EditGraphicalSelectButton) v;
		Graphical gra = button.getGraphical();
		if(listener != null){
			listener.onGraphicalSelect(gra);
		}
	}
	public void setOnGraphicalSelectListener(OnGraphicalSelectListener listener){
		this.listener = listener;
	}

}
