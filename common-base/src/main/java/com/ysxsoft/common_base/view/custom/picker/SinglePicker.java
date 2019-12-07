package com.ysxsoft.common_base.view.custom.picker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.view.custom.wheel.NumberPickerView;

import java.util.List;

/**
 * create by Sincerly on 2019/5/22 0022
 **/
public class SinglePicker extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnDialogSelectListener listener;
    private int p;
    private String[] data = new String[]{};
    NumberPickerView picker;
    private String title;

    public SinglePicker(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_single_picker, null);
        TextView sure = view.findViewById(R.id.sure);
        TextView t = view.findViewById(R.id.t);
        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        picker = view.findViewById(R.id.picker);
        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                value = picker.getContentByCurrValue();
            }
        });

        if (data != null && data.length != 0) {
//            picker.setMinValue(0);
//            picker.setMaxValue(data.length-1);
            picker.setDisplayedValuesAndPickedIndex(data, p, true);
        }
        if(title!=null){
            t.setText(title);
        }
        return view;
    }

    private String value = "";

    /**
     * 设置数据源
     * @param datas
     * @param initP   初始
     */
    public void setData(List<String> datas,int initP) {
        this.data = datas.toArray(new String[]{});
        this.p=initP;
        if (data != null && data.length != 0) {
//            picker.setMinValue(0);
//            picker.setMaxValue(datas.size()-1);
            picker.setDisplayedValuesAndPickedIndex(data, p, true);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OnDialogSelectListener getListener() {
        return listener;
    }

    public void setListener(OnDialogSelectListener listener) {
        this.listener = listener;
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
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel) {
            dismiss();
        } else if (id == R.id.sure) {//确认
            if (listener != null) {
                listener.OnSelect(picker.getContentByCurrValue(), picker.getValue());
                dismiss();
            }
        }

    }

    public interface OnDialogSelectListener {
        void OnSelect(String data, int position);
    }
}
