package com.example.screen.adapter;

import com.example.screen.listener.OnStrokeChangeListener;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class EditStrokeAdapter extends BaseAdapter implements OnSeekBarChangeListener{

	private RelativeLayout root;
	private OnStrokeChangeListener listener;
	private int num_stroke;
	private int num_alpha;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(root == null){
			root = (RelativeLayout) ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_edit_add_stroke_item, null);
			SeekBar stroke = (SeekBar) root.findViewById(R.id.edit_add_stroke_stroke);
			SeekBar alpha = (SeekBar) root.findViewById(R.id.edit_add_stroke_alpha);
			stroke.setOnSeekBarChangeListener(this);
			alpha.setOnSeekBarChangeListener(this);
			num_stroke = stroke.getProgress();
			num_alpha = alpha.getProgress();
		}
		
		return root;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		int id = seekBar.getId();
		if (id == R.id.edit_add_stroke_alpha)
			num_alpha = progress;
		else if (id == R.id.edit_add_stroke_stroke)
			num_stroke = progress;
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if(listener == null){
			return;
		}
		int id = seekBar.getId();
		int progress = seekBar.getProgress();
		if (id == R.id.edit_add_stroke_stroke){
			listener.OnStrokeChange(progress);
			num_stroke = progress;
		}
		else if (id == R.id.edit_add_stroke_alpha){
			listener.OnAlphaChange(progress);
			num_alpha = progress;
		}
	}
	
	public void SetOnStrokeChangeListener(OnStrokeChangeListener listener){
		this.listener = listener;
	}
	
	public int getStroke(){
		return num_stroke;
	}
	public int getAlpha(){
		return num_alpha;
	}

}
