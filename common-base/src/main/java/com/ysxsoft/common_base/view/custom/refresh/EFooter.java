package com.ysxsoft.common_base.view.custom.refresh;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ysxsoft.common_base.R;


public class EFooter extends FrameLayout {
	public EFooter(@NonNull Context context) {
		this(context, null);
	}

	public EFooter(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EFooter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	private void init(Context context, AttributeSet attrs) {
		View headerView = View.inflate(context, R.layout.view_loadmore_footer, null);
		ImageView imageView = (ImageView) headerView.findViewById(R.id.loadingIcon);
		Glide.with(context).asGif().load(R.drawable.loading_view2).apply(new RequestOptions()).into(imageView);
		addView(headerView);
	}
}
