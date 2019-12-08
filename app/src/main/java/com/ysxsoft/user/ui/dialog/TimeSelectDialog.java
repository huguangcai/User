package com.ysxsoft.user.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.view.custom.picker.TwoPicker;
import com.ysxsoft.common_base.view.widgets.ABSDialog;
import com.ysxsoft.user.R;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Create By 胡
 * on 2019/12/8 0008
 */
public class TimeSelectDialog extends ABSDialog {

    private Context mContext;
    private OnDialogClickListener listener;

    public TimeSelectDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;

        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void initView() {
        TextView sure = getViewById(R.id.sure);
        TextView cancel = getViewById(R.id.cancel);
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
                dismiss();
            }
        });
    }

    /**
     * 设置数据
     * @param datas1
     * @param datas2
     * @param listener
     */
    public void setData(List<String> datas1, List<String> datas2, TwoPicker.OnDialogSelectListener listener) {
        TwoPicker picker=new TwoPicker(mContext,R.style.BottomDialogStyle);
        picker.setData(datas1,datas2,0,0);
        picker.setTitle("选择时间");
        picker.setListener(listener);
        picker.showDialog();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_choose_time;
    }

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public interface OnDialogClickListener {
        void sure();
    }



}
