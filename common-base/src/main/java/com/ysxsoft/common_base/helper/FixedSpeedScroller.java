package com.ysxsoft.common_base.helper;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * create by Sincerly on 2019/1/8 0008
 **/
public class FixedSpeedScroller extends Scroller {
    public FixedSpeedScroller(Context context) {
        super(context);
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    private int mDuration=1500;
}
