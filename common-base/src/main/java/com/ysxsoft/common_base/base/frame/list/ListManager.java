package com.ysxsoft.common_base.base.frame.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.ListAdapter;

import com.bigkoo.pickerview.lib.WheelView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.adapter.BaseMultiItemQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.adapter.entity.MultiItemEntity;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/5/9 0009
 **/
public class ListManager<T> {
    private int page = 1;
    private SmartRefreshLayout smartRefresh;
    private MultipleStatusView multipleStatusView;
    private RecyclerView recyclerView;
    private IListAdapter view;//V层
    private int a;
    private BaseQuickAdapter adapter;

    public ListManager(IListAdapter view) {
        this.view = view;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void init(View v) {
        this.smartRefresh = v.findViewById(R.id.smartRefresh);
        this.multipleStatusView = v.findViewById(R.id.multipleStatusView);
        this.recyclerView = v.findViewById(R.id.recyclerView);
        if (smartRefresh != null) {
            smartRefresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    resetPage();
                    view.request(getCurrentPage());
                }
            });
            smartRefresh.setEnableLoadMore(false);
//            smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                    //if(smartRefresh.getState()!=RefreshState.Loading){
//                        //上拉加载完成才进行请求
//                        nextPage();
//                        view.request(getCurrentPage());
//                   // }
//                }
//            });
//            smartRefresh.setEnableLoadMore(true);
        }
        if (recyclerView != null) {
            recyclerView.setLayoutManager(view.getLayoutManager());
            if (view.isMuteAdapter()) {
                //多状态布局
                adapter = new BaseMuteListAdapter(new ArrayList());
            } else {
                adapter = new BaseListAdapter(view.getItemLayoutId());
            }
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    nextPage();
                    view.request(getCurrentPage());
                }
            },recyclerView);
            adapter.setEnableLoadMore(false);
            recyclerView.setAdapter(adapter);
        }
    }

    public SmartRefreshLayout getSmartRefresh() {
        return smartRefresh;
    }

    public void autoRefresh(){
        if(smartRefresh==null){
            return;
        }
        smartRefresh.setEnableLoadMore(false);
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void setData(List<T> data) {
        if(data==null){
            data = new ArrayList<>();
        }
        if (getCurrentPage() == 1) {
            adapter.setNewData(data);
        } else {
            adapter.addData(data);
        }
        if (data.isEmpty()) {
            if (getCurrentPage() == 1) {
                showEmpty();
                return;
            }
        }else{
            showContent();
        }
        if(data.isEmpty()){
            adapter.loadMoreEnd(true);
        }else{
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        }
    }

    public void resetPage() {
        page = 1;
    }

    public int getCurrentPage() {
        return page;
    }

    public int nextPage() {
        page++;
        return page;
    }

    public void showEmpty() {
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showLoading() {
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideLoading(){
        if (multipleStatusView != null) {
            try {
                multipleStatusView.hideLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class BaseListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        public BaseListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (view != null) {
                view.fillView(helper, item);
            }
        }
    }

    /**
     * 列表多状态布局
     *
     * @param <T>
     */
    private class BaseMuteListAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public BaseMuteListAdapter(List<T> data) {
            super(data);
            int[] type = view.getMuteTypes();
            int[] muteLayout = view.getMuteLayouts();
            if (type.length != muteLayout.length) {
                throw new IllegalArgumentException("Please confirm that the parameter type and layout number are the same.");
            }
            for (int i = 0; i < type.length; i++) {
                addItemType(type[i], muteLayout[i]);
            }
        }

        @Override
        protected int getDefItemViewType(int position) {
            //数据源getItemType 方法决定布局类型
            List<T> data = getData();
            if (data != null && data.size() > position) {
                T t = data.get(position);
                return ((MultiItemEntity) t).getItemType();
            }
            return super.getDefItemViewType(position);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (view != null) {
                view.fillMuteView(helper, item);
            }
        }
    }

    public void showContent() {
        if (multipleStatusView != null) {
            try {
                multipleStatusView.hideEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放下拉刷新
     */
    public void releaseRefresh() {
        if (smartRefresh != null) {
            smartRefresh.finishRefresh();
            smartRefresh.finishLoadMore();
        }
        if(adapter!=null){
            adapter.loadMoreComplete();
        }
    }
}
