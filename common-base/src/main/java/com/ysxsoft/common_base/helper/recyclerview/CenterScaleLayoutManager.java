package com.ysxsoft.common_base.helper.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * create by Sincerly on 2018/12/11 0011
 * 中间缩放 横放
 **/
public class CenterScaleLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }


}
