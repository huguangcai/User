package com.ysxsoft.common_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.view.custom.PwdView;

/**
 * create by Sincerly on 2019/4/24 0024
 **/
public class BottomPwdDialog extends Dialog {
    private OnDialogListener listener;
    private Context mContext;
    PwdView pwdView;
    private View view;

    public BottomPwdDialog(Context context, int styleId) {
        super(context, styleId);
        this.mContext = context;
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_bottom_pwd, null);
        LinearLayout backLayout = (LinearLayout) view.findViewById(R.id.backLayout);
        TextView forget = (TextView) view.findViewById(R.id.forget);
        pwdView = (PwdView) view.findViewById(R.id.pwdView);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //跳转支付密码
                ARouter.getInstance().build("/main/SetPayPwdActivity").navigation();
            }
        });
        pwdView.setListener(new PwdView.OnInputListener() {
            @Override
            public void inputEnd(String pwd) {
                if ("".equals(pwd)) {
                    ToastUtils.shortToast(mContext, "请输入密码！");
                }
                if (listener != null) {
                    listener.onInput(pwd);
                }
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //隐藏键盘
//                KeyBoardUtils.hideKeybord(pwdView);
//                KeyBoardUtils.hideInputMethod((Activity) mContext);

//                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);//隐藏键盘
//                if(pwdView!=null){
//                    KeyBoardUtils.hideKeybord(pwdView);
////                    InputMethodManager inputMethodManager= (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
////                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);//隐藏键盘
//                }
            }
        });
    }

    public void closeKeyBord(){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setOnDialogListener(OnDialogListener listener) {
        this.listener = listener;
    }

    public interface OnDialogListener {
        void onInput(String pwd);
    }

    public void showDialog() {
        try {
            if (!isShowing()) {
                //先显示键盘
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(pwdView, 0);

                show();
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                getWindow().setAttributes(lp);
                getWindow().setGravity(Gravity.BOTTOM);
                getWindow().setContentView(view);
                reqeustFocus();
            } else {
                dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reqeustFocus() {
        if (pwdView != null) {
            pwdView.requestFocus();
        }
    }
}

