package com.ysxsoft.common_base.ui.welcome;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import butterknife.BindView;
import okhttp3.Call;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.umeng.commonsdk.debug.I;
import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.bean.SplashResponse;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * create by 胡 on 2019/1/3 0003
 **/
public class SplashActivity extends BaseActivity {

    private ImageView splashImage;

    @Override
    public void doWork() {
        super.doWork();
        splashImage = findViewById(R.id.splashImage);
//        if (!TextUtils.isEmpty(SharedPreferencesUtils.getUid(SplashActivity.this))) {
//            OkHttpUtils.post()
//                    .url("http://eat.ysxapp.com/admin.php/api/login/iscomplete")
//                    .addParams("uid", SharedPreferencesUtils.getUid(SplashActivity.this))
//                    .tag(this)
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            SplashResponse res = JsonUtils.parseByGson(response, SplashResponse.class);
//                            if (res != null) {
//                                if (HttpResponse.SUCCESS.equals(res.getCode())) {
//                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if ("0".equals(res.getData())) {
//                                                //未登录 跳转到登录页面  Tips:宿主工程必须依赖 annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
//                                                ARouter.getInstance().build("/main/PerfectInformationActivity").withString("uid",SharedPreferencesUtils.getUid(SplashActivity.this)).navigation();
////                                               ARouter.getInstance().build("/main/MainActivity").navigation();
//                                            } else {
//                                                //已登录 跳转到主页
//                                                ARouter.getInstance().build("/main/MainActivity").navigation();
//                                            }
////                                  }else{
////                                       ARouter.getInstance().build("/main/GuideActivity").navigation();//跳转到引导页
////                                   }
//                                            finish();
//                                        }
//                                    }, 500);
//                                }
//                            }
//                        }
//                    });
//
//        } else {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //未登录 跳转到登录页面  Tips:宿主工程必须依赖 annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
                ARouter.getInstance().build("/main/LoginActivity").navigation();
                finish();
            }
        }, 500);
//        }
//        request();
    }

    private void request() {
        OkHttpUtils.post()
                .url("http://eat.ysxapp.com/admin.php/api/login/qi")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SplashResponse res = JsonUtils.parseByGson(response, SplashResponse.class);
                        if (res != null) {
                            if ("0".equals(res.getCode())) {
                                Glide.with(SplashActivity.this).load(res.getData()).into(splashImage);
                            }
                        }
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}
