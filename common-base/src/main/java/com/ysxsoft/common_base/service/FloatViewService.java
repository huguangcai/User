package com.ysxsoft.common_base.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * create by Sincerly on 2019/1/10 0010
 **/
public abstract class FloatViewService extends Service implements View.OnTouchListener {
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private View floatView;
    private float x,y,startX,startY,touchStartX,touchStartY;
    private boolean move;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    public void createFloatView() {
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        params.y=100;
        params.horizontalMargin=-40;
        floatView = createView();

        windowManager.addView(floatView,params);
        floatView.setOnTouchListener(this);
        floatView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x=event.getRawX();
        y=event.getRawY()-25;//减去系统状态栏的高度
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                    move = false;
                    startX = event.getX();
                    startY = event.getY();
                    touchStartX = x;
                    touchStartY = y;
//                    touchDown(floatView, startX, startY);
                    windowManager.updateViewLayout(floatView, params);//修改高度
                break;
            case MotionEvent.ACTION_MOVE:
                    float offsetX = x - startX;
                    float offsetY = y - startY;
                    if (Math.abs(offsetX) > 5 && Math.abs(offsetY) > 5) {
                        move = true;
                        resetViewPosition();
//                        touchMove(floatView, offsetX, offsetY);
                    }
                break;
            case MotionEvent.ACTION_UP:
                    if (move) {
                        float offsetX2 = x - startX;
                        float offsetY2 = y - startY;
                        if (Math.abs(offsetX2) > 5 && Math.abs(offsetY2) > 5) {
                            resetViewPosition();
                            touchStartX = touchStartY = 0;
                        }
                    } else {
                        //停止自身
//                    stopSelf();
                    }
//                    touchUp(floatView);
                break;
        }
        return true;
    }

    private void resetViewPosition(){
        params.x= (int) (x-startX);
        params.y= (int) (y-startY);
        windowManager.updateViewLayout(floatView,params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatView != null) {
            windowManager.removeView(floatView);
        }
    }

    public  View createView(){
        return null;
    };

    public WindowManager.LayoutParams getWindowHeight(WindowManager.LayoutParams params){
        return params;
    }

    protected abstract void touchDown(View view,float x,float y);
    protected abstract void touchMove(View view,float offsetX,float offsetY);
    protected abstract void touchUp(View view);
}
