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
import com.byjw.webviewbrowser.Model.History;
import com.byjw.webviewbrowser.Model.HistoryHandler;

import io.realm.RealmResults;

/**
 * Created by jungwoon on 2017. 12. 21..
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model model;
    private WebView webView;
    private Context context;
    private Activity activity;

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
        if (url == null) return "";

        if (!url.contains("http://") || !url.contains("https://"))
            url = "http://" + url;

        return url;
    }

    @Override
    public void addHistory(String url) {
        if (model == null) getModelInstance();

        model.addHistory(url);
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

    private void adoptCustomClient(WebView webView) {
        webView.setWebViewClient(new MyWebViewClient(view, this, context));
        webView.setWebChromeClient(new MyWebChromeClient(view, activity));
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

    private void getModelInstance() {
        this.model = new HistoryHandler();
    }

    public RealmResults<History> readHistory() {
        if (model == null) getModelInstance();

        return model.readHistory();
    }
}
