package com.ysxsoft.user.ui.activity;

import android.text.InputType;
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
import com.ysxsoft.common_base.utils.CountDownTimeHelper;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
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
 * on 2019/12/10 0010
 */
@Route(path = "/main/PhoneLoginPayPwdActivity")
public class PhoneLoginPayPwdActivity extends BaseActivity {
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
    @Autowired
    String type;

    public static void start(String type) {
        ARouter.getInstance().build(ARouterPath.getPhoneLoginPayPwdActivity()).withString("type", type).navigation();
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
        switch (type) {
            case "1":
                title.setText("手机号");
                break;
            case "2":
                title.setText("登录密码");
                break;
            case "3":
                title.setText("支付密码");
                et_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                et_second_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd_layout;
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

                if (type.equals("3")) {
                    if (et_pwd.getText().toString().trim().length() != 6) {
                        showToast("请输入六位数密码");
                        return;
                    }
                } else {
                    if (et_pwd.getText().toString().trim().length() < 6) {
                        showToast("密码不能少于六位数");
                        return;
                    }
                }
                if (TextUtils.isEmpty(et_second_pwd.getText().toString().trim())) {
                    showToast("再次输入密码不能为空");
                    return;
                }
                if (TextUtils.equals(et_pwd.getText().toString().trim(), et_second_pwd.getText().toString().trim())) {
                    showToast("再次输入密码不一致");
                    return;
                }
                submit();
                break;
        }
    }

    private void submit() {
        OkHttpUtils.post()
                .url(Api.GET_PHONELOGINPAYPWD)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .addParams("phone", et_phone.getText().toString().trim())
                .addParams("code", et_invitation_code.getText().toString().trim())
                .addParams("pwd", et_pwd.getText().toString().trim())
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

    private void sendMsg(String phone) {
        OkHttpUtils.post()
                .url(Api.GET_SENDMSG)
                .addParams("phone", phone)
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
                                showToast("发送成功");
                            }
                        }
                    }
                });
    }
}
