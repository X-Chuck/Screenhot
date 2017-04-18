package com.example.screen.animate;

import android.view.View;

public interface CustonAnimate {
	public void start();
	public void setView(View view);
	public void setDuration(long duration);
	public void setRepeatCount(int count);
	public void setRepeatModel(int value);
	public boolean isRunning();
}
