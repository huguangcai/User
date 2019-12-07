package com.ysxsoft.common_base.view.custom.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.List;

/**
 * create by Sincerly on 2019/6/5 0005
 **/
public class CustomWheelView extends ViewGroup {
    private List<String> data;
    private int showNum=0;

    public CustomWheelView(Context context) {
        super(context);
    }

    public CustomWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
