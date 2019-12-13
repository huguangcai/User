package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.MallDataResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class MallFragment2 extends BaseFragment {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvIdCard)
    TextView tvIdCard;
    @BindView(R.id.tvMallName)
    TextView tvMallName;
    @BindView(R.id.tvMallPhone)
    TextView tvMallPhone;
    @BindView(R.id.tvWorkTime)
    TextView tvWorkTime;
    @BindView(R.id.tvMallDesc)
    TextView tvMallDesc;
    @BindView(R.id.tvMallGuiMo)
    TextView tvMallGuiMo;
    @BindView(R.id.tvManagerTime)
    TextView tvManagerTime;
    @BindView(R.id.tvArea)
    TextView tvArea;
    @BindView(R.id.riv)
    RoundImageView riv;
    @BindView(R.id.tvMallLocation)
    TextView tvMallLocation;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mall2;
    }

    @Override
    protected void doWork(View view) {
           requestData();
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_MALL_DATA)
                .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                .addParams("type","")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MallDataResponse resp = JsonUtils.parseByGson(response, MallDataResponse.class);
                        if (resp!=null){
//                            tvName.setText();
//                            tvPhone.setText();
//                            tvIdCard.setText();
//                            tvMallName.setText();
//                            tvMallPhone.setText();
//                            tvWorkTime.setText();
//                            tvMallDesc.setText();
//                            tvMallGuiMo.setText();
//                            tvManagerTime.setText();
//                            tvArea.setText();
//                            tvMallLocation.setText();
//                            Glide.with(getActivity()).load().into(riv);
                        }
                    }
                });
    }
}
