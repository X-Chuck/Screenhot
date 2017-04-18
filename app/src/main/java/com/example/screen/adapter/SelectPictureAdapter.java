package com.example.screen.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.screen.data.Picture;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SelectPictureAdapter extends BaseAdapter{

	private List<Picture> data = new ArrayList<Picture>();
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
			convertView = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_select_pic_frament_item, null);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		img.setImageBitmap(data.get(position).getBitmap());
		return convertView;
	}
	
	public void setData(List<Picture> data){
		if(data == null){
			return;
		}
		this.data = data;
	}

}
