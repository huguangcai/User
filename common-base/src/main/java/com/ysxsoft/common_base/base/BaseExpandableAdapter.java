package com.ysxsoft.common_base.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

public abstract class BaseExpandableAdapter<T> extends BaseExpandableListAdapter {
    private List<T> list;
    private Context context;
    private int childLayout;
    private int parentLayout;

    /**
     * 构造一个适配器
     * @param context      上下文
     * @param childLayout  二级布局
     * @param parentLayout 一级布局
     * @param list         数据源
     */
    public BaseExpandableAdapter(Context context, int childLayout, int parentLayout,List<T> list) {
        this.context = context;
        this.childLayout = childLayout;
        this.parentLayout = parentLayout;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        T d=getGroup(groupPosition);
        if(d!=null){
            count=getChildrenCount(d,groupPosition);
        }
        return count;
    }

    @Override
    public T getGroup(int groupPosition) {
        T d=null;
        if (list != null &&list.size()>groupPosition&& list.get(groupPosition) != null) {
            d=list.get(groupPosition);
        }
        return d;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        T d=null;
        if (list != null &&list.size()>groupPosition&& list.get(groupPosition) != null) {
            d=list.get(groupPosition);
        }
        return getChild(d,childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, parentLayout, null);
        fillGroup(convertView,isExpanded,getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, childLayout, null);
        fillChild(convertView,getGroup(groupPosition),childPosition);
        convertView.setOnClickListener(new ChildClick(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public abstract int getChildrenCount(T data,int groupPosition);
    public abstract Object getChild(T d, int childPosition);
    public abstract void onChildClick(BaseExpandableAdapter adapter,T data,int childPosition);
    public abstract void fillGroup(View convertView,boolean isExpanded,T data);
    public abstract void fillChild(View convertView,T groupData,int childPositon);

    /**
     * 子View点击
     */
    public class ChildClick implements View.OnClickListener {
        private int groupPosition, childPosition;

        public ChildClick(int groupP, int childP) {
            this.groupPosition = groupP;
            this.childPosition = childP;
        }

        @Override
        public void onClick(View v) {
            T d = getGroup(groupPosition);
            if (d != null) {
                onChildClick(BaseExpandableAdapter.this,d, childPosition);
            }
        }
    }
}