package com.ysxsoft.common_base.helper.glide;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ImageViewTarget;

public class ScaleTarget extends ImageViewTarget<Bitmap> {
    private ImageView target;

    public ScaleTarget(ImageView view) {
        super(view);
        this.target=view;
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        //获取原图的宽高
        int width = resource.getWidth();
        int height = resource.getHeight();
        //获取imageView的宽
        int imageViewWidth = target.getWidth();
        //计算缩放比例
        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);

        int imageViewHeight = (int) (height * sy);
        ViewGroup.LayoutParams params = target.getLayoutParams();
        params.height = imageViewHeight;
        target.setLayoutParams(params);
    }
}
