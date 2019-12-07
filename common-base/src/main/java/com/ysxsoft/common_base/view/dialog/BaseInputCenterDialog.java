package com.ysxsoft.common_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.ToastUtils;

/**
 * create by Sincerly on 2019/1/28 0028
 **/
public class BaseInputCenterDialog extends Dialog {
    private Context mContext;
    private String dialogTitle;
    private String tips = "";
    private String content = "";
    private OnDialogClickListener listener;
    private EditText message;

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public BaseInputCenterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_base_input, null);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView title = view.findViewById(R.id.dialogTitle);
        TextView sure = view.findViewById(R.id.sure);
        message = view.findViewById(R.id.message);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(message.getText().toString())) {
                    ToastUtils.shortToast(mContext, tips == null ? "" : tips);
                    return;
                }
                if (listener != null) {
                    listener.sure(message.getText().toString());
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title.setText(dialogTitle == null ? "" : dialogTitle);
        if (content != null && !"".equals(content)) {
            message.setText(content);
            message.setSelection(content.length());
        }
        if (tips != null && !"".equals(tips)) {
            message.setHint(tips);
        }
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
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

    public void initTips(String tips) {
        this.tips = tips;
    }

    public void initTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public interface OnDialogClickListener {
        void sure(String nickname);
    }
}