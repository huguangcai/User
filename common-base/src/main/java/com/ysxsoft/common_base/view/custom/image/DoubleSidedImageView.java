package com.ysxsoft.common_base.view.custom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * create by Sincerly on 2019/1/14 0014
 **/
public class DoubleSidedImageView  extends AppCompatImageView {
    public DoubleSidedImageView(Context context) {
        super(context);
    }

    public DoubleSidedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleSidedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
