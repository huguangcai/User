package com.ysxsoft.user.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.banner.BannerLayout;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.CookerDetailResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.ysxsoft.user.config.AppConfig.IS_DEBUG_ENABLED;

/**
 * Create By 胡
 * on 2019/12/10 0010
 */
@Route(path = "/main/CookerDetailActivity")
public class CookerDetailActivity extends BaseActivity implements IListAdapter {
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

    @BindView(R.id.clLayayout)
    ConstraintLayout clLayayout;
    @BindView(R.id.ivHead)
    CircleImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCookerLevel)
    TextView tvCookerLevel;
    @BindView(R.id.tvServiceMoney)
    TextView tvServiceMoney;
    @BindView(R.id.recycler)
    BannerLayout recycler;
    @BindView(R.id.ivEvaluate)
    ImageView ivEvaluate;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tabRecyclerView)
    RecyclerView tabRecyclerView;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    private ListManager<String> manager;

    private int click = 0;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.multipleStatusView)
//    MultipleStatusView multipleStatusView;
//    @BindView(R.id.smartRefresh)
//    SmartRefreshLayout smartRefresh;
//    ListManager manager;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getCookerDetailActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        iv2.setBackgroundResource(R.mipmap.icon_service_ok);
        iv3.setBackgroundResource(R.mipmap.icon_song_no);
        tv2.setTextColor(getResources().getColor(R.color.theme_color));
        tv3.setTextColor(getResources().getColor(R.color.color_cccccc));
        view2.setBackgroundColor(getResources().getColor(R.color.theme_color));
        view3.setBackgroundColor(getResources().getColor(R.color.transparent));
        initTitle();
        initList();
    }

    private void initList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("川湘菜");
        strings.add("豫菜");
        strings.add("新疆菜");
        strings.add("江浙菜");
        strings.add("福建菜");
        strings.add("湖北菜");
        strings.add("粤菜");
        strings.add("湘菜");
        strings.add("淮扬菜");
        strings.add("杭州菜");


        RBaseAdapter recycleradapter = new RBaseAdapter<String>(mContext, R.layout.item_view_recyclerview, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                CircleImageView civHead = holder.getView(R.id.civHead);
                holder.setText(R.id.tvName,"西冷牛排意面套餐");
                holder.setText(R.id.tvTypeName,"#牛排");
                holder.setText(R.id.tvSales,"月售298份 | 厨师推荐");
                holder.setText(R.id.tvMoney,"￥49");
                ImageView ivAdd = holder.getView(R.id.ivAdd);
                ivAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(item+">>>>>>>>"+position);
                    }
                });
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        recycler.setAdapter(recycleradapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        tabRecyclerView.setLayoutManager(layoutManager);
        RBaseAdapter adapter = new RBaseAdapter<String>(mContext, R.layout.item_tab_recyclerview, strings) {

            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                holder.setText(R.id.tab, item);
//                TextView t = holder.getView(R.id.tab);
//                if (true) {
//                    t.setSelected(true);
//                } else {
//                    t.setSelected(false);
//                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        tabRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
 //                for (int i = 0; i < data.size(); i++) {
//                    MainCateGoryResponse.DataBean.TwoBean menu = data.get(position);
//                    if (i == position) {
//                        data.get(i).setSelected(true);
//                        subId = menu.getCid();
//                    } else {
//                        data.get(i).setSelected(false);
//                    }
//                }
//                adapter.notifyDataSetChanged();
            }
        });


        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //setResult(RESULT_OK, intent);
                //finish();
            }
        });
        manager.getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                request(manager.nextPage());
            }
        }, recyclerView);
        request(1);
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setTextColor(getResources().getColor(R.color.color_282828));
        title.setText("厨师详情");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cooker_detail_layout;
    }

    @OnClick({R.id.clLayayout, R.id.ll2, R.id.ll3, R.id.ivEvaluate, R.id.backLayout})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.clLayayout:
                CookerDataActivity.start();
                break;
            case R.id.ll2:
                iv2.setBackgroundResource(R.mipmap.icon_service_ok);
                iv3.setBackgroundResource(R.mipmap.icon_song_no);
                tv2.setTextColor(getResources().getColor(R.color.theme_color));
                tv3.setTextColor(getResources().getColor(R.color.color_cccccc));
                view2.setBackgroundColor(getResources().getColor(R.color.theme_color));
                view3.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case R.id.ll3:
                iv2.setBackgroundResource(R.mipmap.icon_service_no);
                iv3.setBackgroundResource(R.mipmap.icon_song_ok);
                tv2.setTextColor(getResources().getColor(R.color.color_cccccc));
                tv3.setTextColor(getResources().getColor(R.color.theme_color));
                view2.setBackgroundColor(getResources().getColor(R.color.transparent));
                view3.setBackgroundColor(getResources().getColor(R.color.theme_color));
                break;
            case R.id.ivEvaluate:
                break;
        }

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_cooker_detail_layout;
    }

    @Override
    public void request(int page) {
        if (IS_DEBUG_ENABLED) {
            debug(manager);
        } else {
            OkHttpUtils.post()
                    .url(Api.GET_COOKER_DETIAL)
                    .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CookerDetailResponse resp = JsonUtils.parseByGson(response, CookerDetailResponse.class);
                            if (resp != null) {
//                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                //请求成功
//                                List<CookerDetailResponse.DataBean> data = resp.getData().getData();
//                                manager.setData(data);
//                            } else if (HttpResponse.NONE.equals(resp.getCode())){
//                                if (page==1){
//                                    manager.setData(new ArrayList<>());
//                                }
//                            }else {
//                                //请求失败
//                                showToast(resp.getMsg());
//                            }
//                        } else {
//                            showToast("获取失败");
//                        }
                            }
                        }
                    });
        }
    }

    @Override
    public void fillView(BaseViewHolder helper, Object o) {
//        RoundImageView riv = helper.getView(R.id.riv);
//        Glide.with(mContext).load("").into(riv);
//        helper.setText(R.id.tvName,"");
//        helper.setText(R.id.tvSales,"月销量200份");
//        helper.setText(R.id.tvMoney,"￥49");
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
        return new GridLayoutManager(mContext, 2);
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
