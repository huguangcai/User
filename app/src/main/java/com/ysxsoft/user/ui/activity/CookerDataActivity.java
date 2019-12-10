package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.CookerDataResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/8 0008
 */
@Route(path = "/main/CookerDataActivity")
public class CookerDataActivity extends BaseActivity {
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

    @BindView(R.id.ivHead)
    CircleImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCookerName)
    TextView tvCookerName;
    @BindView(R.id.tvCookerPhone)
    TextView tvCookerPhone;
    @BindView(R.id.tvIdCard)
    TextView tvIdCard;
    @BindView(R.id.tvCookerLevel)
    TextView tvCookerLevel;
    @BindView(R.id.tvServiceMoney)
    TextView tvServiceMoney;
    @BindView(R.id.tvStyle_Of_Cooking)
    TextView tvStyle_Of_Cooking;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvStartWorkTime)
    TextView tvStartWorkTime;
    @BindView(R.id.tvCookerIntroduce)
    TextView tvCookerIntroduce;
    @BindView(R.id.ivProve)
    RoundImageView ivProve;


    public static void start() {
        ARouter.getInstance().build(ARouterPath.getCookerDataActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        requestData();
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setTextColor(getResources().getColor(R.color.color_282828));
        title.setText("厨师资料");
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_COOKER_DATA)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CookerDataResponse resp = JsonUtils.parseByGson(response, CookerDataResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
//                                Glide.with(mContext).load("").into(ivHead);
//                                tvName.setText("");
//                                tvCookerName.setText("");
//                                tvCookerPhone.setText("");
//                                tvIdCard.setText("");
//                                tvCookerLevel.setText("");
//                                tvServiceMoney.setText("");
//                                tvStyle_Of_Cooking.setText("");
//                                tvAddress.setText("");
//                                tvStartWorkTime.setText("");
//                                tvCookerIntroduce.setText("");
//                                Glide.with(mContext).load("").into(ivProve);
                            }
                        }
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_data_layout;
    }

    @OnClick({R.id.backLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
        }
    }
}
