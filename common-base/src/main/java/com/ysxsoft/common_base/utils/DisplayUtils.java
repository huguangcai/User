package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Sincerly on 2018/5/24 0024.
 * 单位工具类
 * {@link #getStatusBarHeight(Context context)} 											获取状态栏高度
 * {@link #getNavigationBarHeight(Context context)} 										获取底部栏高度
 * {@link #getDensityDpi(Context context)} 												    获取设备像素密度
 * {@link #getDisplayWidth(Context context)} 												获取设备宽度
 * {@link #getDisplayHeight(Context context)} 												获取设备高度
 * {@link #dip2px(Context context, float dpValue)} 											dp转化为px
 * {@link #sp2px(Context context, float spValue)} 											sp转化为px
 * {@link #px2dip(Context context, float pxValue)} 											px转化为dp
 * {@link #dp2px(Context context, float dpValue)} 											dp转化为px
 * {@link #px2dp(Context context, float pxValue)} 											px转化为dp
 * {@link #px2sp(Context context, float pxValue)} 											px转化为sp
 */

public class DisplayUtils {
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 0;
		int resouceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		statusBarHeight = context.getResources().getDimensionPixelSize(resouceId);
		return statusBarHeight;
	}

	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}

	public static int getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	public static int getDisplayWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getDisplayHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * dp转px
	 *
	 * @param context
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(Context context, float dpVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpVal * scale + 0.5f);
	}

	/**
	 * sp转px
	 *
	 * @param context
	 * @param spVal
	 * @return
	 */
	public static int sp2px(Context context, float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 *
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 *
	 * @param pxVal
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context, float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}
}
