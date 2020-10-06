package com.hanix.randomgame.view.event;

import android.app.Activity;
import android.webkit.WebView;

import com.hanix.randomgame.R;

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;

    private Activity activity;
    private WebView mWebView;

    public BackPressCloseHandler(Activity context, WebView webView)
    {
        this.activity = context;
        mWebView = webView;
    }

    public void onBackPressed()
    {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            if(mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            }else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

}
