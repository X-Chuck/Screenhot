package com.example.screen.frament;

import java.util.List;

import com.example.screen.adapter.PicturePathAdapter;
import com.example.screen.data.PicturePathFileControler;
import com.example.screen.data.PicturePathFiler;
import com.example.screen.tool.MyConfig;
import com.xsj_Screen.screen.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class PicturePathFrament extends Fragment implements OnItemClickListener{

	private RelativeLayout rootView;
	private ListView filersListView;
	private PicturePathFileControler contruler;
	PicturePathAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = (RelativeLayout) inflater.inflate(R.layout.screenhot_picture_path_frament, null);
		return rootView;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		init();
	}
	
	private void init(){
		filersListView = (ListView) rootView.findViewById(R.id.filersListView);
		contruler = new PicturePathFileControler();
		List<PicturePathFiler> currentCatalog = contruler.getCurrentCatalog();
		adapter = new PicturePathAdapter();
		adapter.setData(currentCatalog);
		filersListView.setAdapter(adapter);
		filersListView.setOnItemClickListener(this);
	}
	
	/**
	 * 保存用户选择
	 */
	public void saveChoice(){
		MyConfig config = MyConfig.GetInstance();
		config.SavePicturePath = contruler.getCurrentNode().getFile().getPath();
		config.SaveString(getActivity(), config.SavePicturePath, config.PIC_PATH);
	}
	
	/**
	 * 返回上一层目录，返回false则是最上一层目录
	 * @return
	 */
	public boolean Backoff(){
		int layer = contruler.getLayer();
		if(layer == 0){
			return false;
		}
		contruler.previous(null);
		adapter.setData(contruler.getCurrentCatalog());
		filersListView.setAdapter(adapter);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		PicturePathFiler picPathFiler = (PicturePathFiler)adapter.getItem(position);
		contruler.next(picPathFiler);
		adapter.setData(contruler.getCurrentCatalog());
		filersListView.setAdapter(adapter);
		
	}
	
}
