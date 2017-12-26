package com.byjw.webviewbrowser.Presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.byjw.webviewbrowser.Custom.MyDownloadListener;
import com.byjw.webviewbrowser.Custom.MyWebChromeClient;
import com.byjw.webviewbrowser.Custom.MyWebViewClient;

/**
 * Created by jungwoon on 2017. 12. 21..
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private WebView webView;
    private Context context;
    private Activity activity;

    public MainPresenter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init() {
        view.setGoPageListener();

        MyWebViewClient myWebViewClient = new MyWebViewClient(view, context);
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient(view, activity);
        MyDownloadListener myDownloadListener = new MyDownloadListener(context, activity);

        // WebView에 WebViewClient 세팅
        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(myWebChromeClient);
        webView.setDownloadListener(myDownloadListener);

        // WebView ProgressBar Settings
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        WebSettings webViewSettings = webView.getSettings();

        // Support Java Script
        webViewSettings.setJavaScriptEnabled(true);

        // 팝업창을 띄우기 위함
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webViewSettings.setLoadsImagesAutomatically(true);
        webViewSettings.setUseWideViewPort(true);

        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setSupportZoom(true);
        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setGeolocationEnabled(true);

        // meta-encoding 이 없는 페이지는 UTF-8로 표시
        webViewSettings.setDefaultTextEncodingName("UTF-8");

        // Support Flash
        webViewSettings.setSupportMultipleWindows(true);

        // HTML5 DOM
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);

        // HTML5 App Cache
        webViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webViewSettings.setAppCacheEnabled(true);
        webViewSettings.setAppCachePath(context.getCacheDir().getAbsolutePath());

    }

    @Override
    public void attachView(MainContract.View view, WebView webView) {
        this.view = view;
        this.webView = webView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public String getValidUrl(String url) {
        if (url == null) return "";

        if (!url.contains("http://") || !url.contains("https://"))
            url = "http://" + url;

        return url;
    }

    @Override
    public void reloadUrl() {
        webView.reload();
        view.setUrl(webView.getUrl());
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
        view.setUrl(webView.getUrl());
    }
}
