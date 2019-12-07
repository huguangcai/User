package com.ysxsoft.common_base.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class EHorizontalScrollView extends HorizontalScrollView {
    private OnScollChangedListener listener;
    public EHorizontalScrollView(Context context, AttributeSet attrs) {
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
        void onScrollChanged(EHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
