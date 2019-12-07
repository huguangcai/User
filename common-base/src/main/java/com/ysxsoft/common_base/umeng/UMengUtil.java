package com.ysxsoft.common_base.umeng;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

//import com.umeng.analytics.MobclickAgent;

public class UMengUtil {
	public static Context context;
	public static boolean showlog;

	public static void init(Context c, String appkey, String name) {
		context = c;
		UMConfigure.init(c, appkey, name, UMConfigure.DEVICE_TYPE_PHONE, "");
		UMConfigure.setLogEnabled(true);
		UMShareAPI.get(c).setShareConfig(getDefaultShareConfig());
	}

	public static UMShareConfig getDefaultShareConfig() {
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo(true);
		return config;
	}

	public static void initQQ(String appId, String appkey) {
		PlatformConfig.setQQZone(appId, appkey);//QQ appkey
	}

	public static void initSina(String appId, String appkey,String redirectUrl) {
		PlatformConfig.setSinaWeibo(appId, appkey,redirectUrl);//QQ appkey
	}

	public static void initWX(String appId, String appkey) {
		PlatformConfig.setWeixin(appId, appkey);//微信appkey
	}

	public static void showUmengLog(boolean s){
		showlog=s;
	}

	///////////////////////////////////////////////////////////////////////////
	// 统计
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 开启统计
	 *
	 * 在UMengUtil.init后调用
	 */
	public static void enableMobclickAgent(String secretkey){
//		MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
//		MobclickAgent.setSecret(context,secretkey);
//		MobclickAgent.setCatchUncaughtExceptions(true);//捕获异常
	}

	/**
	 * 登录QQ
	 */
	public static void loginQQ(){

	}

	/**
	 * 登录微信
	 */
	public static void loginWX(){

	}

	/**
	 * 登录新浪
	 */
	public static void loginSina(){

	}

	/**
	 * 退出
	 */
	public static void logout(){

	}
}
