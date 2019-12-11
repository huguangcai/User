package com.ysxsoft.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

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

    public static void start(Activity activity, int requestCode) {
        ARouter.getInstance().build(ARouterPath.getSelectStaffActivity()).navigation(activity, requestCode);
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();

        initList();
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ArrayList<String> strings = new ArrayList<>();
        strings.add("川湘菜");
        strings.add("豫菜");
        strings.add("新疆菜");
        strings.add("江浙菜");

        RBaseAdapter<String> adapter = new RBaseAdapter<String>(mContext, R.layout.item_select_staff_layout, strings) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                ImageView iv = holder.getView(R.id.iv);
                if (click == position) {
                    iv.setBackgroundResource(R.mipmap.icon_ok);
                } else {
                    iv.setBackgroundResource(R.mipmap.icon_no);
                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };
        adapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                click = position;
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
                intent.putExtra("name","");
                intent.putExtra("phone","");
                intent.putExtra("avatar","");
                intent.putExtra("id","");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
