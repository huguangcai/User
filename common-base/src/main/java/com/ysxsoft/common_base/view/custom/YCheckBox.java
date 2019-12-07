package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.ysxsoft.common_base.R;

/**
 * create by Sincerly on 2019/1/4 0004
 **/
public class YCheckBox extends AppCompatCheckBox {
    private Context mContext;
    private int normal, checked;

    public YCheckBox(Context context) {
        this(context, null);
    }

    public YCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YCheckBox);
        Drawable normal = typedArray.getDrawable(R.styleable.YCheckBox_normalResouce);
        Drawable checked = typedArray.getDrawable(R.styleable.YCheckBox_checkedResouce);
        typedArray.recycle();

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.checked}, checked);
        stateListDrawable.addState(new int[]{-android.R.attr.checked}, normal);
        if (Build.VERSION.SDK_INT > 10) {
            stateListDrawable.setEnterFadeDuration(500);
            stateListDrawable.setExitFadeDuration(500);
        }
        super.setButtonDrawable(stateListDrawable);
    }

//    @Override
//    public void setButtonDrawable(int resId) {
//        StateListDrawable stateListDrawable = new StateListDrawable();
//        stateListDrawable.addState(new int[]{android.R.attr.checked,}, checked);
//        stateListDrawable.addState(new int[]{}, normal);
//        if (Build.VERSION.SDK_INT > 10) {
//            stateListDrawable.setEnterFadeDuration(500);
//            stateListDrawable.setExitFadeDuration(500);
//        }
//        super.setButtonDrawable(stateListDrawable);
//    }
}
