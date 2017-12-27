package com.byjw.webviewbrowser.Custom;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by jungwoon on 2017. 4. 11..
 */

public class MyDownloadListener implements DownloadListener {
    private Context context;
    private Activity activity;

    public MyDownloadListener(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setMimeType(mimeType);
            request.addRequestHeader("User-Agent", userAgent);

            String cookies = CookieManager.getInstance().getCookie(url);
            request.addRequestHeader("cookie", cookies);

            request.setDescription("Downloading file");

            String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);

            request.setTitle(fileName);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            assert downloadManager != null;
            downloadManager.enqueue(request);
        }
        catch (Exception e) {

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(context, "다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                }
                else {
                    Toast.makeText(context, "다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                }
            }
        }
    }
}
