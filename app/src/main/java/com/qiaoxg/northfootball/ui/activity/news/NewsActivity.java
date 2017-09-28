package com.qiaoxg.northfootball.ui.activity.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.utils.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qiaoxg.northfootball.app.AppConstants.PARAM_URL;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.backBtn_ll)
    LinearLayout mBackBtn;
    @BindView(R.id.pageTitle_tv)
    TextView mPageTitleTv;
    @BindView(R.id.content_webview)
    WebView mWebview;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
        initIntent();
        initWebView();
    }

    private void initWebView() {
        mWebview.loadUrl(mUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPageTitleTv.setText(view.getTitle());
            }
        });
    }

    private void initIntent() {
        mUrl = getIntent().getStringExtra(PARAM_URL);
    }

    private void initView() {
        UIHelper.showView(mBackBtn, true);
    }

    @OnClick(R.id.backBtn_ll)
    public void onViewClicked() {
        this.finish();
    }

    public static void intoThisActivity(Activity context, String url) {
        Intent i = new Intent(context, NewsActivity.class);
        i.putExtra(PARAM_URL, url);
        context.startActivity(i);

    }
}
