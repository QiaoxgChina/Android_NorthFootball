package com.qiaoxg.northfootball.ui.activity.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.entity.CollectionBean;
import com.qiaoxg.northfootball.presenter.NewsPresenter;
import com.qiaoxg.northfootball.ui.iview.INewsView;
import com.qiaoxg.northfootball.utils.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qiaoxg.northfootball.app.AppConstants.PARAM_NEW_ID;
import static com.qiaoxg.northfootball.app.AppConstants.PARAM_URL;

public class NewsActivity extends BaseActivity implements INewsView {

    @BindView(R.id.backBtn_ll)
    LinearLayout mBackBtn;
    @BindView(R.id.pageTitle_tv)
    TextView mPageTitleTv;
    @BindView(R.id.content_webview)
    WebView mWebview;
    @BindView(R.id.comment_input_ed)
    EditText commentInputEd;
    @BindView(R.id.collection_btn)
    ImageView collectionBtn;
    @BindView(R.id.share_btn)
    ImageView shareBtn;

    private String mUrl;
    private String newsId;

    private NewsPresenter mNewsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
        initIntent();
        initWebView();
        initPresenter();
    }

    private void initPresenter() {
        mNewsPresenter = new NewsPresenter(this);
        mNewsPresenter.isCollected("userUuid", newsId);
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
        newsId = getIntent().getStringExtra(PARAM_NEW_ID);
    }

    private void initView() {
        UIHelper.showView(mBackBtn, true);
    }

    @OnClick(R.id.backBtn_ll)
    public void onViewClicked() {
        this.finish();
    }

    public static void intoThisActivity(Activity context, String url, String newId) {
        Intent i = new Intent(context, NewsActivity.class);
        i.putExtra(PARAM_URL, url);
        i.putExtra(PARAM_NEW_ID, newId);
        context.startActivity(i);

    }

    @OnClick({R.id.collection_btn, R.id.share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collection_btn:
                CollectionBean bean = new CollectionBean();
                bean.setDateTime(System.currentTimeMillis() + "");
                bean.setNewsId(newsId);
                bean.setUserUuid("");
                mNewsPresenter.collection(true, bean);
                break;
            case R.id.share_btn:
                break;
        }
    }

    @Override
    public void onCollectionResult(boolean isOk) {

    }

    @Override
    public void isCollected(boolean isCollected) {
        collectionBtn.setPressed(isCollected);
    }
}
