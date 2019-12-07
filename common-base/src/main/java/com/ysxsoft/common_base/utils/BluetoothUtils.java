package com.ysxsoft.common_base.utils;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;

/**
 * create by Sincerly on 2019/3/4 0004
 * 蓝牙工具类
 **/
public class BluetoothUtils {
    public static BluetoothUtils instance;

    public static BluetoothUtils getInstance() {
        synchronized (instance) {
            instance = new BluetoothUtils();
        }
        return instance;
    }

    public static BluetoothUtils init(Context context) {
        /**
         *
         */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        return instance;
    }

}

///////////////////////////////////////////////////////////////////////////
// tips
///////////////////////////////////////////////////////////////////////////
// 问题自测
// 1.检查是否拥有蓝牙权限