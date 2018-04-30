package com.byjw.webviewbrowser.Custom;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.byjw.webviewbrowser.Presenter.MainContract;
import com.byjw.webviewbrowser.Presenter.MainPresenter;

/**
 * Created by jungwoon on 2017. 4. 7..
 */

public class MyWebViewClient extends WebViewClient {

    private MainContract.View view;
    private MainPresenter presenter;
    private Context context;

    public MyWebViewClient(MainContract.View view, MainPresenter presenter, Context context) {
        this.view = view;
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        view.showProgressLayout();
        view.setUrl(webView.getUrl());
        presenter.toggleButtonFlag();
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        view.hideProgressLayout();
        presenter.toggleButtonFlag();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Intent existPackage = context.getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                if (existPackage != null) {
                    context.startActivity(intent);
                }
                else {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()));
                    context.startActivity(marketIntent);
                }
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (Uri.parse(url).getScheme().equals("market")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                Activity host = (Activity) webView.getContext();
                host.startActivity(intent);
                return true;
            }
            catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse(url);
                webView.loadUrl("http://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
                return false;
            }
        }
        else if (Uri.parse(url).getScheme().equals("tel")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(intent);
            webView.reload();
            return true;
        }

        webView.loadUrl(url);
        view.hideProgressLayout();
//        view.showToast(url); // 테스트용 코드
        presenter.addHistory(url);

        return true;
    }

}
