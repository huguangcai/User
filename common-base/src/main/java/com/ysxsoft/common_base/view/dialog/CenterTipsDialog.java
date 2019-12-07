package com.ysxsoft.common_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

/**
 * create by Sincerly on 2019/1/28 0028
 **/
public class CenterTipsDialog extends Dialog {
    private Context mContext;
    private String content = "";
    private OnDialogClickListener listener;
    private OnCancelClickListener cancelClickListener;
    private TextView message;

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public CenterTipsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_center_tips, null);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView sure = view.findViewById(R.id.sure);
        message = view.findViewById(R.id.message);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.sure();
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelClickListener != null) {
                    cancelClickListener.cancel();
                }
                dismiss();
            }
        });
        if (content != null && !"".equals(content)) {
            message.setText(content);
        }
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(init());
    }

    public void showDialog() {
        if (!isShowing()) {
            show();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = DisplayUtils.getDisplayWidth(mContext) * 4 / 5;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    public void initContent(String content) {
        this.content = content;
    }

    public interface OnDialogClickListener {
        void sure();
    }

    public interface OnCancelClickListener{
        void cancel();
    }
}