package com.ysxsoft.user.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysxsoft.common_base.base.BaseFragment;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.user.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

/**
 * 员工工作中
 * Create By 胡
 * on 2019/12/7 0007
 */
public class MainChild2Fragment1 extends BaseFragment {

    @BindView(R.id.topView)
    View topView;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.FL1)
    FrameLayout FL1;
    @BindView(R.id.FL2)
    FrameLayout FL2;
    @BindView(R.id.FL3)
    FrameLayout FL3;
    @BindView(R.id.FL4)
    FrameLayout FL4;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.tvPoint1)
    TextView tvPoint1;
    @BindView(R.id.tvPoint2)
    TextView tvPoint2;
    @BindView(R.id.tvPoint3)
    TextView tvPoint3;
    @BindView(R.id.tvPoint4)
    TextView tvPoint4;
    private Fragment currentFragment = new Fragment();//（全局）
    private MainChild2Tab1Fragment tab1 = new MainChild2Tab1Fragment();
    private MainChild2Tab2Fragment tab2 = new MainChild2Tab2Fragment();
    private MainChild2Tab3Fragment tab3 = new MainChild2Tab3Fragment();
    private MainChild2Tab4Fragment tab4 = new MainChild2Tab4Fragment();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mainchild2_fragment1;
    }

    @Override
    protected void doWork(View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) topView.getLayoutParams();
        params.height = DisplayUtils.getStatusBarHeight(getActivity());
        topView.setLayoutParams(params);

        tv1.setTextSize(18);
        tv2.setTextSize(15);
        tv3.setTextSize(15);
        tv4.setTextSize(15);

        line1.setVisibility(View.VISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
        line4.setVisibility(View.INVISIBLE);
        initPoint();
    }

    private void initPoint() {
        new QBadgeView(getActivity())
                .setBadgeBackgroundColor(Color.WHITE)
                .setBadgeTextColor(Color.RED)
                .bindTarget(tvPoint1)
                .setBadgeNumber(10099);
        new QBadgeView(getActivity())
                .setBadgeBackgroundColor(Color.WHITE)
                .setBadgeTextColor(Color.RED)
                .bindTarget(tvPoint2)
                .setBadgeNumber(99);
        new QBadgeView(getActivity())
                .setBadgeBackgroundColor(Color.WHITE)
                .setBadgeTextColor(Color.RED)
                .bindTarget(tvPoint3)
                .setBadgeNumber(99);
        new QBadgeView(getActivity())
                .setBadgeBackgroundColor(Color.WHITE)
                .setBadgeTextColor(Color.RED)
                .bindTarget(tvPoint4)
                .setBadgeNumber(99);

    }

    @OnClick({R.id.FL1, R.id.FL2, R.id.FL3, R.id.FL4})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.FL1:
                tv1.setTextSize(18);
                tv2.setTextSize(15);
                tv3.setTextSize(15);
                tv4.setTextSize(15);

                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);

                switchFragment(tab1).commit();
                break;
            case R.id.FL2:

                tv1.setTextSize(15);
                tv2.setTextSize(18);
                tv3.setTextSize(15);
                tv4.setTextSize(15);

                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);

                switchFragment(tab2).commit();
                break;
            case R.id.FL3:
                tv1.setTextSize(15);
                tv2.setTextSize(15);
                tv3.setTextSize(18);
                tv4.setTextSize(15);

                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.INVISIBLE);
                switchFragment(tab3).commit();
                break;
            case R.id.FL4:
                tv1.setTextSize(15);
                tv2.setTextSize(15);
                tv3.setTextSize(15);
                tv4.setTextSize(18);

                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.VISIBLE);
                switchFragment(tab4).commit();
                break;
        }
    }

    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
//第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fl_content, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }
}
