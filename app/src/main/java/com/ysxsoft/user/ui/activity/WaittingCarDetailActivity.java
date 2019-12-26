package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.WaitCarDetialResponse;
import com.ysxsoft.user.modle.WaittingCarDetialResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/14 0014
 */
@Route(path = "/main/WaittingCarDetailActivity")
public class WaittingCarDetailActivity extends BaseActivity {

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
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.tvCarNum)
    TextView tvCarNum;
    @BindView(R.id.tvQuCar)
    TextView tvQuCar;
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
    @BindView(R.id.ivHead2)
    CircleImageView ivHead2;
    @BindView(R.id.tvName2)
    TextView tvName2;
    @BindView(R.id.tvPhone2)
    TextView tvPhone2;

    @BindView(R.id.vipRecyclerView)
    RecyclerView vipRecyclerView;

    @Autowired
    String orderId;

    public static void start(String orderId) {
        ARouter.getInstance().build(ARouterPath.getWaittingCarDetialActivity()).withString("orderId",orderId).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waitting_car_detail_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        requestData();
    }

    private void requestData() {
        OkHttpUtils.get()
                .url(Api.GET_WAITTING_CAR_DETAIL)
                .addParams("orderId", orderId)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WaittingCarDetialResponse resp = JsonUtils.parseByGson(response, WaittingCarDetialResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())){
                                tvServiceMoney.setText(resp.getResult().getFee());
//                                tvNum.setText("");
                                tvSumMoney.setText(resp.getResult().getTotal()+"元");
                                tvCarNum.setText(resp.getResult().getCarNumber());
                                tvHave_dinner.setText(resp.getResult().getUseTime());
                                tvQuCar.setText(resp.getResult().getTakeCarTime());
                                tvMark.setText(resp.getResult().getRemark());
                                tvLocation.setText("距客户:"+resp.getResult().getDistance());
                                tvEndAdddress.setText(resp.getResult().getEndAddress());
                                tvStartAdddress.setText(resp.getResult().getBeginAddress());
                                Glide.with(mContext).load(AppConfig.BASE_URL+resp.getResult().getAvater()).into(ivHead);
                                tvName.setText(resp.getResult().getUsername());
                                tvPhone.setText(resp.getResult().getPhone());
                                Glide.with(mContext).load(AppConfig.BASE_URL+resp.getResult().getShopAvater()).into(ivHead2);
                                tvName2.setText(resp.getResult().getShopname());
                                tvPhone2.setText(resp.getResult().getMobile());
                                tvOrderNum.setText("订单编号："+resp.getResult().getOrderId());
                                tvPayTime.setText("下单时间："+resp.getResult().getCreateTime());
                                switch (resp.getResult().getPayType()) {
                                    case 1:
                                        tvPayType.setText("支付方式：" + "支付宝");
                                        break;
                                    case 2:
                                        tvPayType.setText("支付方式：" + "微信");
                                        break;
                                }
                                tvOrder_Taking_time.setText("接单时间："+resp.getResult().getTakeOrderTime());
                                initRecyclerView(resp.getResult().getCardList(),resp.getResult().getOrderList());
                            }
                        }
                    }
                });

    }

    private void initRecyclerView(List<String> cardList, List<WaittingCarDetialResponse.ResultBean.OrderListBean> orderList) {
        vipRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RBaseAdapter<String> rBaseAdapter = new RBaseAdapter<String>(mContext, R.layout.item_vip_layout, cardList) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                holder.setText(R.id.tv1,item);
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        vipRecyclerView.setAdapter(rBaseAdapter);

        RBaseAdapter<WaittingCarDetialResponse.ResultBean.OrderListBean> adapter = new RBaseAdapter<WaittingCarDetialResponse.ResultBean.OrderListBean>(mContext, R.layout.item_staff_detail_layout, orderList) {
            @Override
            protected void fillItem(RViewHolder holder, WaittingCarDetialResponse.ResultBean.OrderListBean item, int position) {
                RoundImageView riv = holder.getView(R.id.riv);
                Glide.with(mContext).load(AppConfig.BASE_URL + item.getImg()).into(riv);
                holder.setText(R.id.tvName, item.getName());
                holder.setText(R.id.tvNum, "x " + item.getNumber());
                TextView tv1 = holder.getView(R.id.tv1);
                if (item.getType().equals("2")) {
                    tv1.setText("服务项目");
                } else {
                    tv1.setText("汽车周边");
                }
            }

            @Override
            protected int getViewType(WaittingCarDetialResponse.ResultBean.OrderListBean item, int position) {
                return 0;
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

    @OnClick({R.id.backLayout, R.id.tvAccept})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvAccept:
                TakePhotoActivity.start("");
                break;
        }
    }
}
