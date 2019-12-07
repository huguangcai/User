package com.ysxsoft.common_base.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class EScrollView extends ScrollView {
    private OnScollChangedListener listener;
    public EScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScollChangedListener(OnScollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (listener != null) {
            listener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }



    public interface OnScollChangedListener {
        void onScrollChanged(EScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
