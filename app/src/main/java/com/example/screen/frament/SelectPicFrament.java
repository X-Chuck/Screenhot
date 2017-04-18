package com.example.screen.frament;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.screen.SettingActivity;
import com.example.screen.adapter.SelectPictureAdapter;
import com.example.screen.customView.CustomPropressBar;
import com.example.screen.data.Picture;
import com.example.screen.tool.EditConfig;
import com.example.screen.tool.MyConfig;
import com.xsj_Screen.screen.R;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class SelectPicFrament extends Fragment implements OnItemClickListener{

	private RelativeLayout root;
	private SelectPictureAdapter adapter;
	private GridView gridView;
	private int MSG = 0x5648;
	List<Picture> picList;
	private RelativeLayout pic_select_mask;
	private CustomPropressBar propressBar;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = (RelativeLayout) inflater.inflate(R.layout.screenhot_select_pic_frament, null);
		this.gridView = (GridView) root.findViewById(R.id.pic_select_gallery);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		pic_select_mask = (RelativeLayout) root.findViewById(R.id.pic_select_mask);
		propressBar = (CustomPropressBar) root.findViewById(R.id.propressBar);
		return root;
	}
	/**
	 * 界面能被看到是调用
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		picList = null;
		adapter = new SelectPictureAdapter();
		gridView.setAdapter(adapter);
		final Handler hander = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == MSG){
					adapter.setData(picList);
					gridView.setAdapter(adapter);
					propressBar.stop();
					pic_select_mask.setVisibility(View.GONE);
				}
			}
		};
		
		Thread thread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				picList = getPicList();
				Message msg = new Message();
				msg.what = MSG;
				hander.sendMessage(msg);
			}
		};
		thread.start();
		propressBar.start();
		
	}
	/**
	 * 界面能与用户交互是调用
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gridView.setOnItemClickListener(this);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		picList = null;
		adapter = new SelectPictureAdapter();
		gridView.setAdapter(adapter);
	}
	
	private List<Picture> getPicList(){
		List<Picture> result = new ArrayList<Picture>();
		MyConfig config = MyConfig.GetInstance();
		File dir = new File(config.SavePicturePath);
		File[] files = dir.listFiles();
		for(File f : files){
			Picture picture = new Picture(f.getAbsolutePath());
			if(picture.getName() != null){
				result.add(picture);
			}
		}
		return result;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d("Pic_select", "item 被点击了");
		Picture picture = (Picture) adapter.getItem(position);
		EditConfig config = EditConfig.GetInstance();
		config.Pic_path = picture.getPath();
		ToEditFragment();
	}
	
	private void ToEditFragment(){
		((SettingActivity)this.getActivity()).ToEdithPicFragment();
	}
}
