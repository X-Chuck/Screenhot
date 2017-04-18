package com.example.screen.data;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class Picture {
	private String path;
	private File file;
	private String name;
	private Bitmap bitmap;
	public Picture(String path){
		File file = new File(path);
		if(!file.exists()){
			return;
		}
		if(!file.isFile()){
			return;
		}
		String fileName = file.getName();
		if(fileName.endsWith(".jpg") || fileName.endsWith(".png")){
			this.path = path;
			this.name = fileName;
			this.file = file;
//			Bitmap decodeFile = BitmapFactory.decodeFile(this.path);
			BitmapFactory.Options options = new Options();
			options.inSampleSize = 10;
			options.inJustDecodeBounds = false;
			this.bitmap = BitmapFactory.decodeFile(this.path, options);
		}
		
	}
	
	public String getPath() {
		return path;
	}
	public File getFile() {
		return file;
	}
	public String getName() {
		return name;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
}
