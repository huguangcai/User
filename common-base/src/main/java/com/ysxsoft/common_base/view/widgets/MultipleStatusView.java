package com.ysxsoft.common_base.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ysxsoft.common_base.R;


public class MultipleStatusView extends RelativeLayout {
	public static final int STATUS_CONTENT = 0x00;
	public static final int STATUS_LOADING = 0x01;
	public static final int STATUS_EMPTY = 0x02;
	public static final int STATUS_ERROR = 0x03;
	public static final int STATUS_NO_NETWORK = 0x04;

	private static final int NULL_RESOURCE_ID = -1;
	private final ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	private View mEmptyView;
	private View mErrorView;
	private View mLoadingView;
	private View mNoNetworkView;
	private View mContentView;
	private View mEmptyRetryView;
	private View mErrorRetryView;
	private View mNoNetworkRetryView;
	private int mEmptyViewResId;
	private int mErrorViewResId;
	private int mLoadingViewResId;
	private int mNoNetworkViewResId;
	private int mContentViewResId;
	private int mViewStatus;
	private LayoutInflater mInflater;
	private OnClickListener mOnRetryClickListener;

	public MultipleStatusView(Context context) {
		this(context, null);
	}

	public MultipleStatusView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		final TypedArray a =
				context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);

		mEmptyViewResId =
				a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view);
		mErrorViewResId =
				a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.error_view);
		mLoadingViewResId =
				a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.loading_view);
		mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView,
				R.layout.no_network_view);
		mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView,
				NULL_RESOURCE_ID);
		a.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mInflater = LayoutInflater.from(getContext());
		showContent();
	}

	/**
	 * 显示内容视图
	 */
	public final void showContent() {
//        mViewStatus = STATUS_CONTENT;
//        if (null == mContentView) {
//            if (mContentViewResId != NULL_RESOURCE_ID) {
//                mContentView = mInflater.inflate(mContentViewResId, null);
//                addView(mContentView, 0, mLayoutParams);
//            } else {
//                mContentView = findViewById(R.id.content_view);
//            }
//        }
//        showViewByStatus(mViewStatus);
	}

	public int getViewStatus() {
		return mViewStatus;
	}

	/**
	 * 设置重试点击事件
	 *
	 * @param onRetryClickListener 重试点击事件
	 */
	public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
		this.mOnRetryClickListener = onRetryClickListener;
	}

	public final void showEmpty() {
		mViewStatus = STATUS_EMPTY;
		if (null == mEmptyView) {
			mEmptyView = mInflater.inflate(mEmptyViewResId, null);
			mEmptyRetryView = mEmptyView.findViewById(R.id.iv_empty_view);
			if (null != mOnRetryClickListener && null != mEmptyRetryView) {
				mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
			}
			addView(mEmptyView, 1, mLayoutParams);
		}
		showViewByStatus(mViewStatus);
	}

	public final void hideEmpty(){
		showViewByStatus(STATUS_CONTENT);
	}

	private void showViewByStatus(int viewStatus) {
		if (null != mLoadingView) {
			mLoadingView.setVisibility(viewStatus == STATUS_LOADING ? View.VISIBLE : View.GONE);
		}
		if (null != mEmptyView) {
			mEmptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE : View.GONE);
		}
		if (null != mErrorView) {
			mErrorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE : View.GONE);
		}
		if (null != mNoNetworkView) {
			mNoNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE : View.GONE);
		}
		if (null != mContentView) {
			mContentView.setVisibility(viewStatus == STATUS_CONTENT ? View.VISIBLE : View.GONE);
			mContentView.setVisibility(View.VISIBLE);
		}
	}

	public final void showEmpty(View view) {
		if (view == null) {
			return;
		}
		mViewStatus = STATUS_EMPTY;
		if (null == mEmptyView) {
			mEmptyView = view;
			mEmptyRetryView = mEmptyView.findViewById(R.id.empty_view_tv);
			if (null != mOnRetryClickListener && null != mEmptyRetryView) {
				mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
			}
			addView(mEmptyView, 1, mLayoutParams);
		}
		showViewByStatus(mViewStatus);
	}

	/**
	 * 显示错误视图
	 */
	public final void showError() {
		mViewStatus = STATUS_ERROR;
		if (null == mErrorView) {
			mErrorView = mInflater.inflate(mErrorViewResId, null);
			mErrorRetryView = mErrorView.findViewById(R.id.error_view_tv);
			if (null != mOnRetryClickListener && null != mErrorRetryView) {
				mErrorRetryView.setOnClickListener(mOnRetryClickListener);
			}
			addView(mErrorView, 1, mLayoutParams);
		}
		showViewByStatus(mViewStatus);
	}

	public final void showLoading() {
		mViewStatus = STATUS_LOADING;
		if (null == mLoadingView) {
			mLoadingView = mInflater.inflate(mLoadingViewResId, null);
			addView(mLoadingView, 1, mLayoutParams);
			ImageView imageView = (ImageView) mLoadingView.findViewById(R.id.iv_loading);
			Glide.with(getContext()).asGif().load(R.drawable.loading_view2).apply(new RequestOptions()).into(imageView);
		}
		showViewByStatus(mViewStatus);
	}

	public final void hideLoading() {
		if (null != mLoadingView && mViewStatus == STATUS_LOADING) {
			mViewStatus = STATUS_CONTENT;
			if (mLoadingView != null) {
				try {
					mLoadingView.setVisibility(View.GONE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final void showNoNetwork() {
		mViewStatus = STATUS_NO_NETWORK;
		if (null == mNoNetworkView) {
			mNoNetworkView = mInflater.inflate(mNoNetworkViewResId, null);
			mNoNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_view_tv);
			if (null != mOnRetryClickListener && null != mNoNetworkRetryView) {
				mNoNetworkRetryView.setOnClickListener(mOnRetryClickListener);
			}
			addView(mNoNetworkView, 1, mLayoutParams);
		}
		showViewByStatus(mViewStatus);
	}

}
