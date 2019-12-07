package com.ysxsoft.common_base.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

/**
 * create by Sincerly on 2019/1/5 0005
 **/
public class LoadingDialog extends Dialog {
    private Context mContext;
    private TextView content;
    private String txt="正在加载";

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_center_loading, null);
        content = view.findViewById(R.id.content);
        ImageView loading=view.findViewById(R.id.loading);

        AnimationDrawable animationDrawable = (AnimationDrawable) loading.getDrawable();
        animationDrawable.start();

        content.setText(txt);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setContentView(init());
    }

    public void setText(String text) {
        this.txt = text;
    }

    public void showDialog() {
        try {
            if (!isShowing()) {
                show();
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.width = DisplayUtils.dp2px(mContext, 100);
                lp.height = DisplayUtils.dp2px(mContext, 100);
                getWindow().setAttributes(lp);
                getWindow().setGravity(Gravity.CENTER);
            } else {
                dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
