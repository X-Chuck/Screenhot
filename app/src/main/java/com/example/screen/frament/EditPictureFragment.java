package com.example.screen.frament;

import com.example.screen.adapter.EditColorSelectadapter;
import com.example.screen.adapter.EditGraphicalSelectAdapter;
import com.example.screen.adapter.EditStrokeAdapter;
import com.example.screen.adapter.EditWordsEditAdapter;
import com.example.screen.control.EditGraphicalControler;
import com.example.screen.control.EditGraphicalControler.Graphical;
import com.example.screen.control.EditStrokeControler;
import com.example.screen.control.PictureMergingContrler;
import com.example.screen.customView.CustomPropressBar;
import com.example.screen.customView.Edit;
import com.example.screen.customView.EditColorSelectButton;
import com.example.screen.customView.EditView;
import com.example.screen.customView.EditWrittenWrodsView;
import com.example.screen.customView.HorizontListView;
import com.example.screen.customView.MyRelativeLayout;
import com.example.screen.data.edit.WrittenWords;
import com.example.screen.interfaces.EditAttr;
import com.example.screen.listener.ColorSelectListener;
import com.example.screen.listener.OnAddListener;
import com.example.screen.listener.OnGraphicalSelectListener;
import com.example.screen.listener.OnStrokeChangeListener;
import com.example.screen.tool.EditConfig;
import com.xsj_Screen.screen.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Stack;

public class EditPictureFragment extends Fragment implements OnClickListener, ColorSelectListener, OnGraphicalSelectListener, OnStrokeChangeListener, OnAddListener{
    @Override
    public void onAdding()
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.top_memu);
        if (!(fragment instanceof TopOptMemuFragment)){
            changeTopMemu(true);
        }
    }

    @Override
    public void finishAdd()
    {
        closeAllPage();
    }

    enum FloatType {
        color, text, none, graphical, stroke, newtext, empty
    }

    private FloatType floatType = FloatType.none;
    private LinearLayout edit_stroke;//笔画按钮
    private LinearLayout edit_graphical;//图形按钮
    private LinearLayout edit_words;//文字按钮
    private LinearLayout new_edit_words;//文字按钮
    private LinearLayout edit_color;//颜色选择按钮
    private EditColorSelectButton edit_custon_color;//当前颜色

    private ImageView be_edit_pic;//图片
    private FrameLayout edit_layout;//编辑区
    private LinearLayout floatLayout;//浮动条
    private HorizontListView listView;
    private RelativeLayout saveMask;
	private CustomPropressBar propressBar;


    //颜色选择的adapter
    EditColorSelectadapter colorAdapter;
    //添加文字的adapter
    EditWordsEditAdapter wordsEditAdapter;
    //编辑去栈
    Stack stack;
    //图形选择adapter
    EditGraphicalSelectAdapter graphicalAdapter;
    //图形控制器
    EditGraphicalControler graphicalControler;
    //笔画控制器
    EditStrokeControler strokeControler;
    //笔画adapter
    EditStrokeAdapter strokeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        View rootView = (View) inflater.inflate(R.layout.screenhot_edit_pic_frament, null);
        saveMask = (RelativeLayout) rootView.findViewById(R.id.pic_save_mask);
        edit_stroke = (LinearLayout) rootView.findViewById(R.id.edit_stroke);
        edit_graphical = (LinearLayout) rootView.findViewById(R.id.edit_graphical);
        edit_words = (LinearLayout) rootView.findViewById(R.id.edit_words);
        new_edit_words = (LinearLayout) rootView.findViewById(R.id.new_edit_words);
        edit_color = (LinearLayout) rootView.findViewById(R.id.edit_color);
        be_edit_pic = (ImageView) rootView.findViewById(R.id.be_edit_pic);
        edit_layout = (FrameLayout) rootView.findViewById(R.id.edit_layout);
        floatLayout = (LinearLayout) rootView.findViewById(R.id.floatLayout);
        listView = (HorizontListView) rootView.findViewById(R.id.edit_listView);
        edit_custon_color = (EditColorSelectButton) rootView.findViewById(R.id.edit_custon_color);
		propressBar = (CustomPropressBar) rootView.findViewById(R.id.propressBar);
        edit_stroke.setOnClickListener(this);
        edit_graphical.setOnClickListener(this);
        edit_words.setOnClickListener(this);
        new_edit_words.setOnClickListener(this);
        edit_color.setOnClickListener(this);
        stack = new Stack();
        changeTopMemu(false);
        return rootView;
    }

    private void showPic()
    {
        EditConfig config = EditConfig.GetInstance();
        Bitmap bitmap = BitmapFactory.decodeFile(config.Pic_path);//建议使用线程加载
        be_edit_pic.setImageBitmap(bitmap);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        EditConfig config = EditConfig.GetInstance();
        config.color = edit_custon_color.getColor();
        showPic();
    }

    @Override
    public void onStop()
    {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        colorAdapter = null;
        wordsEditAdapter = null;
        graphicalAdapter = null;
        graphicalControler = null;
        strokeAdapter = null;
        strokeControler = null;
        listView.setAdapter(null);
        stack.clear();
        edit_layout.removeAllViews();
        stack = null;
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.edit_stroke) {//笔画点击
            if (floatType == FloatType.text && floatLayout.getVisibility() == View.VISIBLE) {
                addText();
            }
            if (floatType == FloatType.newtext){
                newFixEditView();
            }
            AddStroke();
        } else if (id == R.id.edit_graphical) {//图形点击
            if (floatType == FloatType.text && floatLayout.getVisibility() == View.VISIBLE) {
                addText();
            }
            if (floatType == FloatType.newtext){
                newFixEditView();
            }
            SelectGraphical();
        } else if (id == R.id.edit_words) {//文字点击
            if (floatType == FloatType.newtext){
                newFixEditView();
            }
            if (addText()) {
                AddTextEditView();
            }
        } else if (id == R.id.edit_color) {//颜色点击
//            if (floatType == FloatType.text && floatLayout.getVisibility() == View.VISIBLE) {
//                addText();
//            }
//            if (floatType == FloatType.newtext) {
//                newFixEditView();
//            }
            SelectColor();
        }else if (id == R.id.new_edit_words){
            if (floatType == FloatType.text && floatLayout.getVisibility() == View.VISIBLE) {
                addText();
            }
            newAddText();
        }else if (id == R.id.edit_text_close_button)//关闭文字
            closeTextEdit();
        else if (id == R.id.edit_text_yes_button)//确定文字添加
            fixedTextEdit();
    }

    //切换顶部菜单
    private void changeTopMemu(boolean isOpt)
    {
        if (isOpt) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.top_memu, new TopOptMemuFragment());
            transaction.commit();
        } else {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.top_memu, new TopSaveMemuFragment());
            transaction.commit();
        }
    }

    private void newAddText(){
        if (floatType==FloatType.newtext && newFixEditView()){
            return;
        }
        floatLayout.setVisibility(View.GONE);
        AddNewEditView();
        floatType = FloatType.newtext;
    }

    //判断是否编辑过
    public boolean hadEdit()
    {
        return !stack.isEmpty();
    }

    //分享图片
    public void share()
    {
        EditConfig config = EditConfig.GetInstance();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, config.Pic_path);
        intent.setType("text/plain");
        startActivity(intent);
    }

    //暂缓编辑
    public void saveEdit()
    {
        changeTopMemu(false);
        if (floatLayout.getVisibility() == View.VISIBLE && floatType == FloatType.text)
            fixedTextEdit();
        closeAllPage();
    }

    public void SavePic(Handler memuHandler)
    {
        propressBar.start();
        FixedTopEditView();
        EditConfig config = EditConfig.GetInstance();
        Handler editHandler = new Handler() {
            @Override
            public void handleMessage(Message msg)
            {
                // TODO Auto-generated method stub
                if (msg.what == PictureMergingContrler.WHAT) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run()
                        {
                            saveMask.setVisibility(View.GONE);
                            propressBar.stop();
                        }
                    });
                }
            }
        };
        PictureMergingContrler contrle = new PictureMergingContrler();
        contrle.setHandler(editHandler, memuHandler);
        contrle.setPicture(config.Pic_path);
        contrle.setStack(stack);
        contrle.Merge();
        saveMask.setVisibility(View.VISIBLE);
    }


    /**
     * 确定当前文字输入
     */
    public void fixedTextEdit()
    {
        FixedTopEditView();
        wordsEditAdapter.setNewView(true);
        floatLayout.setVisibility(View.GONE);
    }

    /**
     * 取消当前文字编辑
     */
    public void closeTextEdit()
    {
        closeTopEditView();
        wordsEditAdapter.setNewView(true);
        floatLayout.setVisibility(View.GONE);
    }

    /**
     * 删除编辑区最上面的层
     *
     * @return
     */
    private boolean closeTopEditView()
    {
        if (stack.empty()) {
            //空栈
            return false;
        }
        View view = (View) stack.pop();
        edit_layout.removeView(view);
        return true;
    }

    public boolean newFixEditView()
    {
        if (stack.empty()) {
            //空栈
            return false;
        }
        MyRelativeLayout view = (MyRelativeLayout) stack.peek();
        if (view.hadEdit()){
            view.fixed();
            return false;
        }
        view.removeEdit();
        edit_color.removeView((View) stack.pop());
        return true;
    }

    /**
     * 固定编辑去最上面的层
     *
     * @return
     */
    private boolean FixedTopEditView()
    {
        if (stack.empty()) {
            return false;
        }
        if (stack.peek() instanceof EditView){
            EditView view = (EditView) stack.peek();
            view.fixed();
        }
        return true;
    }

    /**
     * 添加文字编辑层到编辑版面
     */
    private void AddTextEditView()
    {
        EditView view = AddEditView();
        EditWrittenWrodsView editView = new EditWrittenWrodsView(edit_layout.getWidth(), edit_layout.getHeight());
        editView.setFragment(this);
        WrittenWords conten = editView.getConten();
        wordsEditAdapter.setWrittenWords(conten);
        wordsEditAdapter.setNewView(false);
        editView.setColor(edit_custon_color.getColor());
        view.setEdit(editView);
        view.invalidate();

    }

    /**
     * 添加编辑层到编辑版面
     *
     * @return
     */
    @SuppressLint("NewApi")
    private EditView AddEditView()
    {
        onAdding();
        EditView view = new EditView(this.getActivity().getBaseContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(edit_layout.getWidth(), edit_layout.getHeight());
        view.setLayoutParams(params);
        edit_layout.addView(view);
        stack.push(view);
        return view;
    }

    private void AddNewEditView(){
        onAdding();
        MyRelativeLayout view = new MyRelativeLayout(getActivity().getBaseContext(), edit_custon_color.getColor());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(edit_layout.getWidth(), edit_layout.getHeight());
        view.setLayoutParams(params);
        edit_layout.addView(view);
        stack.push(view);
    }


    /**
     * 打开添加文字版面
     */
    private boolean addText()
    {
        int visibility = floatLayout.getVisibility();
        if (visibility == View.VISIBLE && floatType == FloatType.text) {
            floatLayout.setVisibility(View.GONE);
            String editText = wordsEditAdapter.getEditText();
            if (editText == null || editText.equals("")) {
                closeTextEdit();
            }
            return false;
        }
        floatLayout.setVisibility(View.VISIBLE);
        if (wordsEditAdapter == null) {
            wordsEditAdapter = new EditWordsEditAdapter();

        }
        if (wordsEditAdapter.getNewView()) {
            wordsEditAdapter.setOnCloseButtonClick(this);
            wordsEditAdapter.setOnYesButtonClick(this);
        }
        listView.setAdapter(wordsEditAdapter);
        floatType = FloatType.text;
        return wordsEditAdapter.getNewView();

    }

    /**
     * 添加笔画
     */
    public void AddStroke()
    {
        int visibility = floatLayout.getVisibility();
        if (visibility == View.VISIBLE && floatType == FloatType.stroke) {
            floatLayout.setVisibility(View.GONE);
            return;
        }
        if (strokeAdapter == null) {
            strokeAdapter = new EditStrokeAdapter();
            strokeControler = new EditStrokeControler(stack, edit_layout, edit_custon_color, getActivity().getBaseContext(), this);
            strokeAdapter.SetOnStrokeChangeListener(this);
        }
        floatLayout.setVisibility(View.VISIBLE);
        listView.setAdapter(strokeAdapter);
        int alpha = strokeAdapter.getAlpha();
        int stroke = strokeAdapter.getStroke();
        strokeControler.ChangeStroke(stroke);
        strokeControler.ChangeAlpha(alpha);
        strokeControler.CreateStroke();
        floatType = FloatType.stroke;
    }

    /**
     * 打开颜色选择版面
     */
    private void SelectColor()
    {
        int visibility = floatLayout.getVisibility();
        if (visibility == View.VISIBLE && floatType == FloatType.color) {
            floatLayout.setVisibility(View.GONE);
            return;
        }
        floatLayout.setVisibility(View.VISIBLE);
        if (colorAdapter == null) {
            colorAdapter = new EditColorSelectadapter();
            colorAdapter.setOnColorSelectListener(this);
        }
        listView.setAdapter(colorAdapter);
        floatType = FloatType.color;
    }

    /**
     * 打开图形选择版面
     */
    private void SelectGraphical()
    {
        int visibility = floatLayout.getVisibility();
        if (visibility == View.VISIBLE && floatType == FloatType.graphical) {
            floatLayout.setVisibility(View.GONE);
            return;
        }
        floatLayout.setVisibility(View.VISIBLE);
        if (graphicalAdapter == null) {
            graphicalAdapter = new EditGraphicalSelectAdapter();
            graphicalControler = new EditGraphicalControler(stack, edit_layout, edit_custon_color, getActivity().getBaseContext(), this);
            graphicalAdapter.setOnGraphicalSelectListener(this);
        }
        listView.setAdapter(graphicalAdapter);
        floatType = FloatType.graphical;
    }

    /**
     * 关闭当前fragment
     */
    public void FinishThisFragment()
    {
        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * 关闭所有编辑版面
     */
    private void closeAllPage()
    {
        if (floatLayout.getVisibility() == View.VISIBLE) {
            floatLayout.setVisibility(View.GONE);
        }
        if (floatType == FloatType.text) {
            wordsEditAdapter = null;
        } else if (floatType == FloatType.color) {
            wordsEditAdapter = null;
//			SelectColor();
        } else if (floatType == FloatType.graphical) {
            wordsEditAdapter = null;
        }
    }

    public void BackOff()
    {
        closeAllPage();
        View pop = (View) stack.pop();
        edit_layout.removeView(pop);
        if (stack.empty()) {
//            FinishThisFragment();
            changeTopMemu(false);
        }
    }

    public void BackAllOff()
    {
        closeAllPage();
        while (!stack.empty()) {
            View pop = (View) stack.pop();
            edit_layout.removeView(pop);
        }
        changeTopMemu(false);

    }

    /**
     * 选择颜色回调
     */
    @Override
    public void onColorSelect(int color)
    {
        // TODO Auto-generated method stub
        edit_custon_color.setColor(color);
        edit_custon_color.postInvalidate();
        if (!stack.empty()) {
            View view = (View) stack.peek();
            if (!((EditAttr)view).isFixed()) {
//                Edit edit = view.getEdit();
                ((EditAttr)view).setColor(edit_custon_color.getColor());
                view.invalidate();
            }
        }
        SelectColor();
        if (graphicalControler != null) {
            graphicalControler.ChangeColor();
        }
        if (strokeControler != null) {
            strokeControler.ChangeColor();
        }

    }

    @Override
    public void onGraphicalSelect(Graphical graphical)
    {
        if (!stack.isEmpty()) {
            EditAttr peek = (EditAttr) stack.peek();
            if (!peek.isFixed()) {
                peek.fixed();
            }
        }
        closeAllPage();
        graphicalControler.setGraphical(graphical);
        graphicalControler.CrateGraphical();
    }

    @Override
    public void OnStrokeChange(int progress)
    {
        // TODO Auto-generated method stub
        if (strokeControler != null) {
            strokeControler.ChangeStroke(progress);
        }
    }

    @Override
    public void OnAlphaChange(int progress)
    {
        // TODO Auto-generated method stub
        if (strokeControler != null) {
            strokeControler.ChangeAlpha(progress);
        }
    }

    public boolean isSaving(){
        return saveMask.getVisibility() == View.VISIBLE;
    }


}
