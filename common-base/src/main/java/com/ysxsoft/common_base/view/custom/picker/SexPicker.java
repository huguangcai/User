package com.ysxsoft.common_base.view.custom.picker;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.ysxsoft.common_base.R;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SexPicker {
    private Context context;
    private OptionsPickerView pickerView;
    private OnSelectedListener listener;
    private List<String> sexs = new ArrayList<>();

    public void init(Context context) {
        this.context = context;
        sexs.clear();
        sexs.add("请选择性别");
        sexs.add("男");
        sexs.add("女");
        pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (listener != null) {
                    listener.onSelected(sexs.get(options1));
                }
            }
        }).setLayoutRes(R.layout.picker_sex, new CustomListener() {
            @Override
            public void customLayout(View v) {
                Button button = v.findViewById(R.id.sure);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerView.returnData();
                        diss();
                    }
                });
            }
        })
                .setTitleText("")
                .setTextColorCenter(Color.parseColor("#282828"))
                .setContentTextSize(16)
                .setOutSideCancelable(true)
                .isDialog(true)
                .setSubCalSize(3)
                .build();
        pickerView.setPicker(sexs);//添加数据
    }

    public void show(OnSelectedListener listener) {
        this.listener = listener;
        show();
    }

    public interface OnSelectedListener {
        void onSelected(String text);
    }

    private void diss() {
        if (pickerView != null) {
            pickerView.dismiss();
        }
    }

    private void show() {
        diss();
        pickerView.show();
    }
}
