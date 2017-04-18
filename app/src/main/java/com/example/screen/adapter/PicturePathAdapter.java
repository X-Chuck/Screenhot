package com.example.screen.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.screen.data.PicturePathFiler;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PicturePathAdapter extends BaseAdapter{

	private List<PicturePathFiler> data =  new ArrayList<PicturePathFiler>();
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(position >= this.getCount())
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(position >= this.getCount())
			return -1;
		return data.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_picture_path_item, null);
		}
		TextView fileName = (TextView) convertView.findViewById(R.id.fileName);
		fileName.setText(((PicturePathFiler)getItem(position)).getName());
		return convertView;
	}
	
	
	public void setData(List<PicturePathFiler> data){
		if(data == null){
			return;
		}
		this.data = data;
	}

}
