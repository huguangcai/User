package com.ysxsoft.user.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysxsoft.common_base.view.custom.picker.TwoPicker;
import com.ysxsoft.common_base.view.custom.wheel.NumberPickerView;
import com.ysxsoft.common_base.view.widgets.ABSDialog;
import com.ysxsoft.user.R;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Create By 胡
 * on 2019/12/25 0025
 */
public class SelectOnePicker extends ABSDialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private int p1;
    private int p2;
    NumberPickerView picker1;
    private String[] dataArray1;
    private String title;

    public SelectOnePicker(@NonNull Context context) {
        super(context);
        this.mContext = context;

        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    /**
     * 设置数据
     *
     * @param datas1
     */
    public void setData(List<String> datas1,int initP1,String title) {
        for (int i = 0; i < datas1.size(); i++) {
            datas1.set(i, datas1.get(i));
        }
        this.dataArray1 = datas1.toArray(new String[datas1.size()]);
        this.p1 = initP1;
        this.title = title;
        if (datas1 != null && datas1.size() != 0) {
            picker1.setDisplayedValuesAndPickedIndex(dataArray1, initP1, true);
        }
    }

    @Override
    protected void initView() {

        TextView cancel = getViewById(R.id.cancel);
         picker1 = getViewById(R.id.picker1);
        TextView sure = getViewById(R.id.sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.sure(picker1.getContentByCurrValue(), picker1.getValue());
                }
                dismiss();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_one_picker;
    }

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public interface OnDialogClickListener {
        void sure(String data1, int position1);
    }
}
