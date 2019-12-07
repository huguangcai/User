package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
@Route(path = "/main/RefuseCauseActivity")
public class RefuseCauseActivity extends BaseActivity {
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvSumType)
    TextView tvSumType;
    @BindView(R.id.tvSelect)
    TextView tvSelect;
    @BindView(R.id.etMark)
    EditText etMark;
    @BindView(R.id.ok)
    TextView ok;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getRefuseCauseActivity()).navigation();
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
        title.setText("拒绝原因");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refuse_cause_layout;
    }

    @OnClick({R.id.backLayout, R.id.tvSumType, R.id.ok, R.id.tvSelect,})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvSumType:

                break;
            case R.id.tvSelect:

                break;
            case R.id.ok:

                break;
        }
    }

}
