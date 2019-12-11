package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.MainChild5FragmentResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.AboutPlatformActivity;
import com.ysxsoft.user.ui.activity.AllOrderActivity;
import com.ysxsoft.user.ui.activity.CookerDataActivity;
import com.ysxsoft.user.ui.activity.CookerDetailActivity;
import com.ysxsoft.user.ui.activity.FeedBackActivity;
import com.ysxsoft.user.ui.activity.PersonCenterActivity;
import com.ysxsoft.user.ui.activity.WalletActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class MainChild5Fragment extends BaseFragment {
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

    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.ivMyMall)
    ImageView ivMyMall;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.llName)
    LinearLayout llName;
    @BindView(R.id.tvAllOrder)
    TextView tvAllOrder;


    @Override
    protected void doWork(View view) {
        initTitle();
//        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_PERSON_DATA)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MainChild5FragmentResponse resp = JsonUtils.parseByGson(response, MainChild5FragmentResponse.class);
                        if (resp != null) {

                        }
                    }
                });
    }

    private void initTitle() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        params.height = DisplayUtils.getStatusBarHeight(getActivity());
        statusBar.setLayoutParams(params);

        bg.setBackgroundColor(getResources().getColor(R.color.transparent));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        back.setVisibility(View.GONE);
        bottomLineView.setVisibility(View.GONE);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("个人中心");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mainchild5;
    }

    @OnClick({R.id.civ, R.id.ivMyMall, R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.llName, R.id.tvAllOrder})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.civ:
            case R.id.llName:
                PersonCenterActivity.start();
                break;
            case R.id.ivMyMall:
                CookerDetailActivity.start();
                break;
            case R.id.tv1://钱包
                WalletActivity.start();
                break;
            case R.id.tv2://版本更新

                break;
            case R.id.tv3://关于平台
                AboutPlatformActivity.start();
                break;
            case R.id.tv4://意见反馈
                FeedBackActivity.start();
                break;
            case R.id.tvAllOrder://全部订单
                AllOrderActivity.start();
                break;
        }
    }
}
