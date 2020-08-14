package com.sup.superwallet.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sup.superwallet.R;
import com.sup.superwallet.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView backBtn;
    @BindView(R.id.tv_titles)
    TextView titles;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        //设置禁止浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        titles.setText(title);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // 顶部加载进度条
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                super.onProgressChanged(view, progress);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        //支持播放视频
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
