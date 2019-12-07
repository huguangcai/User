package com.ysxsoft.common_base.base.frame.list.creator;

import android.app.Activity;

import com.ysxsoft.common_base.base.frame.list.IListAdapter;


/**
 * create by Sincerly on 2019/5/9 0009
 **/
public class AdapterFactory extends AbsAdapterFactory {
//    @Override
//    public IListAdapter createAdapter(Activity activity, String key) {
//        switch (key) {
//            case "test":
//                return new TestAdapter(activity);
//            default:
//                break;
//        }
//        return null;
//    }

    @Override
    public <T> T createAdapter(String key) {
        return null;
    }
}
