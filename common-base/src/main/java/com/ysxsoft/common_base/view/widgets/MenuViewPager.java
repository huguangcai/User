package com.ysxsoft.common_base.view.widgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/3/30 0030
 *
 * @author Sincerly
 * {@link #setEnableFixScroll(boolean canScroll)  }//设置是否可以左右滑动
 **/
public class MenuViewPager extends ViewPager {
    private List<Fragment> fragments = new ArrayList<>();
    /**
     * 是否启用水平滑动
     */
    private boolean enableFixScroll = false;

    public MenuViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MenuViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    public void setEnableFixScroll(boolean canScroll) {
        this.enableFixScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (enableFixScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (enableFixScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void initAdapter(FragmentManager fragmentManager,List<Fragment> fragments) {
        if (fragments == null) {
            return;
        }
        initAdapter(fragmentManager,fragments, new ArrayList<String>());
    }

    public void initAdapter(FragmentManager fragmentManager, List<Fragment> fragments, ArrayList<String> titles) {
        if (fragments == null) {
            return;
        }
        if (titles == null) {
            titles = new ArrayList<>(fragments.size());
        }
        setAdapter(new ViewPagerFragmentAdapter(fragmentManager, fragments, titles));
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(listener);
    }
}
