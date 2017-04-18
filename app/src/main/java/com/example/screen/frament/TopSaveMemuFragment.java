package com.example.screen.frament;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.screen.control.PictureMergingContrler;
import com.xsj_Screen.screen.R;

public class TopSaveMemuFragment extends Fragment implements View.OnClickListener {
    private Button btn_share;
    private Button btn_save;
    private Button btn_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.screenhot_edit_top_menu_save, container, false);
        btn_back = (Button) view.findViewById(R.id.back);
        btn_back.setOnClickListener(this);
        btn_save = (Button) view.findViewById(R.id.save);
        btn_save.setOnClickListener(this);
        btn_share = (Button) view.findViewById(R.id.share);
        btn_share.setOnClickListener(this);
        btn_share.setVisibility(View.INVISIBLE);
        if (((EditPictureFragment)getParentFragment()).hadEdit()){
            btn_save.setTextColor(getResources().getColor(R.color.screenhot_btn_white));
            btn_save.setEnabled(true);
        } else {
            btn_save.setTextColor(getResources().getColor(R.color.screenhot_btn_gray));
            btn_save.setEnabled(false);
        }
        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.back){
            ((EditPictureFragment)getParentFragment()).FinishThisFragment();
        } else if (v.getId() == R.id.save){
            ((EditPictureFragment)getParentFragment()).SavePic(memuHandler);
        } else if (v.getId() == R.id.share){
            ((EditPictureFragment)getParentFragment()).share();
        }
    }

    private Handler memuHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == PictureMergingContrler.WHAT) {
                TopSaveMemuFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run()
                    {
                        btn_share.setVisibility(View.VISIBLE);
                        btn_save.setTextColor(getResources().getColor(R.color.screenhot_btn_gray));
                        btn_save.setEnabled(false);
                    }
                });
            }
        }
    };
}
