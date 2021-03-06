package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.WaittingCarDetialResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/14 0014
 */
@Route(path = "/main/SongCarDetialActivity")
public class SongCarDetialActivity extends BaseActivity {
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
    @BindView(R.id.tvArrive_shop_time)
    TextView tvArrive_shop_time;
    @BindView(R.id.tvSong_car_time)
    TextView tvSong_car_time;

    @BindView(R.id.tvUpLoad)
    TextView tvUpLoad;


    @BindView(R.id.cvServiceInfo)
    CardView cvServiceInfo;
    @BindView(R.id.photoRecycleView)
    RecyclerView photoRecycleView;
    @Autowired
    String orderId;

    public static void start(String orderId) {
        ARouter.getInstance().build(ARouterPath.getSongCarDetialActivity()).withString("orderId",orderId).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.songing_car_detail_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initRecyclerView();
        requestData();
    }

    private void requestData() {
        OkHttpUtils.get()
                .url(Api.GET_SONG_CAR)
                .addParams("id", SharedPreferencesUtils.getUid(mContext))
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
//                            if (HttpResponse.SUCCESS.equals(resp.getCode)){
//                                tvServiceMoney.setText("");
//                                tvNum.setText("");
//                                tvSumMoney.setText(""+"元");
//                                tvCarNum.setText("");
//                                tvHave_dinner.setText("");
//                                tvQuCar.setText("");
//                                tvMark.setText("");
//                                tvLocation.setText("距客户:"+"");
//                                tvEndAdddress.setText("");
//                                tvStartAdddress.setText("");
//                                Glide.with(mContext).load("").into(ivHead);
//                                tvName.setText("");
//                                tvPhone.setText("");
//                                Glide.with(mContext).load("").into(ivHead2);
//                                tvName2.setText("");
//                                tvPhone2.setText("");
//                                tvOrderNum.setText("订单编号："+"");
//                                tvPayTime.setText("下单时间："+"");
//                                tvPayType.setText("支付方式："+"");
//                                tvOrder_Taking_time.setText("接单时间："+"");
//                                tvUpLoad.setText("上传时间："+"");
//                                tvArrive_shop_time.setText("到店时间："+"");
//                                tvSong_car_time.setText("送车时间："+"");
//                            }
                        }
                    }
                });
    }

    private void initRecyclerView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        RBaseAdapter<String> adapter = new RBaseAdapter<String>(mContext, R.layout.item_staff_detail_layout, list) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView riv = holder.getView(R.id.riv);
//                Glide.with(mContext).load("").into(riv);
//                helper.setText(R.id.tvName,"");
//                helper.setText(R.id.tvNum,"");
                TextView tv1 = holder.getView(R.id.tv1);
                if (holder.getAdapterPosition() % 2 == 0) {
                    tv1.setText("服务项目");
                } else {
                    tv1.setText("汽车周边");
                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);


        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list1.add(String.valueOf(i));
        }
        BaseQuickAdapter adapter1 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_photo_layout, list1) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
//                RoundImageView riv = helper.getView(R.id.riv);
//                Glide.with(mContext).load("").into(riv);
            }
        };
        photoRecycleView.setLayoutManager(new GridLayoutManager(mContext, 3));
        photoRecycleView.setAdapter(adapter1);
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
