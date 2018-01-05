package com.byjw.webviewbrowser.Model;

import com.byjw.webviewbrowser.Presenter.MainContract;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jungwoon on 2018. 1. 3..
 */

public class HistoryHandler implements MainContract.Model {

    private Realm realm;

    public HistoryHandler() {
        if (realm == null) realm = Realm.getDefaultInstance();
    }

    @Override
    public void addHistory(String url) {
        realm.beginTransaction();
        History history = realm.createObject(History.class);
        history.setDate(getDate());
        history.setUrl(url);
        realm.commitTransaction();
    }

    @Override
    public RealmResults<History> readHistory() {
        return realm.where(History.class).findAll().sort("date");
    }

    private String getDate() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
    }
}
