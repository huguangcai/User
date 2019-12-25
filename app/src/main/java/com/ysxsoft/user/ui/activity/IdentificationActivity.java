package com.ysxsoft.user.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.ApplicationUtils;
import com.ysxsoft.common_base.utils.DateUtils;
import com.ysxsoft.common_base.utils.IntentUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.picker.TwoPicker;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.dialog.TimeSelectDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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
    @BindView(R.id.tvperson)
    TextView tvperson;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.LL1)
    LinearLayout LL1;
    @BindView(R.id.LL2)
    LinearLayout LL2;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.civHead)
    CircleImageView civHead;
    @Autowired
    String orderId;
    private String id;

    public static void start(String orderId) {
        ARouter.getInstance().build(ARouterPath.getIdentificationActivity()).withString("orderId", orderId).navigation();
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
        title.setText("确认接单");
    }

    @OnClick({R.id.backLayout, R.id.ivCall, R.id.ok, R.id.tvSelect, R.id.LL1, R.id.tvperson})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.ivCall:
                IntentUtils.callEdit(mContext, "10086");
                break;
            case R.id.LL1:
            case R.id.tvperson:
                SelectStaffActivity.start(this, 0x01);
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
                a2.add("15:30");
                a2.add("16:30");
                a2.add("17:30");
                TimeSelectDialog selectDialog = new TimeSelectDialog(mContext);
                selectDialog.setData(sevendate, a2, new TwoPicker.OnDialogSelectListener() {
                    @Override
                    public void OnSelect(String data1, int position1, String data2, int position2) {
                        tvSelect.setText(data1 + " " + data2);
                    }
                });
                break;
            case R.id.ok:
                if (TextUtils.isEmpty(tvSelect.getText().toString().trim())) {
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
        GetBuilder getBuilder = OkHttpUtils.get()
//                .url(Api.GET_SHOP_IDENTIFICATION)
                .url(Api.GET_CHECK_TAKE_ORDER)
                .addParams("takeCarTime", tvSelect.getText().toString().trim())
                .addParams("orderId", orderId)
                .addParams("staffId", id);

        switch (SharedPreferencesUtils.getSp(mContext, "role")) {
            case "staff":
                getBuilder.addParams("type", "staff");
                break;
            case "shop":
                getBuilder.addParams("type", "shop");
                break;
        }
        getBuilder.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag",">>>>>e+===="+e.getMessage().toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommonResonse resp = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (resp != null) {
                            showToast(resp.getMessage());
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    LL1.setVisibility(View.VISIBLE);
                    LL2.setVisibility(View.GONE);
                    String name = data.getStringExtra("name");
                    String avatar = data.getStringExtra("avatar");
                    String phone = data.getStringExtra("phone");
                    id = data.getStringExtra("id");
                    tvName.setText(name);
                    tvPhone.setText(phone);
                    Glide.with(mContext).load(AppConfig.BASE_URL + avatar).into(civHead);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
