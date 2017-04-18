package com.example.screen.tool;

public class EditConfig {

	static private EditConfig config;
	static public EditConfig GetInstance(){
		if(config == null){
			config = new EditConfig();
		}
		return config;
	}
	private EditConfig()
	{
		
	}
	
	public int color;
	public String Pic_path;
	
	
}
