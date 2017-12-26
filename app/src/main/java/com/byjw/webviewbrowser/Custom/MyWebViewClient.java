package com.byjw.webviewbrowser.Custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.byjw.webviewbrowser.Presenter.MainContract;

/**
 * Created by jungwoon on 2017. 4. 7..
 */

public class MyWebViewClient extends WebViewClient {

    private MainContract.View view;
    private Context context;

    public MyWebViewClient(MainContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        view.showProgressLayout();
        view.setUrl(webView.getUrl());
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        view.hideProgressLayout();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(intent);
            webView.reload();
            return true;
        }

        // for Progress Bar Loading
        webView.loadUrl(url);
        view.hideProgressLayout();
        view.showToast(webView.getUrl()); // 테스트용 코드

        return true;
    }

}
