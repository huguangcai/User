package com.ysxsoft.common_base.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {

    public static void init(WebView webView){
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//把html中的内容放大webview等宽的一列中
        //设置不缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setNeedInitialFocus(true);
    }

    public static void initX5(WebView webView){
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//把html中的内容放大webview等宽的一列中
        //设置不缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setNeedInitialFocus(true);
    }

    public static void setData(WebView webView,String content){
        if(content==null){
            return;
        }
        webView.loadData(content, "text/html;charset=UTF-8", null);
    }

    public static void setH5Data(WebView webView,String h5_Str) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=0\">\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body>\n");
        stringBuffer.append(h5_Str);
        stringBuffer.append("</body></html>");
//        h5_Str="<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=0\">";
        h5_Str=stringBuffer.toString();
        h5_Str = h5_Str.replace("<img", "<img style=\"display:block;\" width=\"100%\"")
                .replace("<p", "<p style=\"margin:0\"");
        webView.loadDataWithBaseURL(null, h5_Str,"text/html", "utf-8", null);
    }

    /**
     *
     * @param webView
     * @param content
     * @param oldChar
     * @param newChar
     */
    public static void setDataReplace(WebView webView,String content,String oldChar,String newChar){
        if(content==null){
            return;
        }
        content=content.replace(oldChar,newChar);
        webView.loadData(content, "text/html;charset=UTF-8", null);
    }
}
