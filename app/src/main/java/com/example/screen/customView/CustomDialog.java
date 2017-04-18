package com.example.screen.customView;

import com.xsj_Screen.screen.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog{

	private Context mContext;
	private View root;
	private TextView text;
	private Button cancel;
	private Button determine;
	public CustomDialog(Context context, int themeResId) {
		super(context, themeResId);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		root = inflater.inflate(R.layout.screenhot_mydialog, null);
		this.setContentView(root);
		text = (TextView) root.findViewById(R.id.dialogText);
		cancel = (Button) root.findViewById(R.id.dialogCancel);
		determine = (Button) root.findViewById(R.id.dialogDetermine);
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	public CustomDialog setCancelListener(android.view.View.OnClickListener l){
		cancel.setOnClickListener(l);
		return this;
	}
	public CustomDialog setDetermineListener(android.view.View.OnClickListener l){
		determine.setOnClickListener(l);
		return this;
	}
	
	public String getText(){
		CharSequence text2 = text.getText();
		return text2.toString();
	}
	

}
