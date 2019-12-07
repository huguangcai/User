package com.ysxsoft.common_base.view.custom.refresh;


import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.adapter.loadmore.LoadMoreView;

public class ELoadMoreView extends LoadMoreView {
	@Override
	public int getLayoutId() {
		return R.layout.view_loading_footer;
	}

	@Override
	protected int getLoadingViewId() {
		return R.id.load_more_loading_view;
	}

	@Override
	protected int getLoadFailViewId() {
		return R.id.load_more_load_fail_view;
	}

	@Override
	protected int getLoadEndViewId() {
		return R.id.load_more_load_end_view;
	}
}
