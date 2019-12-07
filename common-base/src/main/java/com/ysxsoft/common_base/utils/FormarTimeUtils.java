package com.ysxsoft.common_base.utils;

import java.text.SimpleDateFormat;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class FormarTimeUtils {
    /**
     * All, 年月日时分秒
     * Year,年
     * Year_Mouth,年月
     * Year_Mouth_Day 年月日
     */
    public enum AppTime {
        All,
        Year,
        Year_Mouth,
        Year_Mouth_Day,
        H_M_S
    }

    private static String format;

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String FormarTime(AppTime appTime, long time) {
        switch (appTime) {
            case All:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format = dateFormat.format(time);
                break;
            case Year:
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy");
                format = dateFormat1.format(time);
                break;
            case Year_Mouth:
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
                format = dateFormat2.format(time);
                break;
            case Year_Mouth_Day:
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
                format = dateFormat3.format(time);
                break;
            case H_M_S:
                SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH:mm:ss");
                format = dateFormat4.format(time);
                break;
        }
        return format;
    }
}
