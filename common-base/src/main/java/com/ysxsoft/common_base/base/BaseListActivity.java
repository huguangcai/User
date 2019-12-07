package com.ysxsoft.common_base.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.config.BaseConfig;
import com.ysxsoft.common_base.helper.recyclerview.FastSpeedGridLayoutManager;
import com.ysxsoft.common_base.helper.recyclerview.FastSpeedLinearLayoutManager;
import com.ysxsoft.common_base.utils.LogUtils;
import com.ysxsoft.common_base.view.custom.refresh.ELoadMoreView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by Sincerly on 2018/12/28 0028
 **/
public abstract class BaseListActivity<T> extends BaseActivity {

    public RecyclerView recyclerView;
    public MultipleStatusView multipleStatusView;
    public SmartRefreshLayout smartRefresh;
    LinearLayout backLayout;//返回布局
    FrameLayout bottomCustomLayout;//底部自定义布局
    TextView backWithText;//标题带文本返回
    TextView title;//中间标题
    TextView rightWithIcon;//右边带文本图片
    ImageView back;//右边带文本图片
    private List<T> data;
    private int page = 1;
    private int lastPage = 1;
    protected BaseQuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void doWork() {
//        super.doWork(); TODO:基类调用initRefresh在dowork方法里边 所以这个位置需要重新调一下initRefresh
        page=1;
        lastPage=1;
        initView();
        initTitle(back,backLayout,title,backWithText,rightWithIcon,bottomCustomLayout);
        setAdapter();
        initRefresh();
        if(!enableSmartRefresh()){
            smartRefresh.setEnableRefresh(false);
        }else{
            smartRefresh.setEnableRefresh(true);
        }
        if(isDefaultRequest()){
            if(enableSmartRefresh()){
                smartRefresh.autoRefresh();
            }else{
                getList();
            }
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        multipleStatusView = findViewById(R.id.multipleStatusView);
        smartRefresh = findViewById(R.id.smartRefresh);
        backLayout=findViewById(R.id.backLayout);
        back=findViewById(R.id.back);
        backWithText=findViewById(R.id.backWithText);
        title=findViewById(R.id.title);
        rightWithIcon=findViewById(R.id.rightWithIcon);
        bottomCustomLayout=findViewById(R.id.bottomCustomLayout);
        bottomCustomLayout.setVisibility(View.GONE);//默认隐藏底部评论布局
    }

    private void setAdapter() {
        if (isLinearLayoutManager()) {
            recyclerView.setLayoutManager(new FastSpeedLinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new FastSpeedGridLayoutManager(this, getSpanCount()));
            recyclerView.setLayoutManager(new FastSpeedGridLayoutManager(this, getSpanCount()));
        }
        adapter = new CustomAdapter(getChildView());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T t = (T) adapter.getItem(position);
                onListItemClick(position, t);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("当前页数："+page+" 最后一页:"+lastPage);
                if (page < lastPage) {
                    page++;
                    getList();
                } else {
                    if (adapter != null) {
                        if(adapter.isLoadMoreEnable()){
                            adapter.loadMoreEnd(true);
                        }
                    }
                }
            }
        }, recyclerView);
        adapter.setLoadMoreView(new ELoadMoreView());
        adapter.setEnableLoadMore(false);
        if(hasEmptyView()){
            adapter.setEmptyView(createEmptyView());
        }
        if(hasHeaderView()){
            adapter.setHeaderView(createHeaderView());
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initRefresh(RefreshLayout re) {
        super.initRefresh(re);
        if (re != null) {
            if (BaseConfig.DEBUG) {
                //测试数据  2s自动关闭下拉刷新
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smartRefresh.finishRefresh(true);
                    }
                }, 2000);
            } else {
                page = 1;
                getList();
            }
        }
    }

    @Override
    public View createEmptyView() {
        View view = super.createEmptyView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                getList();
            }
        });
        return view;
    }

    /**
     * 是否拥有headerView
     * @return
     */
    public boolean hasHeaderView(){
        //有header 子类需要重写返回true
        return false;
    }

    public boolean hasEmptyView(){
        return true;
    }

    public View createHeaderView(){
//        创建一个headerView
        return null;
    }

    /**
     * 释放recyclerView
     */
    protected void releaseRecyclerView() {
        if (smartRefresh != null) {
            smartRefresh.finishRefresh(true);
        }
        if (adapter != null) {
            try {
//                if(adapter.isLoadMoreEnable()){
//                    Log.e("tag","隐藏loadmore");
//                    adapter.loadMoreEnd(true);
//                }
                adapter.loadMoreComplete();//刷新完成
                adapter.disableLoadMoreIfNotFullPage(recyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean enableSmartRefresh(){
        return true;
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    protected int getPage() {
        return page;
    }

    protected void setPage(int p){
        this.page=p;
    }

    protected void setLastPage(int lastPage){
        this.lastPage=lastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public boolean isDefaultRequest(){
        //默认进来请求数据
        return true;
    }
    /**
     * 设置列表
     *
     * @return
     */
    private int getSpanCount() {
        return 2;
    }

    private class CustomAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        public CustomAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            fillItemView(helper, item);
        }
    }

    private boolean isLinearLayoutManager() {
        return true;
    }

    protected abstract int getChildView();

    protected abstract void fillItemView(BaseViewHolder helper, T item);

    protected abstract void onListItemClick(int position, T item);

    protected abstract void getList();

    protected abstract void initTitle(ImageView back,LinearLayout backLayout,TextView title,TextView backWithText,TextView rightWithIcon,FrameLayout bottomCustomLayout);
}
