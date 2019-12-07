package com.ysxsoft.common_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.view.custom.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MuteBottomSelectDialog extends Dialog {
    private Context mContext;
    private OnDialogClickListener listener;
    private FlowLayout flowLayout;
    private List<Data> datas = new ArrayList<>();
    private List<TextView> dataViews = new ArrayList<>();

    public MuteBottomSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private View init() {
        View view = View.inflate(mContext, R.layout.dialog_mute_bottom_select, null);
        Button sure = view.findViewById(R.id.sure);
        Button cancel = view.findViewById(R.id.cancel);
        flowLayout = view.findViewById(R.id.flowLayout);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas == null) {
                    datas = new ArrayList<>();
                }
                List<Data> d = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isSelected()) {
                        d.add(datas.get(i));
                    }
                }
                if (listener != null) {
                    listener.OnSureClick(d);
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
        if (datas != null) {
            initFlowLayout();
        }
        return view;
    }

    /**
     * 初始化流式布局
     *
     */
    private void initFlowLayout() {
        flowLayout.removeAllViewsInLayout();
        dataViews.clear();
        for (int i = 0; i < datas.size(); i++) {
            View view = View.inflate(mContext, R.layout.item_mute_bottom_tab_menu, null);
            TextView content = view.findViewById(R.id.content);
            content.setText(datas.get(i).getName());
            content.setSelected(datas.get(i).isSelected());
            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data levels = datas.get(finalI);
                    TextView textView = dataViews.get(finalI);
                    textView.setSelected(!levels.isSelected());
                    levels.setSelected(!levels.isSelected());
                }
            });
            dataViews.add(content);
            flowLayout.addView(view);
        }
    }

    public OnDialogClickListener getListener() {
        return listener;
    }

    public void setListener(OnDialogClickListener listener) {
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

    /**
     * 注入数据
     *
     * @param datas
     */
    public void setData(List<Data> datas) {
        this.datas = datas;
    }

    public interface OnDialogClickListener {
        void OnSureClick(List<Data> selectData);
    }

    public static class Data {
        private String id;
        private String name;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

