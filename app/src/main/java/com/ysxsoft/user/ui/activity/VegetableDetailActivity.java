package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.VegetableDetailResponse;
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
@Route(path = "/main/VegetableDetailActivity")
public class VegetableDetailActivity extends BaseActivity {

    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvTypeName)
    TextView tvTypeName;
    @BindView(R.id.tvSales)
    TextView tvSales;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;

    public static void start(){
        ARouter.getInstance().build(ARouterPath.getVegetableDetailActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        requesetData();
    }

    private void requesetData() {
        OkHttpUtils.post()
                .url(Api.GET_VEGETABLE_DETAIL)
                .addParams("id", "")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VegetableDetailResponse resp = JsonUtils.parseByGson(response, VegetableDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
//                                Glide.with(mContext).load("").into(iv);
//                                tvName.setText();
//                                tvMoney.setText("￥"+"49");
//                                tvTypeName.setText("#"+"#牛排");
//                                tvSales.setText("月售298份 | 厨师推荐");
//                                tv2.setText();
//                                tv3.setText();
//                                tv4.setText();
//                                tv5.setText();
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vegetable_layout;
    }

    @OnClick({R.id.ivback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                backToActivity();
                break;
        }
    }
}
