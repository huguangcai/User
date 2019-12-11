package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.WalletResponse;
import com.ysxsoft.user.net.Api;
import com.ysxsoft.user.ui.fragment.TabMainChild2Fragment1;
import com.ysxsoft.user.ui.fragment.TabMainChild2Fragment2;
import com.ysxsoft.user.ui.fragment.WalletFragment1;
import com.ysxsoft.user.ui.fragment.WalletFragment2;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/11 0011
 */
@Route(path = "/main/WalletActivity")
public class WalletActivity extends BaseActivity {
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

    @BindView(R.id.LL1)
    LinearLayout LL1;
    @BindView(R.id.LL2)
    LinearLayout LL2;
    @BindView(R.id.tvSumMoney)
    TextView tvSumMoney;
    @BindView(R.id.tvCurrentMoney)
    TextView tvCurrentMoney;
    @BindView(R.id.tvTx)
    TextView tvTx;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getWalletActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        tabLayout.removeAllTabs();
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("收益明细");
        titles.add("提现明细");
        fragmentList.add(new WalletFragment1());
        fragmentList.add(new WalletFragment2());
        initViewPage(fragmentList, titles);
        initTabLayout(titles);
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_WALLET)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WalletResponse resp = JsonUtils.parseByGson(response, WalletResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
//                                tvSumMoney.setText();
//                                tvCurrentMoney.setText();
                            }
                        }
                    }
                });
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.transparent));
        backLayout.setVisibility(View.VISIBLE);
        bottomLineView.setVisibility(View.GONE);
        back.setImageResource(R.mipmap.icon_white_back);
        rightWithIcon.setTextColor(getResources().getColor(R.color.colorWhite));
        rightWithIcon.setText("卡包");
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("钱包");
    }

    @OnClick({R.id.backLayout, R.id.rightWithIcon, R.id.LL1, R.id.LL2, R.id.tvTx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.rightWithIcon:
                CardCaseActivity.start();
                break;
            case R.id.LL1:
                break;
            case R.id.LL2:
                break;
            case R.id.tvTx:
                TxActivity.start();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_layout;
    }

    private void initViewPage(List<Fragment> fragmentList, List<String> titles) {
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList, titles));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    private void initTabLayout(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.view_tab);
            TextView textView = tab.getCustomView().findViewById(R.id.tab);
//            textView.setWidth(DisplayUtils.getDisplayWidth(mContext) * 1 / 3);
            textView.setText(titles.get(i));
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.theme_color));
                textView.setTextSize(18);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_282828));
                textView.setTextSize(15);
            }
        }
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getCustomView() == null) {
                return;
            }
            TextView tv = tab.getCustomView().findViewById(R.id.tab);
            tv.setTextSize(18);
            tv.setTextColor(getResources().getColor(R.color.theme_color));
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (tab.getCustomView() == null) {
                return;
            }
            TextView tv = tab.getCustomView().findViewById(R.id.tab);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(R.color.color_282828));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

}
