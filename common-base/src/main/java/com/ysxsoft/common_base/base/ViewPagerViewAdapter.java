package com.ysxsoft.common_base.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public  abstract class ViewPagerViewAdapter<T> extends PagerAdapter {
	private List<T> data;
	private List<String> titles;
	private LayoutInflater inflater;
	private int layoutId;

	public ViewPagerViewAdapter(Context context, List<T> data, int layoutId) {
		this.data = data;
		this.layoutId=layoutId;
		inflater=LayoutInflater.from(context);
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return data==null?0:data.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = inflater.inflate(layoutId, container, false);
		fillView(view,position,data.get(position));
		container.addView(view);
		return view;
	}

	@Override
	public String getPageTitle(int position) {
		return titles==null?super.getPageTitle(position).toString():titles.get(position);
	}
	protected abstract void fillView(View view,int position,T data);
}