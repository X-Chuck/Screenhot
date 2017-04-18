package com.example.screen.control;

import com.example.screen.tool.ScreenTool;

import android.app.Service;
import android.os.Handler;

public class ScreenControler {
	private static ScreenControler controler;
	private ScreenControler(){};
	private Service service;
	private ScreenTool tool;
	public static ScreenControler GetInstance(){
		if(controler == null){
			controler = new ScreenControler();
		}
		return controler;
	}
	public void setService(Service service){
		this.service = service;
		if(tool == null){
			initTool();
		}
	}
	
	private void initTool(){
		tool = new ScreenTool(service);
	}
	
	/**
	 * 全屏截屏
	 * @param handler
	 */
	public void FullScreenshots(Handler handler){
		if(service == null){
			return;
		}
		tool.Screencap(service, handler);
	}
	
	/**
	 * 区域截屏
	 * @param handler
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void RegionScreenShots(Handler handler, int x,int y,int width, int height){
		if(service == null){
			return ;
		}
		tool.Screencap(service, handler, x, y, width, height);
	}
}
