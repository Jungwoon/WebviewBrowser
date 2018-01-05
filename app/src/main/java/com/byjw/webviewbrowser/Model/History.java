package com.byjw.webviewbrowser.Model;

import io.realm.RealmObject;

/**
 * Created by jungwoon on 2018. 1. 3..
 */

public class History extends RealmObject {
    private String date;
    private String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
