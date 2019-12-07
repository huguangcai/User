package com.ysxsoft.common_base.view.widgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MatchHeightViewPager extends ViewPager {
	private boolean enableFixScroll = true;

	public MatchHeightViewPager(@NonNull Context context) {
		super(context);
	}

	public MatchHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int h=0;
		for (int i = 0; i <getChildCount() ; i++) {
			View child=getChildAt(i);
			child.measure(widthMeasureSpec,heightMeasureSpec);
			if(h>child.getMeasuredHeight()){

			}
		}
	}
}
