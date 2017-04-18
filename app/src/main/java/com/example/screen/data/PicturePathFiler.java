package com.example.screen.data;

import java.io.File;

public class PicturePathFiler {
	private Integer id;
	private File file;
	private String name;
	public PicturePathFiler(int id,File file,String name){
		this.id = new Integer(id);
		this.file = file;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
