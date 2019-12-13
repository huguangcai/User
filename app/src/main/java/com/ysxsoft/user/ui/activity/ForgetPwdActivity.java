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
import com.ysxsoft.common_base.utils.CountDownTimeHelper;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.modle.ForgetPwdActivityResponse;
import com.ysxsoft.user.modle.SendMsgResonse;
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
@Route(path = "/main/ForgetPwdActivity")
public class ForgetPwdActivity extends BaseActivity {
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

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_invitation_code)
    EditText et_invitation_code;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_second_pwd)
    EditText et_second_pwd;
    @BindView(R.id.ok)
    TextView ok;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getForgetPwdActivity()).navigation();
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
        title.setText("忘记密码");
    }

    @OnClick({R.id.backLayout, R.id.tvCode, R.id.ok,})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvCode:
                sendMsg(et_phone.getText().toString().trim());
                new CountDownTimeHelper(60, tvCode);
                break;
            case R.id.ok:
                if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                    showToast("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_invitation_code.getText().toString().trim())) {
                    showToast("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
                    showToast("密码不能为空");
                    return;
                }
                if (et_pwd.getText().toString().trim().length() < 6) {
                    showToast("密码不能少于六位数");
                    return;
                }
                if (TextUtils.isEmpty(et_second_pwd.getText().toString().trim())) {
                    showToast("再次输入密码不能为空");
                    return;
                }
                if (!TextUtils.equals(et_pwd.getText().toString().trim(), et_second_pwd.getText().toString().trim())) {
                    showToast("再次输入密码不一致");
                    return;
                }
                submit();
                break;
        }
    }

    private void sendMsg(String phone) {
        OkHttpUtils.get()
                .url(Api.GET_SENDMSG)
                .addParams("phone",phone)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SendMsgResonse res = JsonUtils.parseByGson(response, SendMsgResonse.class);
                        if (res != null) {
                            showToast(res.getMessage());
                            if (HttpResponse.SUCCESS.equals(res.getCode())) {

                            }
                        }
                    }
                });
    }

    private void submit() {
        OkHttpUtils.get()
                .url(Api.GET_FORGETPWD)
                .addParams("type","1")
                .addParams("phone", et_phone.getText().toString().trim())
                .addParams("verify", et_invitation_code.getText().toString().trim())
                .addParams("password", et_pwd.getText().toString().trim())
                .addParams("confirm", et_second_pwd.getText().toString().trim())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ForgetPwdActivityResponse res = JsonUtils.parseByGson(response, ForgetPwdActivityResponse.class);
                        if (res != null) {
                            showToast(res.getMessage());
                            if (HttpResponse.SUCCESS.equals(res.getCode())) {
                                finish();
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd_layout;
    }
}
