package com.ysxsoft.common_base.service.helper;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

/**
 * create by Sincerly on 2019/3/27 0027
 **/
public class AService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName= (String) event.getPackageName();
        Log.e("tag","packageName:"+packageName);
        AccessibilityEventCompat.getAction(event);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
