package com.ysxsoft.user.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.modle.RefuseCauseDataResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.dialog.SelectOnePicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    String orderId;

    private ArrayList arrayList=new ArrayList<String>();

    public static void start(String orderId) {
        ARouter.getInstance().build(ARouterPath.getRefuseCauseActivity()).withString("orderId", orderId).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        switch (SharedPreferencesUtils.getSp(mContext, "role")) {
            case "staff":
            case "shop":
                requestData("1");
                break;
            case "chef ":
                requestData("2");
                break;
        }
    }

    private void requestData(String type) {
        OkHttpUtils.get()
                .url(Api.GET_REFUSE_CAUSE_DATA)
                .addParams("orderId", orderId)
                .addParams("type", type)
                .tag(this)
                .build()
                .execute(new StringCallback() {

                    private List<RefuseCauseDataResponse.ResultBean.ReasonsBean> reasons;

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        RefuseCauseDataResponse parse = JsonUtils.parseByGson(response, RefuseCauseDataResponse.class);
                        if (parse != null) {
                            if (HttpResponse.SUCCESS.equals(parse.getCode())) {
                                reasons = parse.getResult().getReasons();
                                for (int i = 0; i < reasons.size(); i++) {
                                    arrayList.add(reasons.get(i).getReason());
                                }

                                initList(parse.getResult());
                            }
                        }
                    }
                });
    }

    private void initList(RefuseCauseDataResponse.ResultBean result) {

        switch (SharedPreferencesUtils.getSp(mContext, "role")) {
            case "staff":
            case "shop":
                recyclerView1.setVisibility(View.VISIBLE);
                LL1.setVisibility(View.GONE);
                recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
                RBaseAdapter<RefuseCauseDataResponse.ResultBean.ListBean> adapter1 = new RBaseAdapter<RefuseCauseDataResponse.ResultBean.ListBean>(mContext, R.layout.item_detail_layout, result.getList()) {
                    @Override
                    protected void fillItem(RViewHolder holder, RefuseCauseDataResponse.ResultBean.ListBean item, int position) {
                        RoundImageView iv = holder.getView(R.id.riv);
                        Glide.with(mContext).load(AppConfig.BASE_URL+item.getImg()).into(iv);
                        holder.setText(R.id.tvName, item.getTitle());
                        holder.setText(R.id.tvNum, "x"+item.getNumber());
                        holder.setText(R.id.tvMoney, item.getPrice());
                    }

                    @Override
                    protected int getViewType(RefuseCauseDataResponse.ResultBean.ListBean item, int position) {
                        return 0;
                    }
                };
                recyclerView1.setAdapter(adapter1);

                break;
            case "chef ":
                recyclerView1.setVisibility(View.GONE);
                LL1.setVisibility(View.VISIBLE);
                tvSumType.setText("共" + result.getList().size() + "个菜品");
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                RBaseAdapter<RefuseCauseDataResponse.ResultBean.ListBean> adapter = new RBaseAdapter<RefuseCauseDataResponse.ResultBean.ListBean>(mContext, R.layout.item_item_fragment_mainchild1, result.getList()) {
                    @Override
                    protected void fillItem(RViewHolder holder, RefuseCauseDataResponse.ResultBean.ListBean item, int position) {
                        RoundImageView iv = holder.getView(R.id.iv);
                        Glide.with(mContext).load(AppConfig.BASE_URL+item.getImg()).into(iv);
                    }

                    @Override
                    protected int getViewType(RefuseCauseDataResponse.ResultBean.ListBean item, int position) {
                        return 0;
                    }
                };
                recyclerView.setAdapter(adapter);
                break;
        }

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
                SelectOnePicker onePicker = new SelectOnePicker(mContext);
                onePicker.setData(arrayList,0,"请选择拒绝原因");
                TextView title1 = onePicker.findViewById(R.id.t);
                title1.setText("请选择拒绝原因");
                onePicker.setListener(new SelectOnePicker.OnDialogClickListener() {
                    @Override
                    public void sure(String data1, int position1) {
                        tvSelect.setText(data1);
                    }
                });
                onePicker.show();
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
        PostFormBuilder url = OkHttpUtils.post().url(Api.GET_REFUSE_CAUSE);
        url.addParams("orderId", SharedPreferencesUtils.getUid(mContext));
        url.addParams("remark", etMark.getText().toString().trim());
        url.addParams("reason", tvSelect.getText().toString());
        switch (SharedPreferencesUtils.getUid(mContext)) {
            case "staff":
            case "shop":
                url.addParams("type", "1");
                break;
            case "chef ":
                url.addParams("type", "2");
                break;
        }
        url.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommonResonse resp = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (resp != null) {
                            showToast(resp.getMessage());
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                finish();
                            }
                        }
                    }
                });
    }

}
