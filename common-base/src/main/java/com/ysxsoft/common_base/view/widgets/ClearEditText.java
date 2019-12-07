package com.ysxsoft.common_base.view.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * create by Sincerly on 2019/1/3 0003
 **/
public class ClearEditText extends AppCompatEditText {

    public ClearEditText(Context context) {
        super(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused){
            if("".equals(getText().toString())){

            }else{
            }
        }
    }
}
