package com.ysxsoft.common_base.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2018/3/15 0015.
 */

public abstract class AbsJPushReceiver extends BroadcastReceiver {
//	private static final String TAG = "JPushMessage";
//	private NotificationManager nm;
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		Bundle bundle = intent.getExtras();
//		Log.e(TAG, "onReceive - " + intent.getAction() + ", extras: " + bundle);
//		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//			Log.e(TAG, "JPush用户注册成功");
//		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//			Log.e(TAG, "接受到推送下来的自定义消息");
//			String type = processCustomMessage(context, bundle);
//			doAction(context, type);
//		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//			Log.e(TAG, "接受到推送下来的通知");
////			receivingNotification(context, bundle);
//		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//			Log.d(TAG, "用户点击打开了通知");
//			openNotification(context, bundle);
//		} else {
//			Log.d(TAG, "Unhandled intent - " + intent.getAction());
//		}
//	}
//
//	abstract void regJpushSuccess();
//	abstract void receivedCustomMessage();//接收到自定义消息
//	abstract void receivedNotification();//接收到通知
//
//	private void openNotification(Context context, Bundle bundle) {
////		String type = processCustomMessage(context, bundle);
////		switch (type) {
////			case "1":
////				break;
////			case "2":	//系统消息
////				break;
////			case "3"://添加好友申请
////				break;
////			case "4"://4 接受申请好友
////				break;
////			case "5"://"txt":"115"   ID 评论
////				break;
////		}
//
//
////        Intent intent = new Intent(context, MainActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
//	}
//
//	private String processCustomMessage(Context context, Bundle bundle) {
//		if(bundle==null){
//			return "";
//		}
//		String result = "";
//		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		String channel = null;
//		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		try {
//			JSONObject extrasJson = new JSONObject(extras);
//
//			String type = extrasJson.optString("is_login");
//			result = type;
//			Log.e("tag", "JSON:" + extrasJson);//			{"txt":"18","type":"3"}
//		} catch (Exception e) {
//			Log.e(TAG, "Unexpected: extras is not a valid json");
//		}
////		NotificationHelper.showMessageNotification(context, nm, title, message, channel);
//		return result;
//	}
//
//	private void doAction(Context context, String type) {
//		switch (type) {
//			case "1"://退出
////				ToastUtils.showToast("退出");
////				ToastUtils.showToast("您的账号已在其它地方登陆！");
////				SharePrefUtils.saveUserId("");
////				SharePrefUtils.clear();
////				SingleCall.getInstance().addValid(new LoginValid(context, 0)).doCall();
////				EventBus.getDefault().post(new CommentEvent(MAIN_finish));
//				break;
//		}
//	}
//
//	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//		for (String key : bundle.keySet()) {
//			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//				try {
//					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//					Iterator<String> it = json.keys();
//
//					while (it.hasNext()) {
//						String myKey = it.next();
//						sb.append("\nkey:" + key + ", value: [" +
//								myKey + " - " + json.optString(myKey) + "]");
//					}
//				} catch (JSONException e) {
////					Logger.e(TAG, "Get message extra JSON error!");
//				}
//			} else {
//				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//			}
//		}
//		return sb.toString();
//	}
}