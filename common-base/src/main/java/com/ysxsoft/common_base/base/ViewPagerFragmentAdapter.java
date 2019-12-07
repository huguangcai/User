package com.ysxsoft.common_base.base;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private List<String> titles;

	public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
		super(fm);
		this.fragments = fragments;
		this.titles=titles;
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments == null ? 0 : fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		super.destroyItem(container, position, object);
	}
}

///////////////////////////////////////////////////////////////////////////
// 用法
///////////////////////////////////////////////////////////////////////////

//	List<Fragment> fragments = new ArrayList<>();
//		fragments.add(new Tab2Sub1Fragment());
//				fragments.add(new Tab2Sub2Fragment());
//				fragments.add(new Tab2Sub3Fragment());
//				List<String> titles = new ArrayList<>();
//		titles.add("每日推荐");
//		titles.add("TOP100");
//		titles.add("攻略");
//		ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(this.getChildFragmentManager(), fragments, titles);
//		viewPager.setAdapter(adapter);
//		viewPager.setOffscreenPageLimit(2);
//		tabLayout.setupWithViewPager(viewPager);