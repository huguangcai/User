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
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.VIPCompleteDetailResponse;
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
 * on 2019/12/14 0014
 */
@Route(path = "/main/VIPCompleteDetailActivity")
public class VIPCompleteDetailActivity extends BaseActivity {
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
    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @BindView(R.id.tvOrderNum)
    TextView tvOrderNum;
    @BindView(R.id.tvPayTime)
    TextView tvPayTime;
    @BindView(R.id.tvPayType)
    TextView tvPayType;
    @BindView(R.id.tvSumMoney)
    TextView tvSumMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getVIPCompleteDetailActivity()).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.staff_completed_vip_detail_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initRecyclerView();
        requestData();
    }

    private void requestData() {
        OkHttpUtils.get()
                .url(Api.GET_STAFF_VIP_COMPLETED_DETAIL)
                .addParams("orderId", "")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VIPCompleteDetailResponse resp = JsonUtils.parseByGson(response, VIPCompleteDetailResponse.class);
                        if (resp != null) {
//                            if (HttpResponse.SUCCESS.equals(resp.getCode)) {
//                                Glide.with(mContext).load("").into(ivHead);
//                                tvName.setText("");
//                                tvPhone.setText("");
//                                tvOrderNum.setText("");
//                                tvPayTime.setText("");
//                                tvPayType.setText("");
//                                tvSumMoney.setText("");
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
                tv1.setVisibility(View.GONE);
//                if (holder.getAdapterPosition() % 2 == 0) {
//                    tv1.setText("服务项目");
//                } else {
//                    tv1.setText("汽车周边");
//                }
            }

            @Override
            protected int getViewType(String item, int position) {
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

    @OnClick({R.id.backLayout})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
        }
    }
}
