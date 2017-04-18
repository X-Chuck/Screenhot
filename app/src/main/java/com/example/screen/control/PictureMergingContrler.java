package com.example.screen.control;

import java.util.Stack;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.screen.tool.EditConfig;
import com.example.screen.tool.ImageMergingTool;
import com.example.screen.tool.MyConfig;

public class PictureMergingContrler {
    public static final int WHAT = 0x6358;
    Stack stack;
    private Stack<View> queue;
    Bitmap editbitmap;
    String picture;
    Handler editHandler;
    Handler memuHandler;

    public PictureMergingContrler()
    {
        queue = new Stack();
    }

    public void setStack(Stack stack)
    {
        this.stack = stack;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public void setHandler(Handler editHandler, Handler memuHandler)
    {
        this.editHandler = editHandler;
        this.memuHandler = memuHandler;
    }

    public void Merge()
    {
        mergeThread.start();
    }

    private Thread mergeThread = new Thread() {
        public void run()
        {
            if (stack == null || stack.empty() || picture == null) {
                if (editHandler != null) {
                    Message mesg = new Message();
                    mesg.what = WHAT;
                    editHandler.handleMessage(mesg);
                }
                if (memuHandler != null) {
                    Message mesg = new Message();
                    mesg.what = WHAT;
                    memuHandler.handleMessage(mesg);
                }
                return;
            }
            while (!stack.empty()) {
                View view = (View) stack.pop();
//				Bitmap bitmap = ImageMergingTool.convertViewToBitmap(view);
                queue.push(view);
            }
            if (!queue.isEmpty()) {
                Bitmap bitmap = ImageMergingTool.convertViewToBitmap(queue.pop());
                editbitmap = bitmap;
            }
            Bitmap bitmap = null;
//			if(!queue.isEmpty()){
//				bitmap = queue.pop();
//			}
            while (!queue.isEmpty()) {
                bitmap = ImageMergingTool.convertViewToBitmap(queue.pop());
                editbitmap = ImageMergingTool.mergeBitmap(editbitmap, bitmap);
                bitmap.recycle();
            }
            if (editbitmap != null) {
                Bitmap background = BitmapFactory.decodeFile(picture);
                Bitmap mergeBitmap = ImageMergingTool.HandleBitmap(background, editbitmap);
                Bitmap newBitmap = ImageMergingTool.BitmapScalling(background, mergeBitmap);
                Bitmap result = ImageMergingTool.mergeBitmap(background, newBitmap);
                picture = getPath();
                ImageMergingTool.SaveBitmapToPicture(result, picture);
                EditConfig config = EditConfig.GetInstance();
                config.Pic_path = picture;
            }

            if (editHandler != null) {
                Message mesg = new Message();
                mesg.what = WHAT;
                editHandler.handleMessage(mesg);
            }

            if (memuHandler != null) {
                Message mesg = new Message();
                mesg.what = WHAT;
                memuHandler.handleMessage(mesg);
            }

        }

        ;
    };

    public String getPath()
    {
        MyConfig config = MyConfig.GetInstance();
        UUID id = UUID.randomUUID();
        String name = id.toString() + ".jpg";
        String path = config.SavePicturePath + "/" + name;
        return path;
    }

}
