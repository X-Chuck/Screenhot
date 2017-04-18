package com.example.screen.adapter;

import com.example.screen.data.edit.WrittenWords;
import com.xsj_Screen.screen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

public class EditWordsEditAdapter extends BaseAdapter{

	EditText edit;
	View root;
	Button closeButton;
	Button yesButton;
	private OnClickListener closeButtonlistener;
	private OnClickListener yessButtonListener;
	private boolean isNewView = true;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(isNewView){
			if(convertView == null){
				root = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screenhot_edit_add_text_item, null);
			}
			
			closeButton = (Button) root.findViewById(R.id.edit_text_close_button);
			if(closeButtonlistener != null){
				closeButton.setOnClickListener(closeButtonlistener);
				closeButtonlistener = null;
			}
			edit = (EditText) root.findViewById(R.id.edit_text_text);
			yesButton = (Button) root.findViewById(R.id.edit_text_yes_button);
			if(yessButtonListener != null){
				yesButton.setOnClickListener(yessButtonListener);
				yessButtonListener = null;
			}
			
		}
//		android.view.ViewGroup.LayoutParams params = parent.getLayoutParams();
//		HorizontListView.LayoutParams para = new HorizontListView.LayoutParams(params.width, params.height);
//		convertView.setLayoutParams(para);
		return root;
	}
	
	public void setWrittenWords(WrittenWords writtenWords){
		edit.addTextChangedListener(writtenWords);
	}
	
	public void setNewView(Boolean isNewView){
		this.isNewView = isNewView;
	}
	public boolean getNewView(){
		return this.isNewView;
	}
	
	public void  setOnCloseButtonClick(OnClickListener listener){
		this.closeButtonlistener = listener;
	}
	
	public void setOnYesButtonClick(OnClickListener listener){
		this.yessButtonListener = listener;
	}
	
	public String getEditText(){
		if(edit == null){
			return null;
		}
		String text = edit.getText().toString();
		return text;
	}

}
