package com.ysxsoft.common_base.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class CountDownTimeHelper {
    private int time;
    private TextView view;

    public CountDownTimeHelper(int time, TextView view) {
        this.time = time;
        this.view = view;
        MyTime time1 = new MyTime(time * 1000, 1000);
        time1.start();
    }

    public class MyTime extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.setEnabled(false);
            view.setText("倒计时(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            view.setEnabled(true);
            view.setText("重新发送");
        }
    }
}
