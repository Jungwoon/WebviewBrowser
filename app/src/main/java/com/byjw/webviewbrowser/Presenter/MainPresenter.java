package com.byjw.webviewbrowser.Presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.byjw.webviewbrowser.Custom.MyDownloadListener;
import com.byjw.webviewbrowser.Custom.MyWebChromeClient;
import com.byjw.webviewbrowser.Custom.MyWebViewClient;
import com.byjw.webviewbrowser.Model.HistoryHandler;

/**
 * Created by jungwoon on 2017. 12. 21..
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private WebView webView;
    private Context context;
    private Activity activity;
    private String HOME_ADDRESS = "http://naver.com";
    private boolean buttonFlag = true;


    public MainPresenter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
        view.setGoPageListener();
    }

    @Override
    public void webViewInit(WebView webView) {
        this.webView = webView;
        adoptCustomClient(webView);
        setProgressBar(webView);
        setWebViewSettings(webView);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public String getValidUrl(String url) {
        if (url == null) return HOME_ADDRESS;

        if (!url.contains(".")) {
            url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=" + url;
        }
        else if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }

        return url;
    }

    @Override
    public void addHistory(String url) {
        // 쌔애
    }

    @Override
    public void loadHome() {
        loadUrl(HOME_ADDRESS);
    }

    @Override
    public void toggleButtonFlag() {
        buttonFlag = !buttonFlag;
    }

    @Override
    public boolean goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            Log.e("TAG", "canGoBack");
            return true;
        }
        else {
            Log.e("TAG", "can't GoBack");
        }

        return false;


    }

    @Override
    public String getUrl() {
        return webView.getUrl();
    }

    @Override
    public void loadingOrStop() {
        if (buttonFlag)
            webView.reload();
        else
            webView.stopLoading();

        view.setUrl(webView.getUrl());
    }


    @Override
    public void loadUrl(String url) {
        webView.loadUrl(getValidUrl(url));
        view.setUrl(webView.getUrl());
    }

    private void adoptCustomClient(WebView webView) {
        webView.setWebViewClient(new MyWebViewClient(view, this, context));
        webView.setWebChromeClient(new MyWebChromeClient(view));
        webView.setDownloadListener(new MyDownloadListener(context, activity));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewSettings(WebView webView) {
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setLoadsImagesAutomatically(true);
        webViewSettings.setUseWideViewPort(true);
        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setSupportZoom(true);
        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setGeolocationEnabled(true);
        webViewSettings.setDefaultTextEncodingName("UTF-8");
        webViewSettings.setSupportMultipleWindows(true);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        webViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webViewSettings.setAppCacheEnabled(true);
        webViewSettings.setAppCachePath(context.getCacheDir().getAbsolutePath());
    }

    private void setProgressBar(WebView webView) {
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }


}
