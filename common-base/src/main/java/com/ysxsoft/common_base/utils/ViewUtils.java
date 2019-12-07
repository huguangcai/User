package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * create by Sincerly on 2019/5/10 0010
 **/
public class ViewUtils {
    public static void setRight(Context context,TextView textView, int resourceId){
        Drawable drawable=context.getResources().getDrawable(resourceId);
        drawable.setBounds(new Rect(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight()));
        textView.setCompoundDrawables(null,null,drawable,null);
    }
    public static void setLeft(Context context,TextView textView, int resourceId){
        Drawable drawable=context.getResources().getDrawable(resourceId);
        drawable.setBounds(new Rect(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight()));
        textView.setCompoundDrawables(drawable,null,null,null);
    }
}
