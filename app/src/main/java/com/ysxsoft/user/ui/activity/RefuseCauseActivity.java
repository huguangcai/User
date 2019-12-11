package com.ysxsoft.user.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
@Route(path = "/main/RefuseCauseActivity")
public class RefuseCauseActivity extends BaseActivity {
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
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.tvSumType)
    TextView tvSumType;
    @BindView(R.id.tvSelect)
    TextView tvSelect;
    @BindView(R.id.etMark)
    EditText etMark;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.LL1)
    LinearLayout LL1;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getRefuseCauseActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    private void initList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("川湘菜");
        strings.add("豫菜");
        strings.add("新疆菜");
        strings.add("江浙菜");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        RBaseAdapter<String> adapter = new RBaseAdapter<String>(mContext, R.layout.item_item_fragment_mainchild1, strings) {
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

        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        RBaseAdapter<String> adapter1 = new RBaseAdapter<String>(mContext, R.layout.item_detail_layout, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView iv = holder.getView(R.id.iv);
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

    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("拒绝原因");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refuse_cause_layout;
    }

    @OnClick({R.id.backLayout, R.id.tvSumType, R.id.ok, R.id.tvSelect,})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvSumType:
                showToast("点击菜品");
                break;
            case R.id.tvSelect:
                showToast("选择拒绝原因");
                break;
            case R.id.ok:
                if (TextUtils.isEmpty(etMark.getText().toString().trim())) {
                    showToast("拒绝原因不能为空");
                    return;
                }
                submitData();
                break;
        }
    }

    private void submitData() {
        OkHttpUtils.post()
                .url(Api.GET_REFUSE_CAUSE)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .addParams("content", etMark.getText().toString().trim())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommonResonse resp = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
                                finish();
                            }
                        }
                    }
                });
    }

}
