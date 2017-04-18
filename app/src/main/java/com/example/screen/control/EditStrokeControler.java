package com.example.screen.control;

import android.content.Context;
import android.widget.FrameLayout;

import com.example.screen.customView.Edit;
import com.example.screen.customView.EditColorSelectButton;
import com.example.screen.customView.EditStrokeView;
import com.example.screen.customView.EditView;
import com.example.screen.listener.OnAddListener;
import com.example.screen.listener.OnCompleteAnEdit;

import java.util.Stack;

public class EditStrokeControler implements OnCompleteAnEdit {

    private Stack stack;
    private FrameLayout edit_layout;//编辑区
    private EditColorSelectButton edit_custon_color;//当前颜色
    private Context context;
    private int stroke = 10;
    private int alpha = 255;
    private EditView view;
    private OnAddListener listener;

    public EditStrokeControler(Stack stack,
                               FrameLayout edit_Layout,
                               EditColorSelectButton edit_custon_color,
                               Context context,
                               OnAddListener listener)
    {
        this.stack = stack;
        this.edit_layout = edit_Layout;
        this.edit_custon_color = edit_custon_color;
        this.context = context;
        this.listener = listener;
    }

    public void CreateStroke()
    {
        if (context == null || edit_layout == null || edit_custon_color == null) {
            return;
        }
        if (view != null) {
            edit_layout.removeView(view);
        }
        if (!stack.isEmpty() && stack.peek() instanceof EditView) {
            EditView view = (EditView) stack.peek();
            if (!view.isFixed()) {
                view.fixed();
            }
        }
        view = new EditView(context);
        EditStrokeView edit = new EditStrokeView();
        edit.setColor(edit_custon_color.getColor());
        edit.setAlpha(alpha);
        edit.setStrokeWidth(stroke);
        view.setEdit(edit);
        view.setOnCompleteAnEditListener(this);
        edit_layout.addView(view);
    }

    public void ChangeColor()
    {
        if (view != null) {
            Edit edit = view.getEdit();
            edit.setColor(edit_custon_color.getColor());
        }
    }

    @Override
    public void OnCompleteEdit()
    {
        listener.onAdding();
        listener.finishAdd();
        if (view != null) {
            view.fixed();
            stack.push(view);
            view = null;
        }
        CreateStroke();
    }

    public void ChangeStroke(int progress)
    {
        if (view != null) {
            Edit edit = view.getEdit();
            edit.setStrokeWidth(progress);
        }
        this.stroke = progress;
    }

    public void ChangeAlpha(int progress)
    {
        if (view != null) {
            Edit edit = view.getEdit();
            edit.setAlpha(progress);
        }
        this.alpha = progress;
    }


}
