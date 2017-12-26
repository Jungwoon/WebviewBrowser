package com.byjw.webviewbrowser.Presenter;

import android.webkit.WebView;

/**
 * Created by jungwoon on 2017. 12. 21..
 */

public interface MainContract {

    interface Model {

        void addHistory(String url);

        void readHistory();
    }


    interface View {

        void showToast(String messages);

        void setUrl(String url);

        String getUrl();

        void showProgressLayout();

        void hideProgressLayout();

        void refreshProgress(int progress);

        void setGoPageListener();

    }

    interface Presenter {

        void init();

        void attachView(MainContract.View view, WebView webView);

        void detachView();

        String getValidUrl(String url);

        void reloadUrl();

        void loadUrl(String url);
    }

}
