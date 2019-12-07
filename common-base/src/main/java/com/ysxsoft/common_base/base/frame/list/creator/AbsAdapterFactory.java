package com.ysxsoft.common_base.base.frame.list.creator;

import android.app.Activity;

import com.ysxsoft.common_base.base.frame.list.IListAdapter;


/**
 * create by Sincerly on 2019/5/9 0009
 **/
public abstract class AbsAdapterFactory{
    public abstract <T> T createAdapter(String key);
}
