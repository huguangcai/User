package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.WaitingListResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.SongCarDetialActivity;
import com.ysxsoft.user.ui.activity.SongCarWaitCheckDetialActivity;
import com.ysxsoft.user.ui.activity.WaitingListDetialActivity;
import com.ysxsoft.user.widget.MyRecyclerview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.Call;

import static com.ysxsoft.user.config.AppConfig.IS_DEBUG_ENABLED;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class AllOrderFragment4 extends BaseFragment implements IListAdapter {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;
    @Override
    public int getLayoutId() {
        return R.layout.include_list;
    }

    @Override
    protected void doWork(View view) {
        initList(view);
    }
    private void initList(View view) {
        manager = new ListManager(this);
        manager.init(view);
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position % 2 == 0) {
                    SongCarDetialActivity.start();
                } else {
                    SongCarWaitCheckDetialActivity.start();
                }
            }
        });
        request(1);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_all_order_layout;
    }

    @Override
    public void request(int page) {
        if (IS_DEBUG_ENABLED) {
            debug(manager);
        } else {
            OkHttpUtils.post()
                    .url(Api.GET_ALL_ORDER)
                    .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
                    .addParams("type", "")
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            WaitingListResponse resp = JsonUtils.parseByGson(response, WaitingListResponse.class);
                            if (resp != null) {
//                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
//                                    List<PlaceListResponse.DataBean> data = resp.getData();
//                                    manager.setData(data);
//                                }else if (HttpResponse.NONE.equals(resp.getCode())){
//                                    if (page==1){
//                                        manager.setData(new ArrayList());
//                                    }
//                                } else {
//                                    //请求失败
//                                    showToast(resp.getMsg());
//                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void fillView(BaseViewHolder helper, Object o) {

        if (helper.getAdapterPosition()%2==0){
            LinearLayout LL1 = helper.getView(R.id.LL1);
            TextView tvWaittingOk = helper.getView(R.id.tvWaittingOk);
            TextView tvLook = helper.getView(R.id.tvLook);
            tvWaittingOk.setVisibility(View.GONE);
            tvLook.setVisibility(View.GONE);
            LL1.setVisibility(View.GONE);
        }else {
            LinearLayout LL1 = helper.getView(R.id.LL1);
            TextView tvWaittingOk = helper.getView(R.id.tvWaittingOk);
            TextView tvLook = helper.getView(R.id.tvLook);
            tvWaittingOk.setVisibility(View.VISIBLE);
            tvLook.setVisibility(View.GONE);
        }


        helper.setText(R.id.nikeName,"订单号:1264897625123");
        helper.setText(R.id.tvStatus,"送车中");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("川湘菜");
        strings.add("豫菜");
        strings.add("新疆菜");
        strings.add("江浙菜");
        RecyclerView recyclerView1 = helper.getView(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        RBaseAdapter<String> adapter1 = new RBaseAdapter<String>(getActivity(), R.layout.item_detail_layout, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView iv = holder.getView(R.id.riv);
                iv.setBackgroundResource(R.mipmap.ic_launcher);
//                helper.setText(R.id.tvName,"");
//                helper.setText(R.id.tvNum,"");
//                helper.setText(R.id.tvGuiGe,"");
                TextView tvGuiGe = holder.getView(R.id.tvGuiGe);
                if (position%2==0){
                    tvGuiGe.setVisibility(View.GONE);
                }else {
                    tvGuiGe.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        recyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                if (helper.getAdapterPosition() % 2 == 0) {
                    SongCarDetialActivity.start();
                } else {
                    SongCarWaitCheckDetialActivity.start();
                }
            }
        });
        helper.setText(R.id.tvDistance, "距客户:" + "  " + "km");
        helper.setText(R.id.tvSum, "共" + "   " + "件，合计");
        helper.setText(R.id.tvMoney, "¥" + "");
        helper.setText(R.id.tvHave_CarTime, "用车时间:" + " ");
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, Object o) {

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
