package com.ysxsoft.user.config;

import android.os.Environment;

/**
 * 应用配置信息
 */
public class AppConfig {
    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/ysxsoft/user";
    public static final String PHOTO_CACHE_PATH = BASE_PATH + "/image/cache";
    public static final String PHOTO_PATH = BASE_PATH + "/image/";
    public static final String VIDEO_PATH = BASE_PATH + "/video/";
    public static final String MUSIC_PATH = BASE_PATH + "/music/";
    public static final String APK_URL = BASE_PATH + "/apk/";
    public static final String BASE_URL = "http://192.168.1.112:8080";
//    public static final String BASE_H5_URL = "http://192.168.1.236:13140/#/";
    public static final boolean IS_DEBUG_ENABLED = true;
    public static AppConfig INSTANCE;

    public static synchronized AppConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppConfig();
        }
        return INSTANCE;
    }
}
