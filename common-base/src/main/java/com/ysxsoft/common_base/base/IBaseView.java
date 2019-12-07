package com.ysxsoft.common_base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;

public interface IBaseView {
	/**
	 * onCreate前边调用
	 */
	void preCreate();

	/**
	 * setContentView前边调用 用来设置全屏等
	 */
	void preInlate();

	/**
	 * 主方法  *
	 */
	void doWork();

	/**
	 * 跳转activity
	 */
	void goToActivity(Class clazz);

	/**
	 * 跳转activity
	 */
	void goToActivity(String arouterPath);

	/**
	 * 跳转activity
	 */
	void goToActivityTransation(Class clazz);

	/**
	 * 跳转activity
	 *
	 * @param context
	 */
	void start(Context context);

	/**
	 * 跳转activity
	 *
	 * @param context
	 * @param params  携带数据
	 */
	void start(Context context, Bundle params);

	/**
	 * 跳转activity 携带map
	 *
	 * @param context
	 * @param params  携带数据
	 */
	void start(Context context, String key, HashMap params);

	/**
	 * 跳转activity 携带String
	 *
	 * @param context
	 * @param params  携带数据
	 */
	void start(Context context, String key, String params);

	/**
	 * 返回Activity
	 */
	void backToActivity();

	/**
	 * 返回Activity 默认返回code
	 */
	void backToResult(Intent intent);

	/**
	 * 返回Activity 返回自定义code
	 */
	void backToResult(Intent intent, int responseCode);

	/**
	 * loading
	 */
	void showLoading();

	/**
	 * loading dialog
	 */
	void showLoadingDialog();

	/**
	 * loading dialog with tips
	 */
	void showLoadingDialog(String tips);

	/**
	 * hide the dialog
	 */
	void hideLoadingDialog();

	/**
	 * empty
	 */
	void showEmpty();

	/**
	 * empty
	 */
	void showEmpty(View.OnClickListener retryListener);

	/**
	 * 没网络
	 */
	void showNoNet();

	/**
	 * 网络出错
	 */
	void showNetError();

	/**
	 * 显示Toast
	 * @param str
	 */
	void showToast(String str);

	/**
	 * 是否设置状态栏
	 * @return
	 */
	boolean getStatusHeight();

	/**
	 * 是否设置状态栏透明
	 * @return
	 */
	boolean isTransparentBarStatus();

	/**
	 * 刷新
	 * @return
	 */
	void initRefresh();

	/**
	 * 初始化刷新
	 * @return
	 */
	void initRefresh(RefreshLayout refreshlayout);

	/**
	 * 弹出框消失
	 */
	void onLoadingDialogDismiss();

	/**
	 * 显示activity进入动画
	 */
	void showEnterTransition();

	/**
	 * 是否开启跳转动画
	 * @return
	 */
	boolean isShowTransition();

	/**
	 * 显示activity退出动画
	 */
	void showEixtTransition();

	/**
	 * activity进入动画
	 */
	int[] getEnterTransitionAnimResource();

	/**
	 * activity退出动画
	 */
	int[] getExitTransitionAnimResource();
}
