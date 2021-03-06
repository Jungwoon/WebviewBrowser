package com.byjw.webviewbrowser.Custom;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.byjw.webviewbrowser.Presenter.MainContract;


/**
 * Created by jungwoon on 2017. 4. 7..
 */

public class MyWebChromeClient extends WebChromeClient {

    private MainContract.View view;

    public MyWebChromeClient(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onProgressChanged(WebView webView, int progress) {
        view.showProgressLayout();
        view.refreshProgress(progress);

        if (progress == 100)
            view.hideProgressLayout();

        super.onProgressChanged(webView, progress);
    }

}
