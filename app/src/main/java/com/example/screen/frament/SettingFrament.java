package com.example.screen.frament;

import java.io.File;

import com.example.screen.SettingActivity;
import com.example.screen.SettingActivity.nowFragment;
import com.example.screen.service.MyFtpServer;
import com.example.screen.service.textserver;
import com.example.screen.tool.MyConfig;
import com.example.screen.tool.PhoneMesgagerTool;
import com.xsj_Screen.screen.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingFrament extends Fragment implements OnClickListener , OnCheckedChangeListener{
	private View Rootview;
	
	
	
	//数据变量
//	private String serviceIP;
	private String PicPath;
	
//	private boolean deleteAfterUpload = false;
//	private boolean synchronzie = false;
	
	
	//ui变量
	LinearLayout delete_pic_layout;
	LinearLayout PicPath_layout;
	LinearLayout edit_pic;
//	TextView serviceIp_textView;
	TextView picturePath_textView;
	ToggleButton start_ftp_server;
	TextView ftp_server_textView;
//	ToggleButton synUpload;
//	CustomDialog dialog;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Rootview = inflater.inflate(R.layout.screenhot_setting_frament, null);
		return Rootview;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		init();
		SettingActivity activity = (SettingActivity) getActivity();
		activity.setNowFragment(nowFragment.setframent);
	}
	
	private void init(){
		MyConfig config = MyConfig.GetInstance();
		this.PicPath = config.SavePicturePath;
		delete_pic_layout = (LinearLayout) Rootview.findViewById(R.id.delete_pic_layout);
		PicPath_layout = (LinearLayout) Rootview.findViewById(R.id.picture_layout);
		picturePath_textView = (TextView) Rootview.findViewById(R.id.picturePath);
		start_ftp_server = (ToggleButton) Rootview.findViewById(R.id.start_ftp_server);
		ftp_server_textView = (TextView) Rootview.findViewById(R.id.serverip);
		edit_pic = (LinearLayout) Rootview.findViewById(R.id.edit_pic);
		
		if(config.IsFtpServerOpen)
			ftp_server_textView.setVisibility(View.VISIBLE);
		else
			ftp_server_textView.setVisibility(View.GONE);
		picturePath_textView.setText(config.SavePicturePath);
		delete_pic_layout.setOnClickListener(this);
		PicPath_layout.setOnClickListener(this);
		edit_pic.setOnClickListener(this);
		start_ftp_server.setOnCheckedChangeListener(this);
		start_ftp_server.setChecked(config.IsFtpServerOpen);	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.delete_pic_layout) {
//			AlertDialog();
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setTitle("提示");
			builder.setMessage("确认要删除图片吗？");
			builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					DeletePictures();
				}
			});
			builder.setNegativeButton("取消", null);
			builder.show();
		}
		else if (id ==  R.id.picture_layout)
			ToPicturePathFrament();
		else if (id == R.id.edit_pic)
			ToSelectPicFragment();

	}
	
	private void DeletePictures(){
		MyConfig config = MyConfig.GetInstance();
		File file = new File(config.SavePicturePath);
		if(file.exists() && file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				String name = f.getName();
				if(name.endsWith(".jpg")){
					f.delete();
				}
			}
		}
	}
	
	/**
	 * 跳转致选择图片fragment
	 */
	private void ToSelectPicFragment(){
		SettingActivity activity = (SettingActivity)getActivity();
		activity.ToSelectPicFragment();
	}

	
	/**
	 * 跳转致选择图片保存路径的frament
	 */
	private void ToPicturePathFrament(){
		SettingActivity activity = (SettingActivity)getActivity();
		activity.ToFilePathFrament();
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int id = buttonView.getId();
		MyConfig config = MyConfig.GetInstance();
		if (id == R.id.start_ftp_server) {
			//这里开启ftp服务器
			if (isChecked) {
				StartFtpServer();
			} else {
				stopFtpServer();
			}
		}
	}
	
	@SuppressLint("NewApi")
	private void StartFtpServer(){
		MyConfig config = MyConfig.GetInstance();
		if(config.IsFtpServerOpen){
			return;
		}
		
		Intent intent = new Intent(getContext(), textserver.class);
//		intent.setAction("com.xsj.textserver");
//		final Intent eintent = new Intent(PhoneMesgagerTool.createExplicitFromImplicitIntent(getActivity(),intent));
//		
		MyFtpServer server = MyFtpServer.GetInstance();
		boolean start = server.start(getActivity());
		if(start){
			ftp_server_textView.setVisibility(View.VISIBLE);
			ftp_server_textView.setText("ftp://"+ PhoneMesgagerTool.getWIFILocalIpAdress(getActivity())+":2222");
		}
		getActivity().startService(intent);
		
		new AlertDialog.Builder(getActivity()).setTitle("使用提示")
	     .setMessage("在地址栏中输入：\nftp://"+ PhoneMesgagerTool.getWIFILocalIpAdress(getActivity())+":2222\n若需要上传，则需要登录：\n帐号：kingsoft,密码：kingsoft")
	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {
	         @Override  
	         public void onClick(DialogInterface dialog, int which) { 
	         }  
	  
	     }).show();
		
	}
	
	private void stopFtpServer(){
		MyFtpServer server = MyFtpServer.GetInstance();
		server.stop();
		ftp_server_textView.setVisibility(View.GONE);
		
	}
	
	
}
