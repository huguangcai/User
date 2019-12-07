package com.ysxsoft.common_base.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.core.content.ContentResolverCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * create by Sincerly on 2019/3/1 0001
 * {@link #checkCameraHardware(Context context)}    											    检测有没有相机
 * {@link #copy(Context context,String url)}    											        访问剪贴板
 **/
public class AndroidUtils {
    /**
     * 系统总内存
     */
    public static float getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader fileReader = new FileReader(str1);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
            str2 = bufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (float) (initial_memory / 1024);
    }

    /**
     * 检测设备有没有相机
     *
     * @param context
     * @return
     */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * 复制至剪贴板
     * @param url
     */
    public static void copy(Context context,String url){
        ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, url));
        ToastUtils.shortToast(context,"已复制!");
    }
}
