package com.ysxsoft.common_base.pay.wx;

import android.os.Looper;

/**
 * create by Sincerly on 2018/12/19 0019
 **/
public class WxPaymentManager {
    public static WxPaymentManager instance;
    public static OnPayResultListener result;

    public static WxPaymentManager getInstance() {
        if (instance == null) {
            synchronized (WxPaymentManager.class) {
                instance = new WxPaymentManager();
            }
        }
        return instance;
    }

    /**
     * 注册微信事件监听
     * @param inter
     */
    public void attachEvent(OnPayResultListener inter) {
        result = inter;
    }

    /**
     * 微信支付成功
     */
    public void onSuccess() {
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    result.onSuccess();
                }
            }
        });
    }

    /**
     * 微信支付失败
     */
    public void onError() {
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    result.onError();
                }
            }
        });
    }

    /**
     * 支付状态码
     * @param code
     */
    public void onResponseCode(final int code) {
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    result.onResponseCode(code);
                }
            }
        });
    }

    /**
     * 微信支付取消
     */
    public void onCancel() {
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    result.onCancel();
                }
            }
        });
    }

    public interface OnPayResultListener {
        void onCancel();
        void onSuccess();
        void onError();
        void onResponseCode(int wxResponseCode);
    }
}
