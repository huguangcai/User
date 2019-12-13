package com.ysxsoft.common_base.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sincerly on 2018/10/27 0024.
 * {@link #onCreate(Bundle savedInstanceState)}    													生命周期onCreate
 * {@link #onDestroy()}    																			生命周期onDestory
 * {@link #onResume()}    																			生命周期onResume
 * {@link #onPause()}    																			生命周期onPause
 * {@link #preInlate()}    																			setContentView前调用  可用来设置沉浸式，全屏等
 * {@link #doWork()}    																			逻辑处理主方法
 * {@link #goToActivity(Class clazz)}    															跳转至Activity
 * {@link #start(Context context)}    																启动Activity入口
 * {@link #start(Context context, String key, String params)}    									启动Activity入口携带String
 * {@link #start(Context context, Bundle params)}    												启动Activity入口携带Bundle
 * {@link #start(Context context, String key, HashMap map)}    										启动Activity入口携带HashMap
 * {@link #backToActivity()}    																	返回activity
 * {@link #backToResult(Intent intent)}    															返回activity 默认返回RESULT_OK
 * {@link #backToResult(Intent intent, int responseCode)}    										返回activity 自定义ResponseCode
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;
    private boolean isShowing=false;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        mContext=this;
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        preInlate();
        setContentView(getLayoutId());
        if (isTransparentBarStatus()) {
            //设置状态栏透明
            StatusBarUtils.setStatusBarTranslucent(this);
            View view = findViewById(R.id.statusBar);
            if (view != null) {
                view.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getDisplayWidth(this), DisplayUtils.getStatusBarHeight(this)));
            }
        }
        unbinder = ButterKnife.bind(this);
        doWork();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void preCreate() {
        showEnterTransition();
    }

    @Override
    public void preInlate() {
        //可设置全屏
    }

    @Override
    public void doWork() {
        //该类子类必须重写 初始化butterknife bind
        initRefresh();
    }

    @Override
    public void goToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    public void goToActivity(String arouterPath) {
        ARouter.getInstance().build(arouterPath).navigation();
    }

    @Override
    public void start(Context context) {
        Intent intent = new Intent(context, this.getClass());
        startActivity(intent);
    }

    @Override
    public void start(Context context, String key, String params) {
        Intent intent = new Intent(context, this.getClass());
        intent.putExtra(key, params);
        startActivity(intent);
    }

    @Override
    public void start(Context context, Bundle params) {
        Intent intent = new Intent(context, this.getClass());
        intent.putExtras(params);
        startActivity(intent);
    }

    @Override
    public void start(Context context, String key, HashMap params) {
        Intent intent = new Intent(context, this.getClass());
        startActivity(intent);
    }

    @Override
    public void backToActivity() {
        finish();
    }

    @Override
    public void backToResult(Intent intent) {
        backToResult(intent, RESULT_OK);
    }

    @Override
    public void backToResult(Intent intent, int responseCode) {
        setResult(responseCode, intent);
        backToActivity();
    }

    @Override
    public boolean getStatusHeight() {
        return false;//默认是true
    }

    @Override
    public void goToActivityTransation(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
//        overridePendingTransition(R.anim.anim_activity_left_in,R.anim.anim_activity_right_out);
    }

    @Override
    public void showLoading() {
        //多状态布局
        MultipleStatusView multipleStatusView = findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showLoadingDialog() {
        if(isShowing){
            return;
        }
        if (loadingDialog != null) {
        } else {
            loadingDialog = new LoadingDialog(this, R.style.CenterDialogWithoutStyle);
            loadingDialog.setText("请稍后");
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onLoadingDialogDismiss();
                }
            });
        }
        loadingDialog.showDialog();
        isShowing=true;
    }

    @Override
    public void showLoadingDialog(String tips) {
        if(isShowing){
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.setText(tips == null ? "" : tips);
        } else {
            loadingDialog = new LoadingDialog(this, R.style.CenterDialogWithoutStyle);
            loadingDialog.setText(tips == null ? "" : tips);
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onLoadingDialogDismiss();
                }
            });
        }
        loadingDialog.showDialog();
        isShowing=true;
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog=null;
        isShowing=false;
    }

    @Override
    public void showEmpty() {
        //多状态布局
        MultipleStatusView multipleStatusView = findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showEmpty(View.OnClickListener retryListener) {
        MultipleStatusView multipleStatusView = findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.setOnRetryClickListener(retryListener);
                multipleStatusView.showEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showNoNet() {
        //多状态布局
        MultipleStatusView multipleStatusView = findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showNoNetwork();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showNetError() {
        //多状态布局
        MultipleStatusView multipleStatusView = findViewById(R.id.multipleStatusView);
        if (multipleStatusView != null) {
            try {
                multipleStatusView.showError();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showToast(String text) {
        ToastUtils.shortToast(this, text);
    }

    @Override
    public boolean isTransparentBarStatus() {
        //顶部状态栏默认透明状态
        return true;
    }

    @Override
    public void initRefresh() {
        View view = findViewById(R.id.smartRefresh);
        if (view != null) {
            RefreshLayout refreshLayout = (RefreshLayout) view;
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout r) {
                    initRefresh(r);
                }
            });
        }
    }

    /**
     * 刷新回调
     *
     * @param refreshlayout
     */
    @Override
    public void initRefresh(RefreshLayout refreshlayout) {
    }

    public View createEmptyView() {
        View view = View.inflate(this, R.layout.empty_view, null);
        return view;
    }

    @Override
    public void onLoadingDialogDismiss() {
        //弹出框消失
    }

    @Override
    public void showEnterTransition() {
        if (isShowTransition()) {//是否开启动画
            int[] enterAnim = getEnterTransitionAnimResource();
            overridePendingTransition(enterAnim[0], enterAnim[1]);
        }
    }

    @Override
    public boolean isShowTransition() {
        return false;
    }

    @Override
    public void showEixtTransition() {
        if (isShowTransition()) {
            int[] exitAnim = getExitTransitionAnimResource();
            overridePendingTransition(exitAnim[0], exitAnim[1]);
        }
    }

    @Override
    public int[] getEnterTransitionAnimResource() {
        return new int[]{R.anim.anim_activity_right_out, R.anim.anim_activity_left_in};
    }

    @Override
    public int[] getExitTransitionAnimResource() {
        return new int[]{R.anim.anim_activity_left_out, R.anim.anim_activity_right_in};
    }

    @Override
    public void finish() {
        super.finish();
        showEixtTransition();
    }

    public void toLogin(){
        ARouter.getInstance().build("/main/LoginActivity").navigation();
        AppManager.getAppManager().finishAllActivity();
        SharedPreferencesUtils.saveUid(this,"");
        SharedPreferencesUtils.saveToken(this,"");
        SharedPreferencesUtils.deleteSp(mContext);
        SharedPreferencesUtils.saveHasPwd(BaseActivity.this,false);//支付密码
    }

    public void debug(ListManager manager){
        List<String> a=new ArrayList<>();
        for (int i = 0; i <10; i++) {
            a.add("数据"+i);
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

//    public void showPic(String... pic){
//        ArrayList<String> list=new ArrayList<>();
//        for (int i = 0; i <pic.length; i++) {
//            list.add(pic[i]);
//        }
//        PicUtils.startPrew(this,list,0);
//    }

    protected abstract int getLayoutId();
}
