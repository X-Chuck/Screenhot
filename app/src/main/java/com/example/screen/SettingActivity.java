package com.example.screen;

import com.example.screen.frament.EditPictureFragment;
import com.example.screen.frament.PicturePathFrament;
import com.example.screen.frament.SelectPicFrament;
import com.example.screen.frament.SettingFrament;
import com.example.screen.service.MyFtpServer;
import com.example.screen.service.ScreenService;
import com.example.screen.tool.MyConfig;
import com.xsj_Screen.screen.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SettingActivity extends Activity{
	
	public enum nowFragment{
		setframent,picPathFrament,selectPicFrament,editPicFragment
	}
	// sd卡目录  
	 
	
	private SettingFrament settingFrament;
	private PicturePathFrament picturePathFrament;
	private SelectPicFrament selectPicFrament;
	private EditPictureFragment editPicFragment;
	private nowFragment nowfragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.screenhot_setting_activity);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.screenhot_main_title);
		init();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private void init(){
		
		Intent intent = new Intent(this, ScreenService.class);
//		intent.setAction("com.xsj.screenService");
//		final Intent eintent = new Intent(PhoneMesgagerTool.createExplicitFromImplicitIntent(this,intent));
		stopService(intent);
		
		settingFrament = new SettingFrament();
		changeFrament(settingFrament, nowFragment.setframent,false);
	}
	
	
	
	
	public void changeFrament(Fragment fragment,nowFragment fra,boolean addTock){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
		beginTransaction.replace(R.id.now_fragment, fragment);
		if(addTock)
			beginTransaction.addToBackStack(null);
		beginTransaction.commit();
		nowfragment = fra;
	}
	
	/**
	 * 跳转到编辑图片fragment
	 */
	public void ToEdithPicFragment(){
		if(editPicFragment == null){
			editPicFragment = new EditPictureFragment();
		}
		changeFrament(editPicFragment, nowFragment.editPicFragment, true);
	}
	
	/**
	 * 跳转致图片保存路径fragment
	 */
	public void ToFilePathFrament(){
		if(picturePathFrament == null){
			picturePathFrament = new PicturePathFrament();
		}
		changeFrament(picturePathFrament,nowFragment.picPathFrament,false);
	}
	
	/**
	 * 跳转致选择图片fragment
	 */
	public void ToSelectPicFragment(){
		if(selectPicFrament == null){
			selectPicFrament = new SelectPicFrament();
		}
		changeFrament(selectPicFrament, nowFragment.selectPicFrament,true);
	}
	
	/**
	 * 选择图片路径frament取消按钮
	 * @param v
	 */
	public void Cancel(View v){
		if(nowfragment == nowFragment.picPathFrament){
			changeFrament(settingFrament,nowFragment.setframent,false);
		}
	}
	
	/**
	 * 选择图片路径fragment确定按钮
	 * @param v
	 */
	public void Determine(View v){
		if(nowfragment == nowFragment.picPathFrament){
			picturePathFrament.saveChoice();
			changeFrament(settingFrament,nowFragment.setframent,false);
		}
	}
	
	/**
	 * 立刻上传
	 * @param v
	 */
	public void onUploadNow(View v){
		
	}
	
	/**
	 * 退出软件
	 * @param v
	 */
	public void onCloseSoftware(View v){
		MyConfig config = MyConfig.GetInstance();
		if(config.IsFtpServerOpen){
			MyFtpServer server = MyFtpServer.GetInstance();
			server.stop();
		}
		 
		finish();
	}
	
	public void setNowFragment(nowFragment nowfragment){
		this.nowfragment = nowfragment;
	}
	
	
	private boolean keyBack(){
		if(nowfragment == nowFragment.setframent){
			MyStartService(ScreenService.class);
			finish();
			return true;
		}
		if(nowfragment == nowFragment.picPathFrament){
			if(!picturePathFrament.Backoff()){
				changeFrament(settingFrament,nowFragment.setframent,false);
			}
			return true;
		}
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = fragmentManager.findFragmentById(R.id.now_fragment);
		if (fragment instanceof EditPictureFragment){
			if (((EditPictureFragment) fragment).isSaving())
				return true;
			else if (((EditPictureFragment) fragment).hadEdit()){
				((EditPictureFragment) fragment).BackOff();
				return true;
			}
		}
		return false;
	}
	
	public void MyStartService(Class<?> myClass){
		Intent intent = new Intent(getApplicationContext(), myClass);
		startService(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(keyBack())
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
