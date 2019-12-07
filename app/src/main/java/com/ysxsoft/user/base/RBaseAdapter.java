package com.ysxsoft.user.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class RBaseAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

	private List<T> data;
	private Context context;
	private int layoutId;
	private LayoutInflater inflater;

	public RBaseAdapter(Context c, int layoutId, List<T> data) {
		this.context = c;
		this.layoutId = layoutId;
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RViewHolder holder = null;
		switch (viewType) {
			case 0:
				//正常布局
				holder = new RViewHolder(inflater.inflate(layoutId, parent, false));
				break;
			default:
				break;
		}
		if (holder != null) {
			holder.setType(viewType);
		}
		//瀑布流更改image
		holder = changeImageView(holder);
		return holder;
	}

	protected RViewHolder changeImageView(RViewHolder holder) {
		return holder;
	}

	@Override
	public void onBindViewHolder(RViewHolder holder, int position) {
		T item = getItem(position);
		if (item == null) {
			return;
		}
		fillItem(holder, item, position);
		if (onItemClickListener != null) {
			setOnItemClick(holder, holder.getConvertView(), position);
		}
		if (onItemLongClickListener != null) {
			setOnItemLongClick(holder, holder.getConvertView(), position);
		}
	}

	public T getItemData(int position){
		T d = data.get(position);
		return d;
	}

	@Override
	public int getItemViewType(int position) {
		T d = data.get(position);
		return getViewType(d, position);
	}

	public void setOnItemClick(final RViewHolder holder, final View view, final int position) {
		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClickListener.onItemClick(holder, v, position);
			}
		});
	}

	public void setOnItemLongClick(final RViewHolder holder, final View view, final int position) {
		holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return onItemLongClickListener.onItemLongClick(holder, view, position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	private T getItem(int position) {
		if (data != null && data.size() > 0) {
			return data.get(position);
		}
		return null;
	}

	protected abstract void fillItem(RViewHolder holder, T item, int position);

	protected abstract int getViewType(T item, int position);


	public interface OnItemClickListener {
		void onItemClick(RViewHolder holder, View view, int position);
	}

	public interface onItemLongClickListener {
		boolean onItemLongClick(RViewHolder holder, View view, int position);
	}

	private OnItemClickListener onItemClickListener;
	private onItemLongClickListener onItemLongClickListener;

	public void setOnItemLongClickListener(RBaseAdapter.onItemLongClickListener onItemLongClickListener) {
		this.onItemLongClickListener = onItemLongClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}
}