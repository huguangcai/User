package com.ysxsoft.common_base.helper.recyclerview;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速滑动
 */
public class FastSpeedLinearLayoutManager extends LinearLayoutManager {
    private double speedRatio = 1.5f;
    private Map<Integer, Integer> heightMap = new HashMap<>();

    public FastSpeedLinearLayoutManager(Context context) {
        super(context);
    }

    public FastSpeedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FastSpeedLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            heightMap.put(i, view.getHeight());
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int a = super.scrollHorizontallyBy((int) (speedRatio * dx), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
        if (a == (int) (speedRatio * dx)) {
            return dx;
        }
        return a;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int a = super.scrollVerticallyBy((int) (speedRatio * dy), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
        if (a == (int) (speedRatio * dy)) {
            return dy;
        }
        return a;
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        try {
            int firstVisiablePosition = findFirstVisibleItemPosition();
            View firstVisiableView = findViewByPosition(firstVisiablePosition);
            int offsetY = -(int) (firstVisiableView.getY());
            for (int i = 0; i < firstVisiablePosition; i++) {
                offsetY += heightMap.get(i) == null ? 0 : heightMap.get(i);
            }
            return offsetY;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setSpeedRatio(double speedRatio) {
        this.speedRatio = speedRatio;
    }
}
