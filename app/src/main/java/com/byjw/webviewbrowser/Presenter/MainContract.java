package com.byjw.webviewbrowser.Presenter;

import android.webkit.WebView;

import com.byjw.webviewbrowser.Model.History;

import io.realm.RealmResults;

/**
 * Created by jungwoon on 2017. 12. 21..
 */

public interface MainContract {

    interface Model {

        void addHistory(String url);

        RealmResults<History> readHistory();
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

        void attachView(MainContract.View view);

        void webViewInit(WebView webView);

        void detachView();

        String getValidUrl(String url);

        void reloadUrl();

        void loadUrl(String url);

        void addHistory(String url);
    }

}
