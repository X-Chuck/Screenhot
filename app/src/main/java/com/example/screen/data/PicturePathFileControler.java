package com.example.screen.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class PicturePathFileControler {
	private int layer = 0;
	private PicturePathFiler currentFile;
	private List<PicturePathFiler> currentCatalog;
	public PicturePathFileControler(){
		init();
	}
	
	private void init(){
		File root = getRootFile();
		currentFile = new PicturePathFiler(0, root, root.getName());
		currentCatalog = getChildList(currentFile);
		layer = 0;
		
	}
	
	private File getRootFile(){
		File rootFile = Environment.getExternalStorageDirectory();
		return rootFile;
	}
	
	/**
	 * 获取目录下的所有目录
	 * @param file
	 * @return
	 */
	private List<PicturePathFiler> getChildList(PicturePathFiler file){
		if(file == null ){
			return new ArrayList<PicturePathFiler>();
		}
		File[] listFiles = file.getFile().listFiles();
		if(listFiles == null){
			return new ArrayList<PicturePathFiler>();
		}
		List<PicturePathFiler> result = new ArrayList<PicturePathFiler>();
		int id = 0;
		for(File f : listFiles){
			if(f.isDirectory()){
				String name = f.getName();
				result.add(new PicturePathFiler(id++, f, name));
			}
		}
		return result;
	}
	
	/**
	 * 获取下个节点
	 * @param file
	 */
	public void next(PicturePathFiler file){
		if(file == null){
			file = currentFile;
		}
		currentFile = file;
		List<PicturePathFiler> childList = getChildList(file);
		currentCatalog = childList;
		layer++;
	}
	

	/**
	 * 获取上一个节点
	 * @param file
	 */
	public void previous(PicturePathFiler file){
		if(layer == 0){
			return;
		}
		if(file == null){
			file = currentFile;
		}
		
		currentFile =  new PicturePathFiler(0, file.getFile().getParentFile(), file.getFile().getParentFile().getName());
		List<PicturePathFiler> childList = getChildList(currentFile);
		currentCatalog = childList;
		layer --;
	}
	
	public PicturePathFiler getCurrentNode(){
		return currentFile;
	}
	
	public List<PicturePathFiler> getCurrentCatalog(){
		return currentCatalog;
	}
	
	public int getLayer(){
		return layer;
	}

}
