package com.ysxsoft.user.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
@Route(path = "/main/IdentificationActivity")
public class IdentificationActivity extends BaseActivity {
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;

    @BindView(R.id.tvSelect)
    TextView tvSelect;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.ok)
    TextView ok;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getIdentificationActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("确认接单");
    }

    @OnClick({R.id.backLayout, R.id.ivCall, R.id.ok, R.id.tvSelect,})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.ivCall:
                IntentUtils.call(mContext,"10086");
                break;
            case R.id.tvSelect:
                showToast("选择时间");
                break;
            case R.id.ok:

                break;
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_identification_layout;
    }
}
