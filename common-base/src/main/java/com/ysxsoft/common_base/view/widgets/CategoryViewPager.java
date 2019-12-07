package com.ysxsoft.common_base.view.widgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.utils.DisplayUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页分类菜单
 */
public class CategoryViewPager extends LinearLayout {
    private List<Item> data = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private ViewPager viewPager;
    private int menuPageCount = 8;
    private int col = 4;
    private int totalPage;
    private OnMenuClickListener listener;
    private Context mContext;

    public CategoryViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CategoryViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
    }

    public void setData(List<Item> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        this.data = data;
        removeAllViews();
        views.clear();
        pointViews.clear();
        //1.添加viewpager
        viewPager = new ViewPager(mContext);
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        addView(viewPager);
        //分拣列表数据
        Map<Integer, List<Item>> map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int p = i / menuPageCount;
            List<Item> list = map.get(p);
            if (list == null) {
                list = new ArrayList<>();
                list.add(data.get(i));
                map.put(p, list);
            } else {
                list.add(data.get(i));
            }
        }
        totalPage = map.size();
        int height = 0;
        for (int i = 0; i < map.size(); i++) {
            List<Item> menuChildData = map.get(i);
            View view = View.inflate(mContext, R.layout.view_category, null);
            RecyclerView recyclerView = view.findViewById(R.id.baseCategoryRecyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, col));
            CustomAdapter adapter = new CustomAdapter(R.layout.view_category_menu);
            recyclerView.setAdapter(adapter);
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (listener != null) {
                        Item item = (Item) adapter.getItem(position);
                        listener.OnMenuClick(item.getId(),item.getName(), position);
                    }
                }
            });
            adapter.setNewData(menuChildData);
            recyclerView.measure(0, 0);
            height = Math.max(height, recyclerView.getMeasuredHeight());
            views.add(view);
        }
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        viewPager.setAdapter(new CagegoryViewPagerAdapter(views));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changePoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //2.添加point
        initPoint(totalPage);
    }

    private void changePoint(int position) {
        for (int i = 0; i < pointViews.size(); i++) {
            ImageView imageView = pointViews.get(i);
            imageView.setEnabled(i == position);
        }
    }

    private List<ImageView> pointViews = new ArrayList<>();

    private void initPoint(int totalPage) {
        //至少1页时显示
        if(totalPage>1){
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            //添加原点
            for (int i = 0; i < totalPage ; i++) {
                ImageView imageView = new ImageView(mContext);
                LayoutParams p = new LayoutParams(DisplayUtils.dp2px(mContext, 8), DisplayUtils.dp2px(mContext, 8));
                p.gravity = Gravity.CENTER;
                p.leftMargin=DisplayUtils.dp2px(mContext, 2);
                p.topMargin=DisplayUtils.dip2px(mContext,2);
                p.bottomMargin=DisplayUtils.dip2px(mContext,2);
                imageView.setLayoutParams(p);
                imageView.setBackgroundResource(R.drawable.bg_category_menu_point);
                imageView.setEnabled(i == 0);
                pointViews.add(imageView);
                linearLayout.addView(imageView);
            }
            addView(linearLayout);
        }
    }

    private class CustomAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {
        public CustomAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Item item) {
            //菜单icon
            ImageView imageView = helper.getView(R.id.baseCategoryImageView);
            Glide.with(mContext).load(item.getIcon()).apply(new RequestOptions().error(R.drawable.icon_product_menu1_test)).into(imageView);
            //菜单名称
            TextView textView = helper.getView(R.id.baseCategoryTextView);
            item.getIcon();
            textView.setText(item.getName());
        }
    }

    public void reset() {
    }

    public class CagegoryViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public CagegoryViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return (views.get(position));
        }

        @Override
        public int getCount() {
            if (views == null)
                return 0;
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 数据实体
     */
    public static class Item {
        private String id;
        private String icon;
        private String name;

        public Item(String id, String icon, String name) {
            this.id = id;
            this.icon = icon;
            this.name = name;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon == null ? "" : icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public OnMenuClickListener getListener() {
        return listener;
    }

    public void setListener(OnMenuClickListener listener) {
        this.listener = listener;
    }

    public interface OnMenuClickListener {
        void OnMenuClick(String id, String title, int position);
    }
}
