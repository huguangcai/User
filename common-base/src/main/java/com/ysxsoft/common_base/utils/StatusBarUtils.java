package com.ysxsoft.common_base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

/**
 * Created by Sincerly on 2017/8/24.
 * {@link #compat(Activity activity, int statusColor)} 										设置状态栏颜色
 * {@link #compat(Activity activity)} 														设置状态栏为默认颜色
 * {@link #getStatusBarHeight(Context context)} 											获取状态栏高度
 * {@link #miuiSetStatusBarLightMode(Window window, boolean dark)} 							设置小米状态栏图标颜色
 * {@link #meizuStatusBarLightMode(Window window, boolean dark)} 							修改魅族状态栏图标颜色
 * {@link #setStatusBarTranslucent(Activity activity)} 										设置状态栏透明
 * {@link #setViewHeightById(Activity activity, int viewId)} 								设置view高度为状态栏高度
 */

public class StatusBarUtils {
	private static final int INVALID_VAL = -1;
	private static final int COLOR_DEFAULT = Color.parseColor("#FFFFFF");

	public static void compat(Activity activity, int statusColor) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上  直接设置statusBarColor
				if (statusColor != INVALID_VAL) {
					activity.getWindow().setStatusBarColor(statusColor);
				}
				return;
			} else {
				int color = COLOR_DEFAULT;
				ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
				if (statusColor != INVALID_VAL) {
					color = statusColor;
				}
				View statusBarView = new View(activity);
				ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
				statusBarView.setBackgroundColor(color);
				contentView.addView(statusBarView, lp);
			}
		}
	}

	public static void compat(Activity activity) {
		compat(activity, INVALID_VAL);
	}

	/**
	 * 获取状态栏高度
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
	 * 修改小米状态栏图标颜色  miui6.0以上
	 *
	 * @param window
	 * @param dark   是否把状态栏字体及图标颜色设置为深色
	 * @return
	 */
	public static boolean miuiSetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (window == null) {
			return false;
		}
		Class clazz = window.getClass();
		int darkModeFlag = 0;
		Class layoutParams = null;
		try {
			layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			if (dark) {
				extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
			} else {
				extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
			}
			result = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改魅族状态栏图标颜色
	 *
	 * @param window
	 * @param dark   是否把状态栏字体及图标颜色设置为深色
	 * @return
	 */
	public static boolean meizuStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (window != null) {
			try {
				WindowManager.LayoutParams lp = window.getAttributes();
				Field darkFlag = WindowManager.LayoutParams.class
						.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
				Field meizuFlags = WindowManager.LayoutParams.class
						.getDeclaredField("meizuFlags");
				darkFlag.setAccessible(true);
				meizuFlags.setAccessible(true);
				int bit = darkFlag.getInt(null);
				int value = meizuFlags.getInt(lp);
				if (dark) {
					value |= bit;
				} else {
					value &= ~bit;
				}
				meizuFlags.setInt(lp, value);
				window.setAttributes(lp);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void setStatusBarTranslucent(Activity activity) {
		setLightStatusBar(activity,true);
	}

	public static void setLightStatusBar(Activity activity, boolean dark) {
		// 5.0 以上
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			View decorView = activity.getWindow().getDecorView();
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			activity.getWindow().addFlags(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//SYSTEM_UI_FLAG_LIGHT_STATUS_BAR深色字体   释放会导致无法截屏
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//SYSTEM_UI_FLAG_LIGHT_STATUS_BAR深色字体
		}
		if (dark) {
			int originFlag = activity.getWindow().getDecorView().getSystemUiVisibility();
			activity.getWindow().getDecorView().setSystemUiVisibility(originFlag | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
		} else {
			int originFlag = activity.getWindow().getDecorView().getSystemUiVisibility();
			activity.getWindow().getDecorView().setSystemUiVisibility(originFlag | View.SYSTEM_UI_FLAG_VISIBLE);//白色
		}
	}

	/**
	 * 设置view为状态栏高度
	 *
	 * @param activity
	 * @param viewId
	 */
	public static void setViewHeightById(Activity activity, int viewId) {
		StatusBarUtils.setStatusBarTranslucent(activity);
		View view = activity.findViewById(viewId);
		if (view != null) {
			view.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getDisplayWidth(activity), DisplayUtils.getStatusBarHeight(activity)));
		}
	}

	public static void initStatusBar(Activity activity,View v) {
		StatusBarUtils.setLightStatusBar(activity, false);
		ViewCompat.setOnApplyWindowInsetsListener(v, new OnApplyWindowInsetsListener() {
			@Override
			public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
				WindowInsetsCompat newInsets = windowInsetsCompat.replaceSystemWindowInsets(0, 0, 0, windowInsetsCompat.getSystemWindowInsetBottom());
				ViewCompat.onApplyWindowInsets(view, newInsets);
				return windowInsetsCompat.replaceSystemWindowInsets(0, newInsets.getSystemWindowInsetTop(), 0, 0);
			}
		});
	}
}