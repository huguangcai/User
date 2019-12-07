package com.ysxsoft.common_base.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ysxsoft.common_base.R;

/**
 * 选择商品View
 */
public class ChooseProductView extends LinearLayout {
    private Context context;
    public ChooseProductView(Context context) {
        this(context,null);
    }

    public ChooseProductView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChooseProductView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        removeAllViews();
        View view=View.inflate(context, R.layout.view_choose_product,null);

        addView(view);
    }

    public static class ProductMenu{
        private String id;
        private String name;
        private boolean isSelected;
        private int carNum;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getCarNum() {
            return carNum;
        }

        public void setCarNum(int carNum) {
            this.carNum = carNum;
        }
    }
}
