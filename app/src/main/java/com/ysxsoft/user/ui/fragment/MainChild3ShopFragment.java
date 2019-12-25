package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.CompletedResponse;
import com.ysxsoft.user.modle.ShopOrderListResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.CompletedListDetialActivity;
import com.ysxsoft.user.ui.activity.CompletedRefuseListDetialActivity;
import com.ysxsoft.user.ui.activity.VIPCompleteDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.Call;

import static com.ysxsoft.user.config.AppConfig.IS_DEBUG_ENABLED;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class MainChild3ShopFragment extends BaseFragment implements IListAdapter<ShopOrderListResponse.ResultBean.ListBean> {
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;

    @Override
    protected void doWork(View view) {
        initTitle();
        initList(view);
    }

    private void initTitle() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        params.height = DisplayUtils.getStatusBarHeight(getActivity());
        statusBar.setLayoutParams(params);
        statusBar.setBackgroundColor(getResources().getColor(R.color.theme_color));
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
//        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("已完成");
    }

    private void initList(View view) {
        manager = new ListManager(this);
        manager.init(view);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopOrderListResponse.ResultBean.ListBean o = (ShopOrderListResponse.ResultBean.ListBean) adapter.getData().get(position);
                switch (o.getOrderStatus()){
                    case "8"://
                        VIPCompleteDetailActivity.start();
                        break;
                    case "9":// 已完成
                        CompletedListDetialActivity.start();
                        break;
                    case "0"://已拒绝
                        CompletedRefuseListDetialActivity.start();
                        break;
                }
            }
        });
        request(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mainchild1;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_shop_fragment_mainchild3;
    }

    @Override
    public void request(int page) {
        if (false) {
            debug(manager);
        } else {
            GetBuilder getBuilder = OkHttpUtils.get();
            switch (SharedPreferencesUtils.getSp(getActivity(), "role")) {
                case "staff":
                    getBuilder.url(Api.GET_STAFF_ORDER_LIST);
                    getBuilder.addParams("staffId", SharedPreferencesUtils.getUid(getActivity()));
                    break;
                case "shop":
                    getBuilder.url(Api.GET_SHOP_ORDER_LIST);
                    getBuilder.addParams("bossId", SharedPreferencesUtils.getUid(getActivity()));
                    break;
            }
            getBuilder.addParams("type", "7")
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            manager.releaseRefresh();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            manager.releaseRefresh();
//                            CompletedResponse resp = JsonUtils.parseByGson(response, CompletedResponse.class);
                            ShopOrderListResponse resp = JsonUtils.parseByGson(response, ShopOrderListResponse.class);
                            if (resp != null) {
                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                请求成功
                                    List<ShopOrderListResponse.ResultBean.ListBean> data = resp.getResult().getList();
                                    manager.setData(data);
                                } else if (HttpResponse.NONE.equals(resp.getCode())) {
                                    if (page == 1) {
                                        manager.setData(new ArrayList());
                                    }
                                } else {
                                    //请求失败
                                    showToast(resp.getMessage());
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void fillView(BaseViewHolder helper, ShopOrderListResponse.ResultBean.ListBean o) {
        LinearLayout LL1 = helper.getView(R.id.LL1);
        LL1.setVisibility(View.GONE);
        RecyclerView recyclerView1 = helper.getView(R.id.recyclerView1);
        recyclerView1.setVisibility(View.VISIBLE);

        helper.setText(R.id.nikeName, "订单号:" + o.getOrderId());
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        RBaseAdapter<ShopOrderListResponse.ResultBean.ListBean.ProductListBean> adapter1 = new RBaseAdapter<ShopOrderListResponse.ResultBean.ListBean.ProductListBean>(getActivity(), R.layout.item_detail_layout, o.getProductList()) {
            @Override
            protected void fillItem(RViewHolder holder, ShopOrderListResponse.ResultBean.ListBean.ProductListBean item, int position) {
                RoundImageView iv = holder.getView(R.id.riv);
                Glide.with(getActivity()).load(item.getImg()).into(iv);
                holder.setText(R.id.tvName, item.getName());
                holder.setText(R.id.tvNum, item.getNumber());
                holder.setText(R.id.tvMoney, item.getPrice());
            }

            @Override
            protected int getViewType(ShopOrderListResponse.ResultBean.ListBean.ProductListBean item, int position) {
                return 0;
            }
        };
        adapter1.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                switch (o.getOrderStatus()) {
                    case "8"://
                        VIPCompleteDetailActivity.start();
                        break;
                    case "9":// 已完成
                        CompletedListDetialActivity.start();
                        break;
                    case "0"://已拒绝
                        CompletedRefuseListDetialActivity.start();
                        break;
                }
            }
        });
        recyclerView1.setAdapter(adapter1);

        TextView tvSumType = helper.getView(R.id.tvSumType);

        tvSumType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.setText(R.id.tvDistance, "距客户:" + o.getDistance() + "km");
        helper.setText(R.id.tvSum, "共" + o.getZnumber() + "件，合计");
        helper.setText(R.id.tvMoney, "¥" + o.getTotal());

        helper.getView(R.id.tvHave_dinner).setVisibility(View.INVISIBLE);

        LinearLayout llshow = helper.getView(R.id.llshow);

        switch (o.getOrderStatus()){
            case "8"://
                llshow.setVisibility(View.GONE);
                break;
             case "9":// 已完成
                 llshow.setVisibility(View.GONE);
                 helper.setText(R.id.tvStatus, "已完成");
                break;
             case "0"://已拒绝
                 llshow.setVisibility(View.VISIBLE);
                 helper.setText(R.id.tvStatus, "已拒绝");
                break;

        }

        TextView tvLookCause = helper.getView(R.id.tvRefuse);
        tvLookCause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (o.getOrderStatus()){
                    case "8"://
                        VIPCompleteDetailActivity.start();
                        break;
                    case "9":// 已完成
                        CompletedListDetialActivity.start();
                        break;
                    case "0"://已拒绝
                        CompletedRefuseListDetialActivity.start();
                        break;
                }
            }
        });
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, ShopOrderListResponse.ResultBean.ListBean o) {

    }

    @Override
    public void attachActivity(AppCompatActivity activity) {

    }

    @Override
    public void dettachActivity() {

    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public boolean isMuteAdapter() {
        return false;
    }

    @Override
    public int[] getMuteTypes() {
        return new int[0];
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[0];
    }
}
