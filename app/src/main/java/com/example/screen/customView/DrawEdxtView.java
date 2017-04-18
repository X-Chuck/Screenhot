package com.example.screen.customView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class DrawEdxtView extends EditText implements OnTouchListener {


    private Context mContext;


    private int screenWidth, screenHeight;

    private void getDisplayMetrics()
    {

        DisplayMetrics dm = getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels;

        screenHeight = dm.heightPixels - 50;

    }


    private int lastX, lastY; //最后x，y坐标


    private int downX, downY; // 按下View的X，Y坐标

    private int upX, upY; // 放手View的X,Y坐标

    private int rangeDifferenceX, rangeDifferenceY; // 放手和按下X,Y值差

    private int mDistance = 10; // 设定点击事件的移动距离值

    private int mL;
    private int mB;
    private int mR;
    private int mT;

    private int defaultHeight = 0;
    private int widthPadding = 0;
    private int parentheight = 0;
    private int parentwidth = 0;


    public DrawEdxtView(Context context)
    {

        super(context);

        init(context, this);

    }


    public DrawEdxtView(Context context, AttributeSet attrs)
    {

        super(context, attrs);

        init(context, this);

    }


    public DrawEdxtView(Context context, AttributeSet attrs, int defStyle)
    {

        super(context, attrs, defStyle);

        init(context, this);

    }

    private void init(Context context, OnTouchListener onTouchListener)
    {
        mContext = context;
        getDisplayMetrics();
        ;
        setOnTouchListener(onTouchListener);
    }

    public void calculateCoor()
    {
        if (mL == 0 && mT == 0 && mR == 0 && mB == 0) {//初始化坐标
            mB = getBottom();
            mT = getTop();
            mR = getRight();
            mL = getLeft();
        }
        Log.d("==========ml", "" + mL);
        Log.d("==========mR", "" + mR);
        Log.d("==========mT", "" + mT);
        Log.d("==========mB", "" + mB);
        if (defaultHeight == 0)
            defaultHeight = getHeight();
        if (widthPadding == 0) {
            widthPadding = getWidth() - (int) getPaint().measureText(getHint().toString());
        }
        getParentSize();
        int lineHeight = getLineHeight();
        TextPaint paint = getPaint();
        int textwidth = (int) paint.measureText(getText().toString());
        if (textwidth < ((int) paint.measureText(getHint().toString()) + widthPadding)) {
            textwidth = (int) paint.measureText(getHint().toString()) + widthPadding;
        }
        if ((textwidth + mL + (int) getPaint().measureText("1")) < parentwidth) {
            mR = textwidth + mL + widthPadding;
            setHeight(defaultHeight + lineHeight);
            mB = mT + defaultHeight + lineHeight;
//            setWidth(mR - mL);
        } else {
            mR = parentwidth;
            int height;
            height = ((textwidth) / (parentwidth - mL)) * lineHeight + defaultHeight;
            setHeight(height + lineHeight);
            mB = height + mT + lineHeight;
            if (mB > parentheight)
                mB = parentheight;
            setWidth(mR - mL);
        }
    }

    public void reLayout()
    {
        calculateCoor();
        layout(mL, mT, mR, mB);
    }

    public void getParentSize()
    {//初始化父控件参数
        if (parentheight == 0 && parentwidth == 0) {

            ViewGroup mParent = (ViewGroup) getParent();
            if (null != mParent) {
                parentwidth = mParent.getWidth();
                parentheight = mParent.getHeight();
            }
        }
    }


/*隐藏键盘*/

    public static void hideSoftInput(Context mContext, View view)
    {

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    @Override

    public boolean onTouch(View v, MotionEvent event)
    {

        int action = event.getAction();

        getParentSize();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                downX = (int) event.getRawX();

                downY = (int) event.getRawY();

                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标

                lastY = (int) event.getRawY();

                Log.d("按下：", downX + "----X轴坐标");

                Log.d("按下：", downY + "----Y轴坐标");

                break;

            case MotionEvent.ACTION_MOVE:

                int dx = (int) event.getRawX() - lastX;

                int dy = (int) event.getRawY() - lastY;


                mL = v.getLeft() + dx;

                mB = v.getBottom() + dy;

                mR = v.getRight() + dx;

                mT = v.getTop() + dy;


                if (mL < 0) {

                    mL = 0;

                    mR = mL + v.getWidth();

                }


                if (mT < 0) {

                    mT = 0;

                    mB = mT + v.getHeight();

                }


                if (mR > parentwidth && (mR - mL) < 264) {

                    mR = parentwidth;

                    mL = mR - v.getWidth();

                }


                if (mB > parentheight && (mB - mT) < 200) {

                    mB = parentheight;

                    mT = mB - v.getHeight();

                }
                calculateCoor();
                v.layout(mL, mT, mR, mB);

                Log.d("绘制：", "l=" + mL + ";t=" + mT + ";r=" + mR + ";b=" + mB);


                lastX = (int) event.getRawX();

                lastY = (int) event.getRawY();

                v.postInvalidate();


                v.setFocusable(false);

                v.setFocusableInTouchMode(false);

                hideSoftInput(mContext, v);

                break;

            case MotionEvent.ACTION_UP:

                upX = (int) event.getRawX();

                upY = (int) event.getRawY();


                Log.d("离开：", upX + "----X轴坐标");

                Log.d("离开：", upY + "----Y轴坐标");


                rangeDifferenceX = upX - downX;

                rangeDifferenceY = upY - downY;

                if (rangeDifferenceX > 0 && rangeDifferenceX <= mDistance) {

                    if (rangeDifferenceY >= 0 && rangeDifferenceY <= mDistance) {

                        v.setFocusable(true);

                        v.setFocusableInTouchMode(true);

                        Log.d("是否是点击事件：", true + "");


                    } else {

                        if (rangeDifferenceY <= 0 && rangeDifferenceY >= -mDistance) {

                            v.setFocusable(true);

                            v.setFocusableInTouchMode(true);

                            Log.d("是否是点击事件：", true + "");


                        } else {

                            v.setFocusable(false);

                            v.setFocusableInTouchMode(false);

                            Log.d("是否是点击事件：", false + "");

                        }

                    }

                } else {

                    if (rangeDifferenceX <= 0 && rangeDifferenceX >= -mDistance) {

                        v.setFocusable(true);

                        v.setFocusableInTouchMode(true);

                        Log.d("是否是点击事件：", true + "");


                    } else {

                        v.setFocusable(false);

                        v.setFocusableInTouchMode(false);

                        Log.d("是否是点击事件：", false + "");

                    }

                }

                break;

            default:

                break;

        }

        return false;

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter)
    {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
