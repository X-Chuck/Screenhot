package com.example.screen.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import com.example.screen.tool.MyConfig;
import com.example.screen.tool.PhoneMesgagerTool;
import com.xsj_Screen.screen.R;

import android.annotation.SuppressLint;
import android.content.Context;

public class MyFtpServer {
	@SuppressLint("SdCardPath")  
	private static final String dirname = "/mnt/sdcard/ftp"; 
	// ftp服务器配置文件路径  
	private static final String filename = dirname + "/users.properties";  
	private FtpServer mFtpServer = null;  
	private static final int PORT = 2222; 
	private static MyFtpServer server = null;
	
	private MyFtpServer(){};
	
	public static MyFtpServer GetInstance(){
		if(server == null){
			server = new MyFtpServer();
		}
		return server;
	}
	
	
	
	/**
	 * 创建配置文件
	 */
	private boolean CreateProperties(Context context){
		if(isFolderExists(dirname)){
			String[] stringArray = context.getResources().getStringArray(R.array.user_properties);
			File proFile = new File(filename);
			try {
				if(!proFile.exists()){
					proFile.createNewFile();
				}
				FileWriter writer = new FileWriter(proFile);
				for(String str : stringArray){
					if(str.indexOf("homedirectory") != -1)
					{
						MyConfig config = MyConfig.GetInstance();
						str += config.SavePicturePath;
					}
					writer.write(str+"\n");
				}
				writer.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
		}
		return false;
	}
	
	
	/**
	 * 创建目录
	 * @param strFolder
	 * @return
	 */
	private boolean isFolderExists(String strFolder) {
	        File file = new File(strFolder);
	        if (!file.exists()) {
	            if (file.mkdirs()) {
	                return true;
	            } else
	                return false;
	        }
	        return true;
	    }
	
	/**
	 * 开启ftp服务器
	 * @param hostip
	 */
	private boolean startFtpServer(String hostip) {  
	    FtpServerFactory serverFactory = new FtpServerFactory();  
	    PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();  
	    File files = new File(filename);  
	    //设置配置文件  
	    userManagerFactory.setFile(files);  
	    serverFactory.setUserManager(userManagerFactory.createUserManager());  
	    // 设置监听IP和端口号  
	    ListenerFactory factory = new ListenerFactory();  
	    factory.setPort(PORT);
	    factory.setServerAddress(hostip);  
	    Listener createListener = factory.createListener();
	    
	    // replace the default listener  
	    serverFactory.addListener("default", factory.createListener());  
	    
	    // start the server  
	    mFtpServer = serverFactory.createServer();  
	    try {  
	        mFtpServer.start();  
	        MyConfig config = MyConfig.GetInstance();
	        config.IsFtpServerOpen = true;
	        return true;
	    } catch (FtpException e) {  
	        System.out.println(e);  
	    }  
	    return false;
	}
	
	/**
	 * 开启ftp服务器
	 * @param context
	 * @return
	 */
	public boolean start(Context context){
		MyConfig config = MyConfig.GetInstance();
		if(config.IsFtpServerOpen){
			return true;
		}
		boolean createProperties = CreateProperties(context);
		if(createProperties){
			boolean result = startFtpServer(PhoneMesgagerTool.getWIFILocalIpAdress(context));
			return result;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 停止ftp服务器
	 */
	public void stop(){
		 MyConfig config = MyConfig.GetInstance();
	     config.IsFtpServerOpen = false;
		if(mFtpServer == null){
			return;
		}
		if(mFtpServer.isStopped()){
			return;
		}
		mFtpServer.stop();
	}

}
