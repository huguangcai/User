package com.ysxsoft.common_base.view.custom.image.group.listener;

import android.graphics.Bitmap;

public interface OnProgressListener {
    void onStart();

    void onComplete(Bitmap bitmap);
}
