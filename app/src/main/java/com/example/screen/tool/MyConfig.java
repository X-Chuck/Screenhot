package com.example.screen.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyConfig {

	private final String SHARE_NANE = "screen_share";
	public final String PIC_PATH = "screen_picture_path";
	public final String SERVICE_IP = "screen_service_ip";
	public final String AFTER_UPLOAD = "screen_delete_pic_after_upload";
	public final String SYN_UPLOAD = "screen_ synchreonize_upload";
	private static MyConfig config;
	private MyConfig(){};
	public static MyConfig GetInstance(){
		if(config == null){
			config = new MyConfig();
		}
		return config;
	}
	
	public int SDK_INT = 0;
	public boolean IsRoot = false;
	public String RootComment = "";
	public String PicturePath = "";
	public String SavePicturePath = "";
	public boolean IsFtpServerOpen = false;
	
	public void SaveString(Context mContext,String str,String name){
		SharedPreferences share = mContext.getSharedPreferences(SHARE_NANE, Context.MODE_PRIVATE);
		Editor edit = share.edit();
		edit.putString(name, str);
		edit.commit();
	}
	
	public void SaveBoolean(Context mContext,boolean b,String name){
		SharedPreferences share = mContext.getSharedPreferences(SHARE_NANE, Context.MODE_PRIVATE);
		Editor edit = share.edit();
		edit.putBoolean(name, b);
		edit.commit();
	}
	
	public String LoadString(Context mContext,String key){
		SharedPreferences share = mContext.getSharedPreferences(SHARE_NANE, Context.MODE_PRIVATE);
		String string = share.getString(key, "");
		return string;
	}
	
	public boolean LoadBoolean(Context mContext , String key){
		SharedPreferences share = mContext.getSharedPreferences(SHARE_NANE, Context.MODE_PRIVATE);
		return share.getBoolean(key, false);
	}
	
	public void SaveSetting(Context mContext){
		SaveString(mContext, SavePicturePath, PIC_PATH);
	}
	
	public void LoadSetting(Context mContext){
		SavePicturePath = LoadString(mContext, PIC_PATH);
	}
}
