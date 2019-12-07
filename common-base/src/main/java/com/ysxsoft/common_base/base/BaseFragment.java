package com.ysxsoft.common_base.base;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.utils.AppManager;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.StatusBarUtils;
import com.ysxsoft.common_base.utils.ToastUtils;
import com.ysxsoft.common_base.view.widgets.LoadingDialog;
import com.ysxsoft.common_base.view.widgets.MultipleStatusView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create by Sincerly on 2018/12/28 0028
 **/
public abstract class BaseFragment extends Fragment {
    protected Bundle args;
    RefreshLayout refreshLayout;
    private Unbinder unbinder;
    private View view;
    private boolean isRefreshing = false;
    private LoadingDialog loadingDialog;
    private boolean isShowing=false;

    public static <T extends Fragment> T newInstance(Class clazz, Bundle args) {
        T mFragment = null;
        try {
            mFragment = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);//绑定framgent
        unbinder = ButterKnife.bind(this, view);
        doWork(view);
        init();
        initRefresh(view);//初始化刷新
        parse(savedInstanceState);
        initStatusBar(view);
        return view;
    }

    private void initStatusBar(View view) {
        View v=view.findViewById(R.id.statusBar);
        if(v!=null){
            view.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getDisplayWidth(getActivity()), StatusBarUtils.getStatusBarHeight(getActivity())));
        }
    }

    public abstract int getLayoutId();

    protected abstract void doWork(View view);

    public void init() {
    }

    public void parse(Bundle savedInstanceState){
    }

    private void initRefresh(View v) {
        View view = v.findViewById(R.id.smartRefresh);
        if (view != null) {
//            refreshLayout = (RefreshLayout) view;
//            refreshLayout.setEnableLoadMore(false);//禁用加载更多
//            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//                @Override
//                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                    initRefresh(refreshLayout);
//                }
//            });
        }
    }

    public void showToast(String text) {
        ToastUtils.shortToast(getActivity(), text);
//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 刷新回调
     *
     * @param refreshlayout
     */
    public void initRefresh(RefreshLayout refreshlayout) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    public void goToActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    public void goToActivity(String arouterPath) {
        ARouter.getInstance().build(arouterPath).navigation();
    }

    public void goToActivityWithBundle(Class<?> c, Bundle bundle) {
        Intent intent = new Intent(getActivity(), c);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goToActivityByTransition(Intent i, View view, int id) {
        View sharedView = view;
        String transitionName = getResources().getString(id);
        ActivityOptions transitionActivityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
            startActivity(i, transitionActivityOptions.toBundle());
        } else {
            startActivity(i);
        }
    }

    protected void goToActivityByTransition(Intent i, View view, String transitionName) {
        View sharedView = view;
        ActivityOptions transitionActivityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
            startActivity(i, transitionActivityOptions.toBundle());
        } else {
            startActivity(i);
        }
    }

    public void backToActivity() {
        getActivity().finish();
//        getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void showLoading(String text) {

    }

    public void hideLoading() {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.hideLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void backActivity() {
        getActivity().finish();
    }

    public void goToActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    public void start(Context context) {
        Intent intent = new Intent(context, this.getClass());
        context.startActivity(intent);
    }

    public void setImage(ImageView imageView, String url) {
        if ("".equals(url)) {
            return;
        }
    }

    /**
     * 显示空视图
     */
    public void showEmpty() {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示空视图
     */
    public void showEmpty(View.OnClickListener retryListener) {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.setOnRetryClickListener(retryListener);
                multipleStatusView.showEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideEmpty(){
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.hideEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showLoading() {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showNoNetwork() {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showNoNetwork();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showError() {
        if (view == null) {
            return;
        }
        MultipleStatusView multipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showError();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showLoadingDialog() {
        if(isShowing){
            return;
        }
            loadingDialog = new LoadingDialog(getActivity(), R.style.CenterDialogWithoutStyle);
            loadingDialog.setText("请稍后");
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onLoadingDialogDismiss();
                }
            });
        loadingDialog.showDialog();
        isShowing=true;
    }

    public void showLoadingDialog(String tips) {
        if(isShowing){
            return;
        }
            loadingDialog = new LoadingDialog(getActivity(), R.style.CenterDialogWithoutStyle);
            loadingDialog.setText(tips == null ? "" : tips);
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onLoadingDialogDismiss();
                }
            });
        loadingDialog.showDialog();
        isShowing=true;
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.showDialog();
        }
        isShowing=false;
    }

    /**
     * 创建空视图
     * @return
     */
    public View createEmptyView(){
        View view=View.inflate(getActivity(),R.layout.empty_view,null);
        return view;
    }

    /**
     * 刷新完成
     */
    public void refreshEnd(RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
    }

    public void goToLogin(){
//        AppManager.getAppManager().finishAllActivity();
//        SharedPreferencesUtils.saveUid(getActivity(),"");
//        SharedPreferencesUtils.saveAvatar(getActivity(),"");
//        SharedPreferencesUtils.saveSex(getActivity(),"");
        toLogin();
    }

    public boolean enableSmartRefreshLoadMore(){
        return false;
    }

    private void onLoadingDialogDismiss(){
        //弹窗关闭
    }

    public void toLogin(){
        ARouter.getInstance().build("/main/LoginActivity").navigation();
        AppManager.getAppManager().finishAllActivity();
        SharedPreferencesUtils.saveUid(getActivity(),"");
//        ARouter.getInstance().build("/main/LoginActivity").navigation();
    }

    public void debug(ListManager manager){
        List<String> a=new ArrayList<>();
        for (int i = 0; i <10; i++) {
            a.add(a+"");
        }
        manager.setData(a);
        //测试数据  2s自动关闭下拉刷新
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.releaseRefresh();
            }
        }, 3000);
    }
}
