package com.ysxsoft.common_base.listener;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.ysxsoft.common_base.utils.LogUtils;

/**
 * create by Sincerly on 2019/3/2 0002
 * 电话状态监听
 **/
public class PhoneCallListener extends PhoneStateListener {
    private static final String TAG = "PhoneCallListener";
    protected CallListener listener;

    /**
     * 返回电话状态
     * <p>
     * CALL_STATE_IDLE 无任何状态时
     * CALL_STATE_OFFHOOK 接起电话时
     * CALL_STATE_RINGING 电话进来时
     */
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:// 电话挂断
                LogUtils.e(TAG, "电话挂断...");
                if (listener != null) {
                    listener.onHangUp();
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: // 来电接通 或者 去电，去电接通  但是没法区分
                LogUtils.e(TAG, "正在通话...");
                if (listener != null) {
                    listener.onCallRinging();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING: //电话响铃的状态
                LogUtils.e(TAG, "电话响铃");
                if (listener != null) {
                    listener.bell();
                }
                break;
        }
        super.onCallStateChanged(state, incomingNumber);
    }

    //写个回调
    public void setCallListener(CallListener callListener) {
        this.listener = callListener;
    }

    //回调接口
    public interface CallListener {
        void onCallRinging();

        void onHangUp();

        void bell();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 使用
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 监听电话
     */
    /*private void listenPhone(){
        //获得相应的系统服务
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if(tm != null) {
            try {
                PhoneCallListener listener=new PhoneCallListener();
                listener.setCallListener(new PhoneCallListener.CallListener() {
                    @Override
                    public void onCallRinging() {
                        //通话中
                        pauseMusic("");
                    }

                    @Override
                    public void onHangUp() {
                        //挂断
                        playMusic("");
                    }

                    @Override
                    public void bell() {
                        //电话响铃
                        pauseMusic("");
                    }
                });
                // 注册来电监听
                tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
            } catch(Exception e) {
                // 异常捕捉
            }
        }
    }*/
}
