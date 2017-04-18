package com.example.screen.tool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.screen.ShotApplication;
import com.example.screen.customView.FullScreenshotsButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ScreenTool {

//	public static final String  path = "/storage/emulated/0/baidu/aa.png";
	private static final String DEVICE_NAME = "/dev/graphics/fb0"; 
	
	private SimpleDateFormat dataFormat = null;
	private String strDate = null;
//	private String pathImage = null;
	private String nameImage = null;
	private DisplayMetrics metrice = null;
	private ImageReader mImageReader = null;
	private int mScreenDensity = 0;
	private static MediaProjection mMediaProjection = null;
	private int winWidth = 0;
	private int winHeight = 0;
	public ScreenTool(Service service){
		if(Build.VERSION.SDK_INT >= 21){
			createVirtualEnvironment(service);
			startVirtual(service);
		}
	}
	
	public void close(){
		if(Build.VERSION.SDK_INT >= 21){
			tearDownMediaProjection();
		}
	}
	
	/**
	 * android 5.0 区域截屏
	 * @param service
	 * @param handler
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean Screenshot(final Service service,final Handler handler,int x,int y ,int width, int height){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(startCapture(service,x,y,width,height)){
			if(handler != null){
				Message msg  = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
		}else{
			if(handler != null){
				Message msg  = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
		}
		return false;
	}
	
	/**
	 * android 5.0 以上全屏截屏
	 * @param service
	 * @param handler
	 * @return
	 */
	public boolean Screenshot(final Service service,final Handler handler){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(startCapture(service)){
			if(handler != null){
				Message msg  = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
		}else{
			if(handler != null){
				Message msg  = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
		}
		return false;
	}
	
	@SuppressLint("NewApi")
	private void createVirtualEnvironment(Service service){
		
//		dataFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
//		strDate = dataFormat.format(new Date());
////		pathImage = Environment.getExternalStorageDirectory().getPath()+"/Pictures/";
		
//		MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)service.getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		WindowManager mWinManager = (WindowManager)service.getApplication().getSystemService(Context.WINDOW_SERVICE);
		winWidth = mWinManager.getDefaultDisplay().getWidth();
		winHeight = mWinManager.getDefaultDisplay().getHeight();
		metrice = new DisplayMetrics();
		mWinManager.getDefaultDisplay().getMetrics(metrice);
		mScreenDensity = metrice.densityDpi;
		mImageReader = ImageReader.newInstance(winWidth, winHeight, 0x1, 2);
		
	}
	
	private String CreatePicName(){
		dataFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		strDate = dataFormat.format(new Date());
		return strDate+".jpg";
	}
	
	private void startVirtual(Service service){
        if (mMediaProjection != null) {
            
            virtualDisplay(service);
        } else {
            
            setUpMediaProjection(service);
            virtualDisplay(service);
        }
    }
	
	private void setUpMediaProjection(Service service){
		Intent resultData = ShotApplication.getInstance().getIntent();
		int resultCode =ShotApplication.getInstance().getResult();
		MediaProjectionManager mediaProjectionManager = ShotApplication.getInstance().getmMediaProjectionManager();
		mMediaProjection = mediaProjectionManager.getMediaProjection(resultCode, resultData);
	}
	
	@SuppressLint("NewApi")
	private void virtualDisplay(Service service){
		if(winWidth == 0 || winHeight ==0){
			
			WindowManager mWinManager = (WindowManager)service.getApplication().getSystemService(Context.WINDOW_SERVICE);
			winWidth = mWinManager.getDefaultDisplay().getWidth();
			winHeight = mWinManager.getDefaultDisplay().getHeight();
		}
		mMediaProjection.createVirtualDisplay("screen-mirror", winWidth, winHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mImageReader.getSurface(), null, null);
//		mMediaProjection.createVirtualDisplay("screen-mirror", winWidth, winHeight, 1, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mImageReader.getSurface(), null, null);
	}
	
	
	
	
	/**
	 * android 5.0以上区域截屏
	 * @param service
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private boolean startCapture(Service service,int x,int y,int width, int height){
		MyConfig config = MyConfig.GetInstance();
		nameImage = config.SavePicturePath+ "/" + CreatePicName();
		Image image = mImageReader.acquireLatestImage();
		int width_img = image.getWidth();
		int height_img = image.getHeight();
		Plane[] planes = image.getPlanes();
		ByteBuffer buffer = planes[0].getBuffer();
		int pixelStride = planes[0].getPixelStride();
		int rowStride = planes[0].getRowStride();
		int rowPadding = rowStride - pixelStride * width_img;
		Bitmap bitmap = Bitmap.createBitmap(width_img + rowPadding/pixelStride, height_img, Bitmap.Config.ARGB_8888);
		bitmap.copyPixelsFromBuffer(buffer);
		bitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
		image.close();
		Log.i("captured", "image data captured");
		if(bitmap  != null){
			try{
				File fileImage = new File(nameImage);
				if(!fileImage.exists()){
					fileImage.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(fileImage);
				if(out != null){
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
					Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri contenUri = Uri.fromFile(fileImage);
					media.setData(contenUri);
					service.sendBroadcast(media);
				}
				return true;
			}catch(FileNotFoundException e){
				e.printStackTrace();
				Log.i("captured", "文件没有找到");
				return false;
			}catch(IOException e){
				e.printStackTrace();
				Log.i("captured","io出现问题");
				return false;
			}
		}
		return false;
	}
	
	/**
	 * android 5.0以上全屏截屏
	 * @param service
	 * @return
	 */
	private boolean startCapture(Service service){
		MyConfig config = MyConfig.GetInstance();
		nameImage = config.SavePicturePath+ "/" + CreatePicName();
		Image image = mImageReader.acquireLatestImage();
		int width = image.getWidth();
		int height = image.getHeight();
		Plane[] planes = image.getPlanes();
		ByteBuffer buffer = planes[0].getBuffer();
		int pixelStride = planes[0].getPixelStride();
		int rowStride = planes[0].getRowStride();
		int rowPadding = rowStride - pixelStride * width;
		Bitmap bitmap = Bitmap.createBitmap(width + rowPadding/pixelStride, height, Bitmap.Config.ARGB_8888);
		bitmap.copyPixelsFromBuffer(buffer);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		image.close();
		Log.i("captured", "image data captured");
		if(bitmap  != null){
			try{
				File fileImage = new File(nameImage);
				if(!fileImage.exists()){
					fileImage.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(fileImage);
				if(out != null){
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
					Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri contenUri = Uri.fromFile(fileImage);
					media.setData(contenUri);
					service.sendBroadcast(media);
				}
				return true;
			}catch(FileNotFoundException e){
				e.printStackTrace();
				Log.i("captured", "文件没有找到");
				return false;
			}catch(IOException e){
				e.printStackTrace();
				Log.i("captured","io出现问题");
				return false;
			}
		}
		return false;
	}
//	 private void stopVirtual() {
//	        if (mVirtualDisplay == null) {
//	            return;
//	        }
//	        mVirtualDisplay.release();
//	        mVirtualDisplay = null;
//	        Log.i(TAG,"virtual display stopped");
//	    }
	 
	 private void tearDownMediaProjection() {
	        if (mMediaProjection != null) {
	            mMediaProjection.stop();
	            mMediaProjection = null;
	        }
	    }
	
	
	public void a(Context context){
		WindowManager mWinManager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE); 
		Display defaultDisplay = mWinManager.getDefaultDisplay();
	}
	
	/**
	 * 全屏截屏
	 * @param service
	 * @param handler
	 */
	public  void Screencap(final Service service ,final Handler handler){
		Thread thread = new Thread(){
			public void run() {
				MyConfig config = MyConfig.GetInstance();
				if(config.SDK_INT >= 21){
					
					Screenshot(service,handler);
				}
				else if(config.IsRoot){
		//			Screencap();
					Bitmap bitmap = acquireScreenshot(service.getApplicationContext(),handler);
					savePic(bitmap, config.SavePicturePath+ "/" + CreatePicName());
				}
				else{
					Toast.makeText(service, "需要root权限才能截屏", 0).show();
					if(handler != null){
						Message msg = new Message();
						msg.what = FullScreenshotsButton.FINISHHANDLER;
						handler.sendMessage(msg);
					}
				}
			};
		};
		thread.start();
	}
	
	/**
	 * 区域截屏
	 * @param service
	 * @param handler
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public  void Screencap(final Service service ,final Handler handler ,final int x,final int y,final int width,final int height){
		Thread thread = new Thread(){
			public void run() {
				MyConfig config = MyConfig.GetInstance();
				if(config.SDK_INT >= 21){
					Screenshot(service,handler,x,y,width,height);
				}
				else if(config.IsRoot){
		//			Screencap();
					Bitmap bitmap = acquireScreenshot(service.getApplicationContext(),handler, x, y, width, height);
					savePic(bitmap, config.SavePicturePath+ "/" + CreatePicName());
				}
				else{
					Toast.makeText(service, "需要root权限才能截屏", 0).show();
					if(handler != null){
						Message msg = new Message();
						msg.what = FullScreenshotsButton.FINISHHANDLER;
						handler.sendMessage(msg);
					}
				}
			};
		};
		thread.start();
	}
	
	/**
	 * root情况下的截屏
	 */
	public  void Screencap(){
		MyConfig config = MyConfig.GetInstance();
//		dataFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
//		strDate = dataFormat.format(new Date());
		
		nameImage = config.PicturePath+"/" + CreatePicName();
		String[] commands ={config.RootComment,"screencap -p " + nameImage};
		
		boolean rootCommand = RootCommand(commands);
		if(rootCommand){
			copyFile(nameImage, config.SavePicturePath + strDate+".png");
		}
//    	Process process = null;
//    	try{
//    		process = Runtime.getRuntime().exec("su");
//    	    PrintStream outputStream = null;
//    	    try {
////    	    	File f = new File(nameImage);
////    	    	if(!f.exists()){
////    	    		f.createNewFile();
////    	    	}
//    	        outputStream = new PrintStream(new BufferedOutputStream(process.getOutputStream(), 8192));
//    	        outputStream.println("screencap -p " + nameImage  );
//    	        outputStream.flush();
//    	    }catch(Exception e){
//    	    } finally {
//    	        if (outputStream != null) {
//    	            outputStream.close();
//    	        }
//    	    }
//    	    process.waitFor();
//    	}catch(Exception e){
//    	}finally {
//    	    if(process != null){
//    	        process.destroy();
//    	    }
//    	}
    	}
	
	
	
	
	

/**
     * 判断当前手机是否有ROOT权限
     * @return
     */
    public static boolean isRoot(){
        boolean bool = false;

        try{
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())){
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {
        	
        } 
        return bool;
    }
    
    private String ToCommandPath(String path){
    	if(path == null){
    		return null;
    	}
    	String replaceFirst = path.replaceFirst("storage/emulated/0", "sdcard");
    	return replaceFirst;
    }
    
    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath(); RootCommand(apkRoot);
     * @return 应用程序是/否获取Root权限
     */
    public static boolean RootCommand(String[] command)
    {
        Process process = null;
        DataOutputStream os = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            for(int index = 0; index < command.length; index ++){
            	os.writeBytes(command[index] + "\n");
            	os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            InputStream errorStream = process.getErrorStream();
            byte[] buff = new byte[1000000];
            errorStream.read(buff);
            String s = new String(buff);
            System.out.println("sssss == " + s);
        } catch (Exception e)
        {
            Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
            return false;
        } finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
            }
        }
        Log.d("*** DEBUG ***", "Root SUC ");
        return true;
    }

    /**  
     * 复制单个文件  
     * @param oldPath String 原文件路径 如：c:/fqf.txt  
     * @param newPath String 复制后路径 如：f:/fqf.txt  
     * @return boolean  
     */   
   public void copyFile(String oldPath, String newPath) {   
       try {   
           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);
           File newFile = new File(newPath);
           if(!newFile.exists()){
        	   newFile.createNewFile();
           }
           if (!oldfile.exists()) { //文件不存在时   
               InputStream inStream = new FileInputStream(oldPath); //读入原文件   
               FileOutputStream fs = new FileOutputStream(newPath);   
               byte[] buffer = new byte[1444];   
               int length;   
               while ( (byteread = inStream.read(buffer)) != -1) {   
                   bytesum += byteread; //字节数 文件大小   
                   System.out.println(bytesum);   
                   fs.write(buffer, 0, byteread);   
               }   
               inStream.close(); 
               fs.flush();
               fs.close();
           }   
       }   
       catch (Exception e) {   
           System.out.println("复制单个文件操作出错");   
           e.printStackTrace();   
  
       }   
  
   }   
    
	
	
	
	
	
	//下面的暂时无用
	
	
	
	//保存到sdcard 
	public static Boolean savePic(Bitmap b,String strFileName){ 
		FileOutputStream fos = null; 
		try { 
			fos = new FileOutputStream(strFileName); 
			if (null != fos) 
			{ 
				b.compress(Bitmap.CompressFormat.PNG, 90, fos); 
				fos.flush(); 
				fos.close(); 
			} 
			return true;
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return false;
	}
	
	
	/**
	 * android 5.0以下区域截屏
	 * @param context
	 * @param handler
	 * @param x
	 * @param y
	 * @param width1
	 * @param height1
	 * @return
	 */
	@SuppressWarnings("deprecation")  
    public static Bitmap acquireScreenshot(Context context , Handler handler,int x,int y,int width1,int height1) {  
        WindowManager mWinManager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        DisplayMetrics metrics = new DisplayMetrics();  
        Display display = mWinManager.getDefaultDisplay();  
        display.getMetrics(metrics);  
        // 屏幕高  
        int height = metrics.heightPixels;  
        // 屏幕的宽  
        int width = metrics.widthPixels;  
  
        int pixelformat = display.getPixelFormat();  
        PixelFormat localPixelFormat1 = new PixelFormat();  
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);  
        // 位深  
        int deepth = localPixelFormat1.bytesPerPixel;  
  
        byte[] arrayOfByte = new byte[height * width * deepth];  
        try {  
            // 读取设备缓存，获取屏幕图像流  
           readAsRoot(arrayOfByte);  
             
            Log.d("root_cat", "handler前");
            //回调显示按钮
            if(handler != null){
				Message msg = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
  
            int[] tmpColor = new int[width * height];  
            int r, g, b;  
            for (int j = 0; j < width * height * deepth; j += deepth) {  
                r = arrayOfByte[j] & 0xff;  
                g = arrayOfByte[j + 1] & 0xff;  
                b = arrayOfByte[j + 2] & 0xff;  
                tmpColor[j / deepth] = (r << 16) | (g << 8) | b | (0xff000000);  
            }  
//            for(int portrait = 0; portrait < height; portrait ++){
//            	for(int transverse = 0; transverse < width; transverse ++){
//            		r = arrayOfByte[(portrait * transverse + transverse)*deepth] & 0xff;
//            		g = arrayOfByte[(portrait * transverse + transverse)*deepth + 1] & 0xff;
//            		b = arrayOfByte[(portrait * transverse + transverse)*deepth + 2] & 0xff;
//            		tmpColor[portrait * transverse + transverse] = (r << 16) | (g << 8) | b | (0xff000000);
//            	}
//            }
            // 构建bitmap  
            Bitmap scrBitmap = Bitmap.createBitmap(tmpColor, width, height,  
                    Bitmap.Config.ARGB_8888);  
            Log.d("root_cat", "返回前");
            Bitmap result = Bitmap.createBitmap(scrBitmap, x, y, width1, height1);
            return result;  
  
        } catch (Exception e) {  
            Log.d("root_cat", "#### 读取屏幕截图失败");  
            if(handler != null){
				Message msg = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
            e.printStackTrace();  
        }  
        return null;  
  
    }
	
	
	/**
	 * android 5.0 一下全屏截屏
	 * @param context
	 * @param handler
	 * @return
	 */
	@SuppressWarnings("deprecation")  
    public static Bitmap acquireScreenshot(Context context , Handler handler) {  
        WindowManager mWinManager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        DisplayMetrics metrics = new DisplayMetrics();  
        Display display = mWinManager.getDefaultDisplay();  
        display.getMetrics(metrics);  
        // 屏幕高  
        int height = metrics.heightPixels;  
        // 屏幕的宽  
        int width = metrics.widthPixels;  
  
        int pixelformat = display.getPixelFormat();  
        PixelFormat localPixelFormat1 = new PixelFormat();  
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);  
        // 位深  
        int deepth = localPixelFormat1.bytesPerPixel;  
  
        byte[] arrayOfByte = new byte[height * width * deepth];  
        try {  
            // 读取设备缓存，获取屏幕图像流  
           readAsRoot(arrayOfByte);  
             
            Log.d("root_cat", "handler前");
            //回调显示按钮
            if(handler != null){
				Message msg = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
  
            int[] tmpColor = new int[width * height];  
            int r, g, b;  
            for (int j = 0; j < width * height * deepth; j += deepth) {  
                r = arrayOfByte[j] & 0xff;  
                g = arrayOfByte[j + 1] & 0xff;  
                b = arrayOfByte[j + 2] & 0xff;  
                tmpColor[j / deepth] = (r << 16) | (g << 8) | b | (0xff000000);  
            }  
            // 构建bitmap  
            Bitmap scrBitmap = Bitmap.createBitmap(tmpColor, width, height,  
                    Bitmap.Config.ARGB_8888);  
            Log.d("root_cat", "返回前");
            return scrBitmap;  
  
        } catch (Exception e) {  
            Log.d("root_cat", "#### 读取屏幕截图失败");  
            if(handler != null){
				Message msg = new Message();
				msg.what = FullScreenshotsButton.FINISHHANDLER;
				handler.sendMessage(msg);
			}
            e.printStackTrace();  
        }  
        return null;  
  
    }  
	/** 
     * @Title: readAsRoot 
     * @Description: 以root权限读取屏幕截图 
     * @throws Exception 
     * @throws 
     */  
    public static void readAsRoot(byte[] arrayOfByte)  { 
    	InputStream localInputStream = null;
    	Process localProcess = null;
    	try{
        File deviceFile = new File(DEVICE_NAME);  
        localProcess = Runtime.getRuntime().exec("su");  
//        String str = "cat " + deviceFile.getAbsolutePath() + "\n"; 
        String cammd = MyConfig.GetInstance().RootComment + "\n";
        localProcess.getOutputStream().write(cammd.getBytes());
        String str = "screencap\n";  
        localProcess.getOutputStream().write(str.getBytes());  
        
        localInputStream =  localProcess.getInputStream();  
        DataInputStream localDataInputStream = new DataInputStream(  
                localInputStream);  
        localDataInputStream.readFully(arrayOfByte);  
        String exit = "exit\n";
        localProcess.getOutputStream().write(exit.getBytes());
        localProcess.waitFor();
    	}catch(Exception e){
    		
    	}finally{
    		try {
    			if(localInputStream != null)
    				localInputStream.close();
    			if(localProcess != null)
    				localProcess.destroy();
    			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    	}
        
    } 
    
    

	
	
	
	/**
	 * 无SurfaceView情况下截图功能实现
	 * @param act
	 * @return
	 */
	public static Bitmap takeScreenShot(Context act) {  
	    if (act == null ) {  
	        Log.d("TooL", "act参数为空.");  
	        return null;  
	    }  
	  
	    // 获取当前视图的view  
	    View scrView = new Dialog(act).getWindow().getDecorView();
	    scrView.setDrawingCacheEnabled(true);  
	    scrView.buildDrawingCache(true);  
	  
	    // 获取状态栏高度  
	    Rect statuBarRect = new Rect();  
	    scrView.getWindowVisibleDisplayFrame(statuBarRect);  
	    int statusBarHeight = statuBarRect.top;  
	    int width = ((WindowManager)act.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();  
	    int height = ((WindowManager)act.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();  
	  
	    Bitmap scrBmp = null;  
	    try {  
	        // 去掉标题栏的截图  
	    	Bitmap drawingCache = scrView.getDrawingCache();
	    	
	        scrBmp = Bitmap.createBitmap( scrView.getDrawingCache(), 0, statusBarHeight,  
	                width, height - statusBarHeight);  
	    } catch (IllegalArgumentException e) {  
	        Log.d("", "#### 旋转屏幕导致去掉状态栏失败");  
	    }  
	    scrView.setDrawingCacheEnabled(false);  
	    scrView.destroyDrawingCache();  
	    return scrBmp;  
	}
	
	public static Bitmap takeScreenShot(Window window,Context context) {  
        // View是你需要截图的View  
        View view = window.getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = null;  
        try {  
            b1 = view.getDrawingCache();  
        } catch (OutOfMemoryError e) {  
            // TODO: handle exception  
        }  
  
        // 获取状态栏高度  
        Rect frame = new Rect();  
        window.getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
        System.out.println(statusBarHeight);  
        // 获取屏幕长和高  
        WindowManager mWinManager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);
        int width = mWinManager.getDefaultDisplay().getWidth();  
        int height = mWinManager.getDefaultDisplay().getHeight();  
        // 去掉标题栏  
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);  
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);  
        view.destroyDrawingCache();  
        return b;  
    }  
}
