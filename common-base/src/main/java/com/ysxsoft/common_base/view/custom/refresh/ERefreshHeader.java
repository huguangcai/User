package com.ysxsoft.common_base.view.custom.refresh;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.ysxsoft.common_base.R;

public class ERefreshHeader extends FrameLayout implements RefreshHeader {
	public ERefreshHeader(@NonNull Context context) {
		this(context, null);
	}

	public ERefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ERefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	TextView s;
	private void init(Context context, AttributeSet attrs) {
		View headerView = View.inflate(context, R.layout.view_loading, null);
		ImageView imageView = (ImageView) headerView.findViewById(R.id.loadingIcon);
		s=headerView.findViewById(R.id.s);
		Glide.with(context).asGif().load(R.drawable.loading_view2).apply(new RequestOptions()).into(imageView);
		addView(headerView);
	}

	@NonNull
	@Override
	public View getView() {
		return this;
	}

	@NonNull
	@Override
	public SpinnerStyle getSpinnerStyle() {
		return SpinnerStyle.Translate;
	}

	@Override
	public void setPrimaryColors(int... colors) {
		setBackground(getResources().getDrawable(R.drawable.bg_blue_gradient));
	}

	@Override
	public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

	}

	@Override
	public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

	}

	@Override
	public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

	}

	@Override
	public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

	}

	@Override
	public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
		return 0;
	}

	@Override
	public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

	}

	@Override
	public boolean isSupportHorizontalDrag() {
		return false;
	}

	@Override
	public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
		switch (newState) {
			case None: // 无状态
				break;
			case PullDownToRefresh: // 可以下拉状态
				s.setText("下拉即可刷新");
				break;
			case Refreshing: // 刷新中状态
				s.setText("正在刷新数据...");
				break;
			case ReleaseToRefresh:  // 释放就开始刷新状态
				s.setText("松开立即刷新...");
				break;
		}
	}
}
