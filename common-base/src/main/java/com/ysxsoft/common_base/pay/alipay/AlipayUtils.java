package com.ysxsoft.common_base.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * create by Sincerly on 2018/12/29 0029
 **/
public class AlipayUtils {

    /**
     * 支付宝支付
     * @param context  上下文
     * @param handler  回调处理
     * @param whatCode 请求码
     * @param orderInfo 订单信息
     */
    public static void startAlipay(final Activity context, final Handler handler, final int whatCode, final String orderInfo) {
        Runnable payRunnable =  new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = whatCode;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 备注使用
    ///////////////////////////////////////////////////////////////////////////
//	1.支付宝
//	PayUtils.startAlipay(this,mHandler,0x01,data);//支付宝支付
//	支付宝支付结果
//	private Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//				case 0x10:
//					Map<String, String> map = (Map<String, String>) msg.obj;
//					//9000支付成功  8000 正在处理中  4000 订单支付失败  5000重复请求  6001中途取消  6002网络连接出错 6004 支付结果未知  其他其他支付错误
//					if ("9000".equals(map.get("resultStatus"))) {//订单支付成功
//						Toast.makeText(QuDlInputActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
//						finish();
//					} else if ("4000".equals(map.get("resultStatus"))) {//订单支付失败
//						Toast.makeText(QuDlInputActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
//					} else if ("6001".equals(map.get("resultStatus"))) {//订单支付中途取消
//						Toast.makeText(QuDlInputActivity.this, "支付宝支付取消！", Toast.LENGTH_SHORT).show();
//					}
//					Log.e("tag", new Gson().toJson(map));
//					break;
//			}
//		}
//	};

}
