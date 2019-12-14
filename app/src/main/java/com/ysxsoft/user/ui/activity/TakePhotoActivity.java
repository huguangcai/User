package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By 胡
 * on 2019/12/14 0014
 */
@Route(path = "/main/TakePhotoActivity")
public class TakePhotoActivity extends BaseActivity {
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

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getTakePhotoActivity()).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initList();
    }

    private void initList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
//item_take_photo_layout
        RBaseAdapter<String> adapter = new RBaseAdapter<String>(mContext, R.layout.item_take_photo_layout, list) {
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

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(adapter);
    }

    private void initTitle() {
        bottomLineView.setVisibility(View.GONE);
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("上传照片");
    }

    @OnClick({R.id.backLayout, R.id.tvOk})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvOk:

                break;
        }
    }
}
