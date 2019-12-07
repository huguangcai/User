package com.ysxsoft.common_base.umeng.share;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.umeng.UMengUtil;

public class ShareUtil {

	public static void showShareWebDialog(Activity activity, UMShareListener shareListener, UMWeb web, SHARE_MEDIA... medias){
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo(true);
		UMShareAPI.get(UMengUtil.context).setShareConfig(config);
//		UMImage img = new UMImage(UMengUtil.context, R.drawable.umeng_socialize_back_icon);
//		UMWeb web = new UMWeb("https://fir.im/8acb");
//		web.setTitle("尤帅安卓版下载链接");//标题
//		web.setThumb(img);  //缩略图
//		web.setDescription("尤帅分享");//描述
		new ShareAction(activity).withMedia(web).setDisplayList(medias)
				.setCallback(shareListener).open();
	}

	public static void shareUrl(Activity activity,String title, UMWeb web, UMShareListener listener, SHARE_MEDIA platform){
		switch (platform){
			case QQ:
				if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
					//安装的有QQ
				} else {
					Toast.makeText(activity, "请安装QQ！", Toast.LENGTH_SHORT).show();
					return;
				}
				break;
			case WEIXIN:
				if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
					//安装的有微信
				} else {
					Toast.makeText(activity, "请安装微信！", Toast.LENGTH_SHORT).show();
					return;
				}
				break;
			case SINA:
				if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.SINA)) {
					//安装的有新浪
				} else {
					Toast.makeText(activity, "请安装新浪微博！", Toast.LENGTH_SHORT).show();
					return;
				}
				break;
		}
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo(true);
		UMShareAPI.get(UMengUtil.context).setShareConfig(config);
		web.setTitle(title);//标题
		web.setDescription("家分享");//描述
		new ShareAction(activity)
				.setPlatform(platform)//传入平台
				.withMedia(web)
				.setCallback(listener)//回调监听器
				.share();
	}

	public static void shareText(Activity activity, String text, UMShareListener listener, SHARE_MEDIA media){
		new ShareAction(activity).withText(text).setCallback(listener).setPlatform(media).share();
	}

	public static void shareTextWithIcon(Activity activity, String text, UMImage image, UMShareListener listener, SHARE_MEDIA media){
		new ShareAction(activity).withText(text).withMedia(image).setCallback(listener).setPlatform(media).share();
	}

	///////////////////////////////////////////////////////////////////////////
	// tips
	///////////////////////////////////////////////////////////////////////////
	//使用步骤
	// 1.在application初始化
//		UMengUtil.init(this, "5b7cd15ca40fa37cc0000010", "Umeng");
//		UMengUtil.initWX("wxa82b62d2a24eb369", "cbd9fe24c383cb12087a90172cc561e0");//微信appkey
//		UMengUtil.initQQ("1107708287", "uLbSkf2zrrHHtZ5R");//QQ appkey
	// 2.在需要调用的地方调 ShareUtil

	//3.分享时需要配置回调和ShareListener
//		@Override
//		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//			super.onActivityResult(requestCode, resultCode, data);
//			UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//		}
	//4.修改本Module中AndroidManifest.xml中的qq的appid  例如tencent100424468

}
