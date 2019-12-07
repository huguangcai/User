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
public class TwoPicker extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnDialogSelectListener listener;
    private int p1;
    private int p2;
    NumberPickerView picker1;
    NumberPickerView picker2;
    private String[] dataArray1;
    private String[] dataArray2;
    private String title;

    public TwoPicker(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_two_picker, null);
        TextView sure = view.findViewById(R.id.sure);
        TextView t = view.findViewById(R.id.t);
        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        picker1 = view.findViewById(R.id.picker1);
        picker2 = view.findViewById(R.id.picker2);
        if (dataArray1 != null && dataArray1.length != 0) {
            picker1.setDisplayedValuesAndPickedIndex(dataArray1, p1, true);
        }

        if (dataArray2 != null && dataArray2.length != 0) {
            picker2.setDisplayedValuesAndPickedIndex(dataArray2, p2, true);
        }
        if(title!=null){
            t.setText(title);
        }
        return view;
    }

    /**
     * 设置数据源
     */
    public void setData(List<String> datas1, List<String> datas2,int initP1, int initP2) {
        for (int i = 0; i < datas1.size(); i++) {
            datas1.set(i, datas1.get(i));
        }
        for (int i = 0; i < datas2.size(); i++) {
            datas2.set(i, datas2.get(i));
        }
        this.dataArray1 = datas1.toArray(new String[datas1.size()]);
        this.dataArray2 = datas2.toArray(new String[datas2.size()]);
        this.p1 = initP1;
        this.p2 = initP2;
        if (datas1 != null && datas1.size() != 0) {
            picker1.setDisplayedValuesAndPickedIndex(dataArray1, initP1, true);
        }
        if (datas2 != null && datas2.size() != 0) {
            picker2.setDisplayedValuesAndPickedIndex(dataArray2, initP2, true);
        }
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
                listener.OnSelect(picker1.getContentByCurrValue(), picker1.getValue(), picker2.getContentByCurrValue(), picker2.getValue());
            }
            dismiss();
        }
    }

    public interface OnDialogSelectListener {
        void OnSelect(String data1, int position1, String data2, int position2);
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
