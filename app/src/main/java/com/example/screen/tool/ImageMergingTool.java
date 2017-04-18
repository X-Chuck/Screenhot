package com.example.screen.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.View.MeasureSpec;

public class ImageMergingTool {
	
	 public static Bitmap mergeBitmap(Bitmap background, Bitmap edit) {
		 if(background == null || edit == null){
			 return null;
		 }
	        Bitmap bitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(),
	        		background.getConfig());
	        Canvas canvas = new Canvas(bitmap);
	        canvas.drawBitmap(background, new Matrix(), null);
	        canvas.drawBitmap(edit, 0, 0, null);
	        return bitmap;
	    }
	 
	 public static Bitmap convertViewToBitmap(View view){
//	        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	        view.buildDrawingCache();
	        Bitmap bitmap = view.getDrawingCache();

	        return bitmap;
	}
	 
	 public static Bitmap HandleBitmap(Bitmap base, Bitmap bitmap){
		 if(base == null || bitmap == null){
			 return null;
		 }
		 float baseWidth = base.getWidth();
		 float baseHeight = base.getHeight();
		 float width = bitmap.getWidth();
		 float height = bitmap.getHeight();
		 float baseProportion = baseHeight/baseWidth;
		 float bitProportion = height/width;
		 int newWidth = 0;
		 int newHeight = 0;
		 if(baseProportion > bitProportion){
			 newHeight = (int) height;
			 newWidth = (int) (height/baseProportion);
		 }else{
			 newWidth = (int) width;
			 newHeight = (int) (width * baseProportion);
		 }
		 int x = (int) (width/2 - newWidth/2);
		 int y = (int) (height/2 - newHeight/2);
		 Bitmap newBitmap = Bitmap.createBitmap(bitmap, x, y, newWidth, newHeight);
		 return newBitmap;
	 }
	 
	 public static Bitmap BitmapScalling(Bitmap base , Bitmap bitmap){
		 if(base == null || bitmap == null){
			 return null;
		 }
		 float baseWidth = base.getWidth();
		 float baseHeight = base.getHeight();
		 float width = bitmap.getWidth();
		 float height = bitmap.getHeight();
		 float baseProportion = baseHeight/baseWidth;
		 float bitProportion = height/width;
		 if(baseProportion - bitProportion > 0.01f || bitProportion - baseProportion > 0.01f){
			 return null;
		 }
		 float pro = baseWidth/width;
		 Matrix matrix = new Matrix();
		 matrix.postScale(pro, pro);
		 Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int)width, (int)height, matrix, true);
		 return newBitmap;
	 }
	 
	 public static void SaveBitmapToPicture(Bitmap bitmap , String path){
		 if(bitmap == null || path == null){
			 return;
		 }
		 try{
		 File file = new File(path);
		 if(!file.exists()){
			file.createNewFile();
		 }
		 FileOutputStream out = new FileOutputStream(file);
		 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		 out.flush();
		 out.close();
		 }catch(Exception e){
			 
		 }
	 }
}
