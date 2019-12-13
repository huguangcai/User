package com.ysxsoft.user.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
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
public class MallFragment1 extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mall1;
    }

    @Override
    protected void doWork(View view) {
        tabLayout.removeAllTabs();
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("汽车美容");
        titles.add("汽车保养");
        titles.add("汽车周边");
        fragmentList.add(new MallChild1Fragment1());
        fragmentList.add(new MallChild1Fragment2());
        fragmentList.add(new MallChild1Fragment3());
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

    private void initTabLayout(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.view_tab);
            TextView textView = tab.getCustomView().findViewById(R.id.tab);
//            textView.setWidth(DisplayUtils.getDisplayWidth(mContext) * 1 / 3);
            textView.setText(titles.get(i));
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorWhite));
                textView.setBackgroundResource(R.drawable.shape_theme_btn);
                textView.setPadding(10,5,10,5);
                textView.setTextSize(13);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_282828));
                textView.setBackgroundResource(getResources().getColor(R.color.transparent));
                textView.setTextSize(13);
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
            tv.setTextSize(13);
            tv.setPadding(10,5,10,5);
            tv.setBackgroundResource(R.drawable.shape_theme_btn);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (tab.getCustomView() == null) {
                return;
            }
            TextView tv = tab.getCustomView().findViewById(R.id.tab);
            tv.setTextSize(13);
            tv.setBackgroundResource(getResources().getColor(R.color.transparent));
            tv.setTextColor(getResources().getColor(R.color.color_282828));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

}
