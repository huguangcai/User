package com.ysxsoft.common_base.base;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;


import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;
import com.ysxsoft.common_base.BuildConfig;
import com.ysxsoft.common_base.config.BaseConfig;
import com.ysxsoft.common_base.helper.BGAGlideImageLoader490;
import com.ysxsoft.common_base.umeng.UMengUtil;
import com.ysxsoft.common_base.view.custom.refresh.ERefreshHeader;

import org.litepal.LitePal;

import cn.bingoogolapple.photopicker.imageloader.BGAImage;

/**
 * create by Sincerly on 2018/12/28 0028
 **/
public class BaseApplication extends Application {
    public static Context context;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ERefreshHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                return new ClassicsFooter(context).setDrawableSize(20);
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);//初始化数据库
        initARouter();
        initUM();
        handle();
        BGAImage.setImageLoader(new BGAGlideImageLoader490());
        // 安装LeakCanary
        //LeakCanary.install(this);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }

    private void initUM() {
        UMengUtil.init(getApplicationContext(), BaseConfig.UMENG_APP_KEY, "Umeng");
        UMengUtil.enableMobclickAgent(BaseConfig.UMENG_SECRET_KEY);//开启统计
        UMengUtil.initWX(BaseConfig.WX_APP_ID, BaseConfig.WX_APP_KEY);//微信appkey
        UMengUtil.initQQ(BaseConfig.QQ_APP_ID, BaseConfig.QQ_APP_KEY);//QQ appkey
        UMengUtil.initSina(BaseConfig.SINA_APP_ID, BaseConfig.SINA_APP_KEY, BaseConfig.SINA_REDIRECT_URL);//sina 新浪
    }

    /**
     * 默认初始化结束后处理
     */
    protected void handle(){
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
