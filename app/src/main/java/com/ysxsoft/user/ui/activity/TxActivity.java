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
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.MoneyTextWatcher;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/11 0011
 */
@Route(path = "/main/TxActivity")
public class TxActivity extends BaseActivity {
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

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    private int type = 1;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getTxActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        bottomLineView.setVisibility(View.GONE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("提现");
        et.addTextChangedListener(new MoneyTextWatcher(et));
        iv1.setBackgroundResource(R.mipmap.icon_ok);
        iv2.setBackgroundResource(R.mipmap.icon_no);
        iv3.setBackgroundResource(R.mipmap.icon_no);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tx_layout;
    }

    @OnClick({R.id.backLayout, R.id.tvAll, R.id.LL1, R.id.LL2, R.id.LL3, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvAll:
                et.setText(tvMoney.getText().toString());
                break;
            case R.id.LL1:
                type = 1;
                iv1.setBackgroundResource(R.mipmap.icon_ok);
                iv2.setBackgroundResource(R.mipmap.icon_no);
                iv3.setBackgroundResource(R.mipmap.icon_no);
                break;
            case R.id.LL2:
                type = 2;
                iv1.setBackgroundResource(R.mipmap.icon_no);
                iv2.setBackgroundResource(R.mipmap.icon_ok);
                iv3.setBackgroundResource(R.mipmap.icon_no);
                break;
            case R.id.LL3:
                type = 3;
                iv1.setBackgroundResource(R.mipmap.icon_no);
                iv2.setBackgroundResource(R.mipmap.icon_no);
                iv3.setBackgroundResource(R.mipmap.icon_ok);
                break;
            case R.id.tvOk:
                if (TextUtils.isEmpty(et.getText().toString().trim())) {
                    showToast("输入金额不能为空");
                    return;
                }
                submitData();
                break;
        }
    }

    private void submitData() {
        OkHttpUtils.post()
                .url(Api.GET_TXACTIVITY)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .addParams("type", String.valueOf(type))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommonResonse resp = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
                                finish();
                            }
                        }
                    }
                });

    }
}
