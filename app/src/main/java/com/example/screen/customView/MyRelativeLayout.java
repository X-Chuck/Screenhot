package com.example.screen.customView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.screen.interfaces.EditAttr;
import com.xsj_Screen.screen.R;

/**
 * Created by kingsoft on 2017/4/17.
 */
public class MyRelativeLayout extends RelativeLayout implements EditAttr {

    private DrawEdxtView edit;
    private boolean fixed;
    private int color;

    public MyRelativeLayout(Context context, int color)
    {
        super(context);
        init(context, color);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int color)
    {
        super(context, attrs);
        init(context, color);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int color)
    {
        super(context, attrs, defStyleAttr);
        init(context, color);
    }

    private void init(Context context, int color){
        edit = new DrawEdxtView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        edit.setLayoutParams(params);
        edit.setHint("拖动试试");
        edit.setBackground(getResources().getDrawable(R.drawable.screenhot_dashed_line_border));
        setClickable(true);
        this.addView(edit);
        fixed = false;
        this.color = color;
        edit.setTextColor(this.color);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (edit != null) {
            edit.reLayout();
        }
    }

    public boolean hadEdit(){
        if (edit.getText()==null || edit.getText().toString().trim().isEmpty()){
            return false;
        }
        return true;
    }

    public void removeEdit(){
        removeView(edit);
    }

    public void fixed(){
        if (edit.isEnabled())
            edit.setEnabled(false);
        edit.setBackground(getResources().getDrawable(R.drawable.screenhot_no_border));
        fixed = true;
    }


    @Override
    public boolean isFixed()
    {
        return fixed;
    }

    @Override
    public void setColor(int color)
    {
        this.color = color;
        edit.setTextColor(this.color);
    }
}
