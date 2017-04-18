package com.example.screen.frament;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xsj_Screen.screen.R;

/**
 * Created by kingsoft on 2017/4/11.
 */
public class TopOptMemuFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_cancel;
    private ImageView iv_ok;
    private ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.screenhot_edit_top_menu_opt, container, false);
        iv_cancel = (ImageView) view.findViewById(R.id.cancel);
        iv_ok = (ImageView) view.findViewById(R.id.ok);
        iv_back = (ImageView) view.findViewById(R.id.back);
        iv_cancel.setOnClickListener(this);
        iv_ok.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.ok){
            ((EditPictureFragment)getParentFragment()).saveEdit();
        } else if (v.getId() == R.id.back){
            ((EditPictureFragment)getParentFragment()).BackOff();
        } else if (v.getId() == R.id.cancel){
            ((EditPictureFragment)getParentFragment()).BackAllOff();
        }
    }
}
