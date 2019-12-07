package com.ysxsoft.common_base.view.widgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
	private boolean enableFixScroll = true;

	public NoScrollViewPager(@NonNull Context context) {
		super(context);
	}

	public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void setEnableFixScroll(boolean canScroll) {
		this.enableFixScroll = canScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item, false);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (enableFixScroll)
			return false;
		else
			return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (enableFixScroll)
			return false;
		else
			return super.onTouchEvent(ev);
	}
}
