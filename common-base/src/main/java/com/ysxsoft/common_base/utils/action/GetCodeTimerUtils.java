package com.ysxsoft.common_base.utils.action;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sincerly on 2018/6/19 0019.
 * QQ:1475590636
 * 定时器  60s倒计时
 */

public class GetCodeTimerUtils {
	public int TOTAL_TIME = 0;//总时间
	public int DELAY_TIME = 1;//步进方式   1s
	public OnGetCodeListener listener;
	public GetCodeTimerUtils instance = null;
	public boolean isRunning = false;
	Timer timer = new Timer();

	public GetCodeTimerUtils() {
	}

	public static GetCodeTimerUtils getInstance() {
		return new GetCodeTimerUtils();
	}

	/**
	 * 初始化延时时间
	 *
	 * @param time
	 */
	public void initDelayTime(int time) {
		TOTAL_TIME = time;
	}

	/**
	 * 初始步进时间
	 *
	 * @param time
	 */
	public void initStepTime(int time) {
		DELAY_TIME = time;
	}

	public void setText(View view) {
		if (view instanceof TextView) {
			TextView v = (TextView) view;
		} else if (view instanceof Button) {
			Button v = (Button) view;
		}
	}

	public void startDelay() {
		if (!isRunning) {
			Log.e("tag", "startDelay");
			isRunning = true;
			if(timer==null){
				timer=new Timer();
			}
			timer.schedule(new MyTimerTask(), 0, DELAY_TIME * 1000);
		}
	}

	public void stopDelay() {
		if(timer!=null){
			timer.cancel();
		}
		timer=null;
		isRunning = false;
	}

	public void setOnGetCodeListener(OnGetCodeListener listener) {
		this.listener = listener;
	}

	public interface OnGetCodeListener {
		void onRunning(int totalTime);

		void onFinish();
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			--TOTAL_TIME;
			if (TOTAL_TIME > 0) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						listener.onRunning(TOTAL_TIME);
					}
				});
			} else {
				isRunning = false;
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						listener.onFinish();
					}
				});
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// 用法
	///////////////////////////////////////////////////////////////////////////

//	private boolean isRunning = false;
//	GetCodeTimerUtils utils;
//	utils = GetCodeTimerUtils.getInstance();
//
// if (!isRunning) {
//		utils.initDelayTime(60);
//		utils.initStepTime(1);
//		utils.setOnGetCodeListener(new GetCodeTimerUtils.OnGetCodeListener() {
//			@Override
//			public void onRunning(int totalTime) {
//				getCode.setText(totalTime + "s后可重新获取");
//				isRunning = true;
//			}
//
//			@Override
//			public void onFinish() {
//				Log.e("tag", "onFinish");
//				utils.stopDelay();
//				getCode.setText("重新获取");
//				isRunning = false;
//			}
//		});
//		utils.startDelay();
//	}


}
