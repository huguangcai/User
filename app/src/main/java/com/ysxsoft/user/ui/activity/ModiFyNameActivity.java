package com.ysxsoft.user.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.modle.ModifyNikeNameResonse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/10 0010
 */
@Route(path = "/main/ModiFyNameActivity")
public class ModiFyNameActivity extends BaseActivity {
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


    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.ivClose)
    ImageView ivClose;


    @Autowired
    String name;

    public static void start(String name) {
        ARouter.getInstance().build(ARouterPath.getModiFyNameActivity()).withString("name", name).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("姓名");
        etName.setText(name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name_layout;
    }

    @OnClick({R.id.backLayout, R.id.ivClose, R.id.tvOk})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.ivClose:
                etName.setText("");
                break;
            case R.id.tvOk:
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    showToast("姓名不能为空");
                    return;
                }
                submitData();
                break;
        }
    }

    private void submitData() {
        OkHttpUtils.get()
                .url(Api.GET_EDIT_USER_NIKENAME)
                .addParams("id", SharedPreferencesUtils.getUid(mContext))
                .addParams("username", etName.getText().toString().trim())
                .addParams("identity", SharedPreferencesUtils.getSp(mContext, "role"))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ModifyNikeNameResonse resp = JsonUtils.parseByGson(response, ModifyNikeNameResonse.class);
                        if (resp != null) {
                            showToast(resp.getMessage());
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                finish();
                            }
                        }
                    }
                });
    }
}
