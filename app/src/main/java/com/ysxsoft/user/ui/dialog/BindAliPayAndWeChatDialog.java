package com.ysxsoft.user.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.view.widgets.ABSDialog;
import com.ysxsoft.user.R;

import androidx.annotation.NonNull;

/**
 * Create By 胡
 * on 2019/12/11 0011
 */
public class BindAliPayAndWeChatDialog extends ABSDialog {

    private OnDialogClickListener listener;
    private Context context;

    public BindAliPayAndWeChatDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DisplayUtils.getDisplayWidth(context) * 4 / 5;
//            lp.width = DisplayUtils.getDisplayWidth(mContext);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);

    }

    @Override
    protected void initView() {
        ImageView ivClose = getViewById(R.id.ivClose);
        EditText name = getViewById(R.id.name);
        TextView tvTip = getViewById(R.id.tvTip);
        TextView tvOk = getViewById(R.id.tvOk);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    ToastUtils.show(context,"输入内容不能为空");
                }

                if (listener!=null){
                    listener.sure(name.getText().toString().trim());
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_bind_wechat_alipay_layout;
    }
    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public interface OnDialogClickListener {
        void sure(String name);
    }
}
