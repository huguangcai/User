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
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.ShopOrderListResponse;
import com.ysxsoft.user.modle.WaitingListResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.IdentificationActivity;
import com.ysxsoft.user.ui.activity.RefuseCauseActivity;
import com.ysxsoft.user.ui.activity.WaitingListDetialActivity;
import com.zhy.http.okhttp.OkHttpUtils;
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
public class MainChild1ShopFragment extends BaseFragment implements IListAdapter<ShopOrderListResponse.ResultBean.ListBean> {
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
    public int getLayoutId() {
        return R.layout.fragment_mainchild1;
    }

    @Override
    protected void doWork(View view) {
        initTitle();
        initList(view);
    }

    private void initTitle() {
//        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
//                .reset()  //重置所以沉浸式参数
//                .init();  //必须调用方可应用以上所配置的参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        params.height = DisplayUtils.getStatusBarHeight(getActivity());
        statusBar.setLayoutParams(params);
        statusBar.setBackgroundColor(getResources().getColor(R.color.theme_color));
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
//        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("待接单");
    }

    private void initList(View view) {
        manager = new ListManager(this);
        manager.init(view);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WaitingListDetialActivity.start();
            }
        });
        request(1);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_shop_fragment_mainchild1;
    }

    @Override
    public void request(int page) {
        if (false) {
            debug(manager);
        } else {
            OkHttpUtils.get()
                    .url(Api.GET_SHOP_ORDER_LIST)
                    .addParams("bossId", SharedPreferencesUtils.getUid(getActivity()))
                    .addParams("type", "2")
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
                            ShopOrderListResponse resp = JsonUtils.parseByGson(response, ShopOrderListResponse.class);
                            if (resp != null) {
                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                请求成功
                                    List<ShopOrderListResponse.ResultBean.ListBean> data = resp.getResult().getList();
                                    manager.setData(data);
                                }else if (HttpResponse.NONE.equals(resp.getCode())){
                                    if (page==1){
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


        helper.setText(R.id.nikeName, "订单号:"+o.getOrderId());
//        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("川湘菜");
//        strings.add("豫菜");
//        strings.add("新疆菜");
//        strings.add("江浙菜");
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
//        RBaseAdapter<String> adapter = new RBaseAdapter<String>(getActivity(), R.layout.item_item_fragment_mainchild1, strings) {
//            @Override
//            protected void fillItem(RViewHolder holder, String item, int position) {
//                RoundImageView iv = holder.getView(R.id.iv);
//                iv.setBackgroundResource(R.mipmap.ic_launcher);
//            }
//
//            @Override
//            protected int getViewType(String item, int position) {
//                return 0;
//            }
//        };
//        recyclerView.setAdapter(adapter);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        RBaseAdapter<ShopOrderListResponse.ResultBean.ListBean.ProductListBean> adapter1 = new RBaseAdapter<ShopOrderListResponse.ResultBean.ListBean.ProductListBean>(getActivity(), R.layout.item_detail_layout, o.getProductList()) {
            @Override
            protected void fillItem(RViewHolder holder, ShopOrderListResponse.ResultBean.ListBean.ProductListBean item, int position) {
                RoundImageView iv = holder.getView(R.id.riv);
                holder.setText(R.id.tvName,item.getName());
                holder.setText(R.id.tvNum,"x"+item.getNumber());
                holder.setText(R.id.tvMoney,"¥"+item.getPrice());
                Glide.with(getActivity()).load(AppConfig.BASE_URL+item.getImg()).into(iv);
            }

            @Override
            protected int getViewType(ShopOrderListResponse.ResultBean.ListBean.ProductListBean item, int position) {
                return 0;
            }
        };
        recyclerView1.setAdapter(adapter1);


        TextView tvSumType = helper.getView(R.id.tvSumType);
        tvSumType.setText("共" + "  " + "菜品");
        tvSumType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("菜品详情");
            }
        });
        helper.setText(R.id.tvDistance, "距客户:" + o.getDistance() + "km");
        helper.setText(R.id.tvSum, "共" + o.getZnumber() + "件，合计");
        helper.setText(R.id.tvMoney, "¥" +o.getTotal());
        helper.setText(R.id.tvHave_dinner, "用车时间:" + o.getUseTime());
        TextView tvRefuse = helper.getView(R.id.tvRefuse);
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefuseCauseActivity.start(o.getOrderId());
            }
        });
        TextView tvAccept = helper.getView(R.id.tvAccept);
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentificationActivity.start(o.getOrderId());
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
