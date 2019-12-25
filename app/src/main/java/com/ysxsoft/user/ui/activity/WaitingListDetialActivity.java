package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.WaitingListDetialResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/8 0008
 */
@Route(path = "/main/WaitingListDetialActivity")
public class WaitingListDetialActivity extends BaseActivity {
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

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.ivHead)
    CircleImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvServiceMoney)
    TextView tvServiceMoney;
    @BindView(R.id.tvSumMoney)
    TextView tvSumMoney;
    @BindView(R.id.tvHave_dinner)
    TextView tvHave_dinner;
    @BindView(R.id.tvServiceMethod)
    TextView tvServiceMethod;
    @BindView(R.id.tvMark)
    TextView tvMark;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvEndAdddress)
    TextView tvEndAdddress;
    @BindView(R.id.tvStartAdddress)
    TextView tvStartAdddress;
    @BindView(R.id.tvOrderNum)
    TextView tvOrderNum;
    @BindView(R.id.tvPayTime)
    TextView tvPayTime;
    @BindView(R.id.tvPayType)
    TextView tvPayType;
    @BindView(R.id.tvRefuse)
    TextView tvRefuse;
    @BindView(R.id.tvAccept)
    TextView tvAccept;
    @BindView(R.id.tvOrder_Taking_time)
    TextView tvOrder_Taking_time;
    @BindView(R.id.llshow)
    LinearLayout llshow;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getWaitingListDetialActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initRecyclerView();
//        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_WAITING_LIST_DETIAL)
                .addParams("orderId", "")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaitingListDetialResponse resp = JsonUtils.parseByGson(response, WaitingListDetialResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
                                tvStartAdddress.setText("大学科技园东区");
                                tvEndAdddress.setText("农业路天明路交叉口路东100米某某大厦");
                                tvLocation.setText("距客户:12km");
                                tvOrderNum.setText("订单编号：118896325578");
                                tvPayTime.setText("下单时间：2019-10-26 12:26:25");
                                tvPayType.setText("支付方式：支付宝");
                                tvOrder_Taking_time.setText("接单时间：2019-10-26 12:26:25");
                            }
                        }
                    }
                });

    }

    private void initRecyclerView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_detail_layout, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
//                RoundImageView riv = helper.getView(R.id.riv);
//                Glide.with(mContext).load("").into(riv);
//                helper.setText(R.id.tvName,"");
//                helper.setText(R.id.tvNum,"");
            }
        };
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

    }

    private void initTitle() {
        bottomLineView.setVisibility(View.GONE);
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("订单详情");
    }

    @OnClick({R.id.backLayout, R.id.tvRefuse, R.id.tvAccept})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvRefuse:
                RefuseCauseActivity.start("");
                break;
            case R.id.tvAccept:
                IdentificationActivity.start("");
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_list_detail_layout;
    }
}
