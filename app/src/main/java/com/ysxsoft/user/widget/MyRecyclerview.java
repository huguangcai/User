package com.ysxsoft.user.widget;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create By 胡
 * on 2019/12/13 0013
 */
public class MyRecyclerview extends RecyclerView {
    public MyRecyclerview(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
