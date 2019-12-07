package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.graphics.Rect;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;

import com.ysxsoft.common_base.helper.recyclerview.FastSpeedLinearLayoutManager;

/**
 * Created by Administrator on 2017/8/22.
 */

public class RecyclerViewUtils {
    /**
     * 水平没线
     * @param recyclerView
     * @param context
     * @return
     */
    public static LinearLayoutManager initHLayoutManager(RecyclerView recyclerView, Context context){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        FastSpeedLinearLayoutManager layoutManager=new FastSpeedLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        return layoutManager;
    }

    /**
     * 水平有线
     * @param recyclerView
     * @param context
     * @return
     */
    public static LinearLayoutManager initHLayoutManagerWithDivider(RecyclerView recyclerView, Context context){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        FastSpeedLinearLayoutManager layoutManager=new FastSpeedLinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL));
        return layoutManager;
    }

    /**
     * 垂直没有水平线
     * @param recyclerView
     * @param context
     * @return
     */
    public static LinearLayoutManager initVLayoutManager(RecyclerView recyclerView, Context context){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        FastSpeedLinearLayoutManager layoutManager=new FastSpeedLinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return layoutManager;
    }

    /**
     * 垂直且有水平线
     * @param recyclerView
     * @param context
     * @return
     */
    public static LinearLayoutManager initVLayoutManagerWithDivider(RecyclerView recyclerView, Context context){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        FastSpeedLinearLayoutManager layoutManager=new FastSpeedLinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        return layoutManager;
    }

    /**
     * 垂直且有水平线
     * @param recyclerView
     * @param context
     * @return
     */
    public static LinearLayoutManager addHeaderView(RecyclerView recyclerView, Context context){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        FastSpeedLinearLayoutManager layoutManager=new FastSpeedLinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        return layoutManager;
    }

    /**
     * 瀑布流 垂直
     * @param recyclerView
     * @param spanCount  列数
     * @return
     */
    public static StaggeredGridLayoutManager initVStaggeredLayoutManager(RecyclerView recyclerView, int spanCount){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        return layoutManager;
    }

    /**
     * 瀑布流  水平
     * @param recyclerView
     * @param spanCount  行数
     * @return
     */
    public static StaggeredGridLayoutManager initHStaggeredLayoutManager(RecyclerView recyclerView, int spanCount){
        if(recyclerView==null){
            throw new NullPointerException("The recyclerview cannot null");
        }
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        return layoutManager;
    }


    static class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private int space=0;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param recyclerView
     * @param n  要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, RecyclerView recyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            recyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(n);
        }
    }
}
