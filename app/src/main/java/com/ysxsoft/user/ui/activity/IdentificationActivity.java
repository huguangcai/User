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
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.DateUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.picker.TwoPicker;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.dialog.TimeSelectDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
                IntentUtils.callEdit(mContext, "10086");
                break;
            case R.id.tvSelect:
                showToast("选择时间");
                List<String> sevendate = DateUtils.getSevendate();
                List<String> a2 = new ArrayList<>();
                a2.add("8:30");
                a2.add("9:30");
                a2.add("10:30");
                a2.add("11:30");
                a2.add("12:30");
                a2.add("13:30");
                a2.add("14:30");
                TimeSelectDialog selectDialog = new TimeSelectDialog(mContext);
                selectDialog.setData(sevendate,a2,new TwoPicker.OnDialogSelectListener(){
                    @Override
                    public void OnSelect(String data1, int position1, String data2, int position2) {
                        tvSelect.setText(data1 + " " + data2);
                    }
                });
                break;
            case R.id.ok:
                if (TextUtils.isEmpty(tvSelect.getText().toString().trim())){
                    showToast("选择时间不能为空");
                    return;
                }
                submitData();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void submitData() {
        OkHttpUtils.post()
                .url(Api.GET_IDENTIFICATION)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .addParams("time", tvSelect.getText().toString().trim())
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identification_layout;
    }
}
