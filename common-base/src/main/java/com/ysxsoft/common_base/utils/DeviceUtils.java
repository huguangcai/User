package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class DeviceUtils {
    private DeviceUtils() {
    }

    /**
     * 获取屏幕宽高度
     *
     * @param context
     * @param isWidth 是否获取屏幕宽度true为是，false为获取高度
     * @return
     */
    public static int getScreenWidthAndHeight(Context context, boolean isWidth) {
        DisplayMetrics metrics = context.getResources()
                .getDisplayMetrics();
        return isWidth ? metrics.widthPixels : metrics.heightPixels;
    }

    /**
     * @param context
     * @param isWidth
     * @return
     */
    public static int getNormalDialogWidthOrHeight(Context context, boolean isWidth) {
        if (isWidth) {
            return getScreenWidthAndHeight(context, isWidth) * 4 / 5;
        }
        return getScreenWidthAndHeight(context, isWidth) / 2;
    }

    /**
     * 获取设备状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取app版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo pi;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 获取配置渠道号
     *
     * @return
     */
    public static String getChannel(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            return String.valueOf(appInfo.metaData.get("UMENG_CHANNEL"));
        }
        return null;
    }

    /**
     * 获取umeng APPKEY
     *
     * @param context
     * @return
     */
    public static String getUmengAppKey(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            return String.valueOf(appInfo.metaData.get("UMENG_APPKEY"));
        }
        return null;
    }

    /**
     * 获取umeng SECRET
     *
     * @param context
     * @return
     */
    public static String getUmengAppSecret(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            return String.valueOf(appInfo.metaData.get("UMENG_MESSAGE_SECRET"));
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取手机IMEI号
     *
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取手机IMSI号
     *
     * @param context
     * @return
     */
    public static String getPhoneIMSI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取制造商
     *
     * @return
     */
    public static String getVendor() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取产品型号
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取当前默认系统使用语言类型
     *
     * @return
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统版本
     *
     * @return
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }
}
