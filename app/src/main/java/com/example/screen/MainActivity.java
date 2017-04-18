package com.example.screen;



import java.io.File;

import com.example.screen.service.ScreenService;
import com.example.screen.tool.MyConfig;
import com.example.screen.tool.ScreenTool;
import com.xsj_Screen.screen.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

	private int result = 0;
	private Intent intent = null;
	private int REQUEST_MEDIA_PROJECTION = 1;
	private MediaProjectionManager mMediaProjectionManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.screenhot_activity_main);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.screenhot_main_title);
		MyConfig config = MyConfig.GetInstance();
		config.LoadSetting(this);
		String apkRoot="chmod 777 "+getPackageCodePath(); 
		config.PicturePath =  getFilesDir()+"/Picture";
		if(config.SavePicturePath.equals("")){
			config.SavePicturePath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/";
		}
		createDir(config.SavePicturePath);
		config.RootComment = apkRoot;
		config.IsRoot = ScreenTool.isRoot();
		System.out.println("是否root = " +config.IsRoot);
		config.SDK_INT = Build.VERSION.SDK_INT;
		if(config.SDK_INT >= 21){
			mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		}
		Intent intent = new Intent(this, ScreenService.class);
//		intent.setAction("com.xsj.screenService");
//		final Intent eintent = new Intent(PhoneMesgagerTool.createExplicitFromImplicitIntent(this,intent));
		stopService(intent);
		
	}
	
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName); 
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }
    }
	
	@SuppressLint("NewApi")
	private void startIntent(){
		if(intent != null && result != 0){
			Log.i("action","user agree the application to capture screen");
			ShotApplication.getInstance().setResult(result);
			ShotApplication.getInstance().setIntent(intent);
			Intent intent = new Intent(getApplicationContext(), ScreenService.class);
			startService(intent);
			finish();
			Log.i("action", "start service");
		}
		else{
			//这里请求录制屏幕
			startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
			ShotApplication.getInstance().setmMediaProjectionManager(mMediaProjectionManager);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyConfig config = MyConfig.GetInstance();
		if(config.SDK_INT >= 21){
			startIntent();
		}else if(config.IsRoot){
			Intent intent = new Intent(getApplicationContext(), ScreenService.class);
			startService(intent);
			
			finish();
		}
		else{
			DiagLogBox("您的手机尚未root！");
		}
		
	}
	
	private void DiagLogBox(String str){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(str);
		builder.setTitle("提示")
		.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		}).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == REQUEST_MEDIA_PROJECTION){
			if(resultCode != Activity.RESULT_OK){
				Toast.makeText(this, "需获取录屏授权才能使用该插件！", Toast.LENGTH_SHORT).show();
				finish();
			}else if(data != null && resultCode != 0){
				Log.i("action","user agree the application to capture screen");
				//这里把请求保存到application中
				result = resultCode;
				intent = data;
				(ShotApplication.getInstance()).setResult(resultCode);
				(ShotApplication.getInstance()).setIntent(data);
				Intent intent = new Intent(getApplicationContext(), ScreenService.class);
				startService(intent);
				Log.i("action", "start service Service");
				finish();
			}
		}
	}

}
