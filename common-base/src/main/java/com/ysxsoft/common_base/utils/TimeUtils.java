package com.ysxsoft.common_base.utils;

public class TimeUtils {
    public static void main(String[] args){
        System.out.println(getGenreByYear(2019));
    }

    /**
     * 根据年限获取属相
     * @param year
     * @return
     */
    public static String getGenreByYear(int year) {
        String sx = null;
        switch (year % 12) {
            case 4:
                sx = "鼠";
                break;
            case 5:
                sx = "牛";
                break;
            case 6:
                sx = "虎";
                break;
            case 7:
                sx = "兔";
                break;
            case 8:
                sx = "龙";
                break;
            case 9:
                sx = "蛇";
                break;
            case 10:
                sx = "马";
                break;
            case 11:
                sx = "羊";
                break;
            case 0:
                sx = "猴";
                break;
            case 1:
                sx = "鸡";
                break;
            case 2:
                sx = "狗";
                break;
            case 3:
                sx = "猪";
                break;
        }
        return sx;
    }

    public static String formattedTime(long second) {
        String hs, ms, ss, formatTime;
        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (h > 0) {
            formatTime = hs + ":" + ms + ":" + ss;
        } else {
            formatTime = ms + ":" + ss;
        }
        return formatTime;
    }


}
