package com.ysxsoft.common_base.view.custom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;

/**
 * create by Sincerly on 2019/1/8 0008
 **/
public class SpeedViewPager extends ViewPager {
    private float radio=1.1f;
    public SpeedViewPager(@NonNull Context context) {
        super(context);
    }

    public SpeedViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
