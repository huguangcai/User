package com.ysxsoft.common_base.base.frame.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;;

import android.view.View;

import com.ysxsoft.common_base.adapter.BaseViewHolder;

/**
 * create by Sincerly on 2019/5/9 0009
 **/
public interface IListAdapter<T>{


    /**
     * 列表itemLayoutId
     * @return
     */
    int getItemLayoutId();

    /**
     * 请求数据
     * @return
     */
    void request(int page);

    /**
     * 填充View
     */
    void fillView(BaseViewHolder helper, T t);

    /**
     * 多状态填充View
     */
    void fillMuteView(BaseViewHolder helper, T t);

    /**
     * 关联activity
     * @param activity
     */
    void attachActivity(AppCompatActivity activity);

    /**
     * 取消关联activity
     */
    void dettachActivity();

    /**
     * 设置布局管理器
     * @return
     */
    RecyclerView.LayoutManager getLayoutManager();

    /**
     * 是否多布局
     * @return
     */
    boolean isMuteAdapter();

    /**
     * 多布局类型
     * @return
     */
    int[] getMuteTypes();

    /**
     * 多布局item布局
     * @return
     */
    int[] getMuteLayouts();
}
