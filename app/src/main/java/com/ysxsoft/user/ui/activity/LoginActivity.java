package com.ysxsoft.user.ui.activity;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.adapter.util.TouchEventUtil;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.AndroidUtils;
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.MainActivity;
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
 * on 2019/12/7 0007
 */
@Route(path = "/main/LoginActivity")
public class LoginActivity extends BaseActivity {

    @BindView(R.id.logo)
    RoundImageView logo;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.forgetPwd)
    TextView forgetPwd;


    public static void start() {
        ARouter.getInstance().build(ARouterPath.getLoginActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
    }

    private void initTitle() {
        logo.setBackgroundDrawable(ApplicationUtils.getAppIcon(mContext,ApplicationUtils.getPackageName(mContext)));
    }

    @OnClick({R.id.login, R.id.forgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
//                if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
//                    showToast("手机号不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
//                    showToast("密码不能为空");
//                    return;
//                }
//                LoginData();

                MainActivity.start();
//                finish();
                break;
            case R.id.forgetPwd:
                ForgetPwdActivity.start();
                break;
        }
    }

    private void LoginData() {
        OkHttpUtils.post()
                .url(Api.GET_LOGIN)
                .addParams("phone", et_phone.getText().toString().trim())
                .addParams("password", et_pwd.getText().toString().trim())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommonResonse res = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (res != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
                                MainActivity.start();
                                finish();
                            }
                        }
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
