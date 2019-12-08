package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.user.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Create By 胡
 * on 2019/12/7 0007
 */
public class MainChild2Fragment extends BaseFragment {
    @BindView(R.id.topView)
    View topView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void doWork(View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) topView.getLayoutParams();
        params.height = DisplayUtils.getStatusBarHeight(getActivity());
        topView.setLayoutParams(params);

        tabLayout.removeAllTabs();
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("准备中");
        titles.add("服务中");
        fragmentList.add(new TabMainChild2Fragment1());
        fragmentList.add(new TabMainChild2Fragment2());
        initViewPage(fragmentList, titles);
        initTabLayout(titles);
    }

    private void initViewPage(List<Fragment> fragmentList, List<String> titles) {
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragmentList, titles));
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mainchild2;
    }
    private void initTabLayout(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.view_tab);
            TextView textView = tab.getCustomView().findViewById(R.id.tab);
            textView.setText(titles.get(i));
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorWhite));
                textView.setTextSize(18);
            } else {
                textView.setTextColor(getResources().getColor(R.color.colorWhite));
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
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (tab.getCustomView() == null) {
                return;
            }
            TextView tv = tab.getCustomView().findViewById(R.id.tab);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

}
