package com.byjw.webviewbrowser.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.byjw.webviewbrowser.Presenter.MainContract;
import com.byjw.webviewbrowser.Presenter.MainPresenter;
import com.byjw.webviewbrowser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.edit_url)
    EditText editUrl;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.progress_layout)
    FrameLayout progressLayout;
    @OnClick(R.id.btn_reload)
    void reload() {
        mainPresenter.reloadUrl();
    }

    private MainPresenter mainPresenter;
    private String HOME_ADDRESS = "http://naver.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this, this);
        mainPresenter.attachView(this, webView);
        mainPresenter.init();
        mainPresenter.loadUrl(HOME_ADDRESS);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Back 키를 눌렀을때 종료가 되는게 아니라, 이전 페이지로 넘어 가는 부분
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showToast(String messages) {
        Toast.makeText(this, messages, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUrl(String url) {
        editUrl.setText(url);
    }

    @Override
    public String getUrl() {
        return editUrl.getText().toString();
    }

    @Override
    public void showProgressLayout() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressLayout() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void refreshProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void setGoPageListener() {
        editUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    mainPresenter.loadUrl(mainPresenter.getValidUrl(getUrl()));
                    hideKeyboard(v);
                    setUrl(webView.getUrl());
                }

                return false;
            }

            private void hideKeyboard(TextView textView) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            }
        });
    }
}
