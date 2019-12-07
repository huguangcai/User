package com.ysxsoft.common_base.view.custom.picker;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.ysxsoft.common_base.R;

import java.util.Calendar;
import java.util.Date;

/**
 * create by Sincerly on 2019/1/8 0008
 **/
public class DateYMDPicker {
    private Context context;
    private TimePickerView pvTime;
    private OnSelectedListener listener;

    public void init(Context context){
        this.context=context;
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(1900,0,1);

         pvTime = new TimePickerView(new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if(listener!=null){
                    listener.onSelected(date);
                }
            }
        }).setLayoutRes(R.layout.picker_date, new CustomListener() {
             @Override
             public void customLayout(View v) {
                 Button button=v.findViewById(R.id.sure);
                 button.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         pvTime.returnData();
                         diss();
                     }
                 });
             }
         })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,Calendar.getInstance())//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                );
    }

    public void show(OnSelectedListener listener){
        this.listener=listener;
        show();
    }

    public interface OnSelectedListener{
        void onSelected(Date date);
    }

    private void diss(){
        if(pvTime!=null){
            pvTime.dismiss();
        }
    }

    private void show(){
        diss();
        pvTime.show();
    }

}
