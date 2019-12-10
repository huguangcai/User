package com.ysxsoft.user.ui.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.modle.AboutPlatformResponse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/10 0010
 */
@Route(path = "/main/AboutPlatformActivity")
public class AboutPlatformActivity extends BaseActivity {
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;

    @BindView(R.id.webview)
    WebView webview;


    public static void start() {
        ARouter.getInstance().build(ARouterPath.getAboutPlatformActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webview.setWebViewClient(new MyWebViewClient());
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()
                .url(Api.GET_ABOUTPLATFORM)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AboutPlatformResponse resp = JsonUtils.parseByGson(response, AboutPlatformResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals("0")) {
//                                webview.loadDataWithBaseURL(null, getNewContent(t.data), "text/html", "utf-8", null)
                            }
                        }
                    }
                });
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("关于平台");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_platform_layout;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        // 在WebView中而不在默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    @OnClick({R.id.backLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
        }
    }
}
