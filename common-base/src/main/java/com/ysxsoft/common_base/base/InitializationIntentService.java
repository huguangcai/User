package com.ysxsoft.common_base.base;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

/**
 * create by Sincerly on 2019/6/11 0011
 **/
public class InitializationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitializationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
