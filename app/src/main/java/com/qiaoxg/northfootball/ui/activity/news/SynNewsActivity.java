package com.qiaoxg.northfootball.ui.activity.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.presenter.SynNewsPresenter;
import com.qiaoxg.northfootball.service.SynNewsService;
import com.qiaoxg.northfootball.ui.iview.IBaseView;
import com.qiaoxg.northfootball.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ZHIBOBA;

public class SynNewsActivity extends BaseActivity implements IBaseView, SynNewsService.SynNewsListener {

    private static final String TAG = "SynNewsActivity";

    @BindView(R.id.backBtn_ll)
    LinearLayout mBackBtn;
    @BindView(R.id.pageTitle_tv)
    TextView mTitleTv;
    @BindView(R.id.dongQiuDi_btn)
    RelativeLayout dongQiuDiBtn;
    @BindView(R.id.wangYi_btn)
    RelativeLayout wangYiBtn;
    @BindView(R.id.xinLang_btn)
    RelativeLayout xinLangBtn;
    @BindView(R.id.huPu_btn)
    RelativeLayout tengXunBtn;
    @BindView(R.id.synAll_btn)
    TextView mSynAllBtn;
    @BindView(R.id.clearData_btn)
    TextView mClearDataBtn;

    private SynNewsPresenter mPresenter;
    private ProgressDialog mDialog;

    private SynNewsService mSynNewsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syn_news);
        ButterKnife.bind(this);
        initView();
        initPresenter();
        initService();
    }

    private void initService() {
        Intent i = new Intent(this, SynNewsService.class);
        bindService(i, myConnection, Service.BIND_AUTO_CREATE);
    }

    public ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SynNewsService.MyBinder binder = (SynNewsService.MyBinder) service;
            mSynNewsService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mSynNewsService = null;
        }
    };

    private void initPresenter() {
        mPresenter = new SynNewsPresenter(this, this);
    }

    private void initView() {
        mTitleTv.setText("新闻同步");
        UIHelper.showView(mBackBtn, true);
        mDialog = new ProgressDialog(this);
    }

    @OnClick({R.id.backBtn_ll, R.id.dongQiuDi_btn, R.id.wangYi_btn, R.id.xinLang_btn, R.id.huPu_btn,
            R.id.synAll_btn, R.id.clearData_btn, R.id.zhiBoBa_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn_ll:
                this.finish();
                break;
            case R.id.dongQiuDi_btn:
                mPresenter.startSynNews(NEWS_TYPE_DONGQIUDI, this);
                break;
            case R.id.wangYi_btn:
                mPresenter.startSynNews(NEWS_TYPE_WANGYI, this);
                break;
            case R.id.xinLang_btn:
                mPresenter.startSynNews(NEWS_TYPE_SINA, this);
                break;
            case R.id.huPu_btn:
                mPresenter.startSynNews(NEWS_TYPE_HUPU, this);
                break;
            case R.id.zhiBoBa_btn:
                mPresenter.startSynNews(NEWS_TYPE_ZHIBOBA, this);
                break;
            case R.id.synAll_btn:
                if (mSynNewsService != null) {
                    Log.e(TAG, "onViewClicked: start");
                    mSynNewsService.startSynNews(initSynDataList(), this);
                }
                break;
            case R.id.clearData_btn:
                mPresenter.clearData();
                break;
        }
    }

    private List<SynNewsService.SynNewsBean> initSynDataList() {
        List<SynNewsService.SynNewsBean> list = new ArrayList<>();
        SynNewsService.SynNewsBean bean1 = new SynNewsService.SynNewsBean();
        bean1.setFrom("懂球帝");
        bean1.setType(NEWS_TYPE_DONGQIUDI);
        bean1.setUrl("");

        SynNewsService.SynNewsBean bean2 = new SynNewsService.SynNewsBean();
        bean2.setFrom("网易体育");
        bean2.setType(NEWS_TYPE_WANGYI);
        bean2.setUrl("");

        SynNewsService.SynNewsBean bean3 = new SynNewsService.SynNewsBean();
        bean3.setFrom("新浪体育");
        bean3.setType(NEWS_TYPE_SINA);
        bean3.setUrl("");

        SynNewsService.SynNewsBean bean4 = new SynNewsService.SynNewsBean();
        bean4.setFrom("虎扑体育");
        bean4.setType(NEWS_TYPE_HUPU);
        bean4.setUrl("");

        SynNewsService.SynNewsBean bean5 = new SynNewsService.SynNewsBean();
        bean5.setFrom("直播吧");
        bean5.setType(NEWS_TYPE_ZHIBOBA);
        bean5.setUrl("");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        return list;
    }

    /**
     * 进入此activity的入口
     *
     * @param activity
     */
    public static void intoActivity(Activity activity) {
        Intent i = new Intent(activity, SynNewsActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void showTipView(String string) {
        mDialog.setMessage(string);
        if (!mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void hiddenTipView() {
        mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        unbindService(myConnection);
    }

    @Override
    public void onStartSyn() {
        mDialog.setMessage("准备开始同步");
        if (!mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void onSuccess() {
        mDialog.setMessage("同步完成");
        mDialog.dismiss();
    }

    @Override
    public void onFail(String errorMsg) {
        mDialog.setMessage(errorMsg + "，同步时发生错误");
    }

    @Override
    public void onSynDoing(String msg) {
        mDialog.setMessage("正在同步：" + msg);
    }
}
