package com.ysxsoft.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.SelectStaffResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/11 0011
 */
@Route(path = "/main/SelectStaffActivity")
public class SelectStaffActivity extends BaseActivity {
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
    @BindView(R.id.tvOk)
    TextView tvOk;

    private int click = -1;
    private List<SelectStaffResponse.ResultBean.ListBean> beans;
    private String avater;
    private String id;
    private String name;
    private String phone;
    private String role;

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getSelectStaffActivity()).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        requestData();
    }

    private void requestData() {
        OkHttpUtils.get()
                .url(Api.GET_SELECT_STAFF)
                .addParams("id", SharedPreferencesUtils.getUid(mContext))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SelectStaffResponse resp = JsonUtils.parseByGson(response, SelectStaffResponse.class);
                        if (resp!=null){
                            if (HttpResponse.SUCCESS.equals(resp.getCode())){
                                beans = resp.getResult().getList();
                                initList();
                            }
                        }
                    }
                });

    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("川湘菜");
//        strings.add("豫菜");
//        strings.add("新疆菜");
//        strings.add("江浙菜");

        RBaseAdapter<SelectStaffResponse.ResultBean.ListBean> adapter = new RBaseAdapter<SelectStaffResponse.ResultBean.ListBean>(mContext, R.layout.item_select_staff_layout, beans) {
            @Override
            protected void fillItem(RViewHolder holder, SelectStaffResponse.ResultBean.ListBean item, int position) {
                ImageView iv = holder.getView(R.id.iv);
                if (click == position) {
                    iv.setBackgroundResource(R.mipmap.icon_ok);
                } else {
                    iv.setBackgroundResource(R.mipmap.icon_no);
                }
                CircleImageView civHead = holder.getView(R.id.civHead);
                Glide.with(mContext).load(AppConfig.BASE_URL+item.getAvater()).into(civHead);
                holder.setText(R.id.tvName,item.getName());
                holder.setText(R.id.tvPhone,item.getPhone());
            }

            @Override
            protected int getViewType(SelectStaffResponse.ResultBean.ListBean item, int position) {
                return 0;
            }
        };
        adapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                click = position;
                avater = beans.get(position).getAvater();
                id = beans.get(position).getId();
                name = beans.get(position).getName();
                phone = beans.get(position).getPhone();
                role = beans.get(position).getType();
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("选择员工");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_staff_layout;
    }

    @OnClick({R.id.backLayout, R.id.tvOk})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvOk:
                if (click == -1) {
                    showToast("选择员工不能为空");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("avatar",avater);
                intent.putExtra("id",id);
                intent.putExtra("role",role);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
