package com.ysxsoft.common_base.jpush;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * create by Sincerly on 2019/2/22 0022
 **/
public class JpushUtils {
    public static void init(Context context) {
        JPushInterface.init(context);
        //JPushInterface.setChannel(context, BaseConfig.JPUSH_CHANNEL);
    }
}
