package com.ysxsoft.user;


import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.ViewPagerFragmentAdapter;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.widgets.NoScrollViewPager;
import com.ysxsoft.user.ui.fragment.MainChild1Fragment;
import com.ysxsoft.user.ui.fragment.MainChild2Fragment;
import com.ysxsoft.user.ui.fragment.MainChild2Fragment1;
import com.ysxsoft.user.ui.fragment.MainChild3Fragment;
import com.ysxsoft.user.ui.fragment.MainChild3Fragment1;
import com.ysxsoft.user.ui.fragment.MainChild4Fragment;
import com.ysxsoft.user.ui.fragment.MainChild5Fragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private boolean isBack = false;

    @BindView(R.id.rg_home)
    RadioGroup rg_home;
    @BindView(R.id.rb_wait)
    RadioButton rb_wait;
    @BindView(R.id.rb_work)
    RadioButton rb_work;
    @BindView(R.id.rb_complete)
    RadioButton rb_complete;
    @BindView(R.id.rb_sale)
    RadioButton rb_sale;
    @BindView(R.id.rb_my)
    RadioButton rb_my;
    private Fragment currentFragment = new Fragment();//（全局）
    private MainChild1Fragment tab1 = new MainChild1Fragment();
    private MainChild2Fragment tab2 = new MainChild2Fragment();
    private MainChild3Fragment tab3 = new MainChild3Fragment();
    private MainChild4Fragment tab4 = new MainChild4Fragment();
    private MainChild5Fragment tab5 = new MainChild5Fragment();
    @Autowired
    String role;

    public static void start(String role) {
        ARouter.getInstance().build(ARouterPath.getMainActivity()).withString("role", role).navigation();
    }

    @SuppressLint("CheckResult")
    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        ImmersionBar.with(this).init();
        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECORD_AUDIO
                ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {

                } else {
                    showToast("未授权,部分功能无法使用");
                }
            }
        });
//        initBottomNavigationView();
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
//        if ("staff".equals(role)) {
        if ("staff".equals("staff")) {
            fragmentList.add(new MainChild2Fragment1());//员工 工作中
            fragmentList.add(new MainChild3Fragment1());//员工 已完成
            fragmentList.add(new MainChild5Fragment());//员工 个人中心
            rb_wait.setVisibility(View.GONE);
            rb_sale.setVisibility(View.GONE);

            viewPager.setCurrentItem(0);
            rg_home.check(R.id.rb_work);
            rg_home.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_work:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.rb_complete:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.rb_my:
                            viewPager.setCurrentItem(2);
                            break;
                    }
                }
            });
            viewPager.setOffscreenPageLimit(3);
        }else {
            fragmentList.add(new MainChild1Fragment());
            fragmentList.add(new MainChild2Fragment());
            fragmentList.add(new MainChild3Fragment());
            fragmentList.add(new MainChild4Fragment());
            fragmentList.add(new MainChild5Fragment());

            viewPager.setCurrentItem(0);
            rg_home.check(R.id.rb_wait);
            rg_home.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_wait:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.rb_work:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.rb_complete:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.rb_sale:
                            viewPager.setCurrentItem(3);
                            break;
                        case R.id.rb_my:
                            viewPager.setCurrentItem(4);
                            break;
                    }
                }
            });
            viewPager.setOffscreenPageLimit(5);
        }


        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList, new ArrayList<String>()));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                viewPager.setCurrentItem(position);
////                bottomNavigationView.getMenu().getItem(position).setChecked(true);
////                resetMenu(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });
//      resetMenu(0)
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
//                if ("staff".equals(role)) {
                switch (menuItem.getItemId()) {
                    case R.id.menu1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu4:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.menu5:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 切换菜单
     *
     * @param position
     */
    private void resetMenu(int position) {
        if (bottomNavigationView != null) {
//            if ("staff".equals(role)) {
            if ("staff".equals("staff")) {

                bottomNavigationView.getMenu().getItem(0).setIcon(position == 0 ? R.mipmap.icon_tab1_selected : R.mipmap.icon_tab1_normal);
                bottomNavigationView.getMenu().getItem(1).setIcon(position == 1 ? R.mipmap.icon_tab2_selected : R.mipmap.icon_tab2_normal);
                bottomNavigationView.getMenu().getItem(2).setIcon(position == 2 ? R.mipmap.icon_tab3_selected : R.mipmap.icon_tab3_normal);
                bottomNavigationView.getMenu().getItem(3).setIcon(position == 3 ? R.mipmap.icon_tab4_selected : R.mipmap.icon_tab4_normal);
                bottomNavigationView.getMenu().getItem(4).setIcon(position == 4 ? R.mipmap.icon_tab5_selected : R.mipmap.icon_tab5_normal);

                bottomNavigationView.getMenu().findItem(R.id.menu1).setVisible(false);
                bottomNavigationView.getMenu().findItem(R.id.menu4).setVisible(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setIcon(position == 0 ? R.mipmap.icon_tab1_selected : R.mipmap.icon_tab1_normal);
                bottomNavigationView.getMenu().getItem(1).setIcon(position == 1 ? R.mipmap.icon_tab2_selected : R.mipmap.icon_tab2_normal);
                bottomNavigationView.getMenu().getItem(2).setIcon(position == 2 ? R.mipmap.icon_tab3_selected : R.mipmap.icon_tab3_normal);
                bottomNavigationView.getMenu().getItem(3).setIcon(position == 3 ? R.mipmap.icon_tab4_selected : R.mipmap.icon_tab4_normal);
                bottomNavigationView.getMenu().getItem(4).setIcon(position == 4 ? R.mipmap.icon_tab5_selected : R.mipmap.icon_tab5_normal);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isBack) {
                finish();
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出!", Toast.LENGTH_SHORT).show();
                isBack = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBack = false;
                    }
                }, 3000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
