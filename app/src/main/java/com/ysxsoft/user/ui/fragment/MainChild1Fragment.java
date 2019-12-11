package com.ysxsoft.user.ui.fragment;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.adapter.util.TouchEventUtil;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.LogUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.WaitingListResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.activity.IdentificationActivity;
import com.ysxsoft.user.ui.activity.RefuseCauseActivity;
import com.ysxsoft.user.ui.activity.WaitingListDetialActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

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
public class MainChild1Fragment extends BaseFragment implements IListAdapter {
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
        return R.layout.item_fragment_mainchild1;
    }

    @Override
    public void request(int page) {
        if (IS_DEBUG_ENABLED) {
            debug(manager);
        } else {
            OkHttpUtils.post()
                    .url(Api.GET_WAITING_LIST)
                    .addParams("uid", SharedPreferencesUtils.getUid(getActivity()))
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
        LinearLayout LL1 = helper.getView(R.id.LL1);
        RecyclerView recyclerView1 = helper.getView(R.id.recyclerView1);

        CircleImageView ivHead = helper.getView(R.id.ivHead);
        Glide.with(getActivity()).load("").into(ivHead);
        helper.setText(R.id.nikeName, "");
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("川湘菜");
        strings.add("豫菜");
        strings.add("新疆菜");
        strings.add("江浙菜");
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RBaseAdapter<String> adapter = new RBaseAdapter<String>(getActivity(), R.layout.item_item_fragment_mainchild1, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView iv = holder.getView(R.id.iv);
                iv.setBackgroundResource(R.mipmap.ic_launcher);
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        recyclerView.setAdapter(adapter);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        RBaseAdapter<String> adapter1 = new RBaseAdapter<String>(getActivity(), R.layout.item_detail_layout, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView iv = holder.getView(R.id.riv);
                iv.setBackgroundResource(R.mipmap.ic_launcher);
//                helper.setText(R.id.tvName,"");
//                helper.setText(R.id.tvNum,"");
            }

            @Override
            protected int getViewType(String item, int position) {
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
        helper.setText(R.id.tvDistance, "距客户:" + "  " + "km");
        helper.setText(R.id.tvSum, "共" + "   " + "件，合计");
        helper.setText(R.id.tvMoney, "¥" + "");
        helper.setText(R.id.tvHave_dinner, "用餐时间:" + " ");
        TextView tvRefuse = helper.getView(R.id.tvRefuse);
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefuseCauseActivity.start();
            }
        });
        TextView tvAccept = helper.getView(R.id.tvAccept);
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentificationActivity.start();
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
