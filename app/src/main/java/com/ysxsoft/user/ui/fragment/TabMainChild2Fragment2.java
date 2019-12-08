package com.ysxsoft.user.ui.fragment;

import android.os.HandlerThread;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.PreparingResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.IdentificationActivity;
import com.ysxsoft.user.ui.activity.RefuseCauseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.Call;

import static com.ysxsoft.user.config.AppConfig.IS_DEBUG_ENABLED;

/**
 * Create By 胡
 * on 2019/12/8 0008
 */
public class TabMainChild2Fragment2 extends BaseFragment implements IListAdapter {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;

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

            }
        });
        request(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_main_child2_fragment1;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_fragment_tab_main_child2_fragment2;
    }

    @Override
    public void request(int page) {
        if (IS_DEBUG_ENABLED) {
            debug(manager);
        } else {
            OkHttpUtils.post()
                    .url(Api.GET_PREPARING)
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
                            PreparingResponse resp = JsonUtils.parseByGson(response, PreparingResponse.class);
                            if (resp != null) {
//                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
//                                    List<PreparingResponse.DataBean> data = resp.getData();
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
        CircleImageView ivHead = helper.getView(R.id.ivHead);
        Glide.with(getActivity()).load("").into(ivHead);
        helper.setText(R.id.nikeName, "");
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_item_fragment_mainchild1) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                RoundImageView iv = helper.getView(R.id.iv);
                Glide.with(getActivity()).load("").into(iv);
            }
        };
        recyclerView.setAdapter(adapter);
        TextView tvSumType = helper.getView(R.id.tvSumType);
        tvSumType.setText("共" + "  " + "菜品");
        tvSumType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("菜品详情");
            }
        });
        helper.setText(R.id.tvDistance, "距客户:" + "  " + "km");
        helper.setText(R.id.tvSum, "共" + "   " + "件，合计");
        helper.setText(R.id.tvMoney, "¥" + "");
        TextView tvHave_dinner = helper.getView(R.id.tvHave_dinner);
        helper.setText(R.id.tvHave_dinner, "等待客户确认");
        TextView tvAccept = helper.getView(R.id.tvAccept);
        if (helper.getAdapterPosition()% 2 == 0){
            tvHave_dinner.setVisibility(View.INVISIBLE);
            tvAccept.setVisibility(View.VISIBLE);
        }else {
            tvHave_dinner.setVisibility(View.VISIBLE);
            tvAccept.setVisibility(View.GONE);
        }
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
