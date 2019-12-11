package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.dialog.BindAliPayAndWeChatDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.ysxsoft.user.config.AppConfig.IS_DEBUG_ENABLED;

/**
 * Create By 胡
 * on 2019/12/11 0011
 */
@Route(path = "/main/CardCaseActivity")
public class CardCaseActivity extends BaseActivity implements IListAdapter {
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
    @BindView(R.id.tvEd1)
    TextView tvEd1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tvEd2)
    TextView tvEd2;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.ll)
    LinearLayout ll;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    ListManager manager;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getCardCaseActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    private void initList() {
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
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("卡包");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_case_layout;
    }

    @OnClick({R.id.backLayout, R.id.tv1, R.id.tvEd1, R.id.tv2, R.id.tvEd2, R.id.ll, R.id.cv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tv1:
            case R.id.tvEd1:
                BindAliPayAndWeChatDialog dialog = new BindAliPayAndWeChatDialog(mContext);
                EditText name = dialog.findViewById(R.id.name);
                TextView tvTip = dialog.findViewById(R.id.tvTip);
                name.setHint("请输入微信账号");
                tvTip.setText("请绑定微信账号");
                dialog.setListener(new BindAliPayAndWeChatDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String name) {
                        showToast(name);
                    }
                });
                dialog.show();
                break;
            case R.id.tv2:
            case R.id.tvEd2:
                BindAliPayAndWeChatDialog dialog1 = new BindAliPayAndWeChatDialog(mContext);
                EditText name1 = dialog1.findViewById(R.id.name);
                TextView tvTip1 = dialog1.findViewById(R.id.tvTip);
                name1.setHint("请输入支付宝账号");
                tvTip1.setText("请绑定支付宝账号");
                dialog1.setListener(new BindAliPayAndWeChatDialog.OnDialogClickListener() {
                    @Override
                    public void sure(String name) {
                        showToast(name);
                    }
                });
                dialog1.show();
                break;
            case R.id.cv:
            case R.id.ll:
                AddBankActivity.start();
                break;
        }
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_card_case_layout;
    }

    @Override
    public void request(int page) {
        if (IS_DEBUG_ENABLED) {
            debug(manager);
        } else {
            OkHttpUtils.post()
                    .url(Api.GET_CARDCASE_LIST)
//                    .addParams("uid", SharedPreferencesUtils.getUid(MusicListActivity.this))
//                    .addParams("page", String.valueOf(page))
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                            manager.releaseRefresh();
                            showToast(e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
//                            manager.releaseRefresh();
//                            MusicListResponse resp = JsonUtils.parseByGson(response, MusicListResponse.class);
//                            if (resp != null) {
//                                if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                    //请求成功
//                                    datas = resp.getData();
//                                    initList(datas);
////                                    manager.setData(data);
//                                } else {
//                                    //请求失败
//                                    showToast(resp.getMsg());
//                                }
//                            } else {
//                                showToast("获取失败");
//                            }
                        }
                    });
        }
    }

    @Override
    public void fillView(BaseViewHolder helper, Object o) {
        helper.setText(R.id.tvBankName, "交通银行");
        helper.setText(R.id.tvBankNum, "**** **** **** 5268");
        TextView tvEd = helper.getView(R.id.tvEd);
        TextView tvDelete = helper.getView(R.id.tvDelete);
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
        return null;
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
