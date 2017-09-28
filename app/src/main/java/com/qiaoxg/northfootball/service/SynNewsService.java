package com.qiaoxg.northfootball.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.model.local.NewsDbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_ZHIBOBA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_ZHIBOBA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ZHIBOBA;

/**
 * Created by admin on 2017/9/27.
 */

public class SynNewsService extends Service {

    private static final String TAG = "SynNewsService";

    public static final int SYN_NEWS_SUCCESS = 0;
    private static final int SYN_NEWS_FAIL = 1;
    private static final int SYN_NEWS_TO_DO = 2;
    private static final int SYN_NEWS_DOING = 3;
    private static final int SYN_NEWS_ALL_DONE = 4;

    public interface SynNewsListener {
        void onStartSyn();

        void onSuccess();

        void onFail(String errorMsg);

        void onSynDoing(String msg);
    }

    private MyBinder myBinder = new MyBinder();
    private SynNewsService mSynNewsService;

    private boolean isSynDoing = false;
    private List<SynNewsBean> mSynNewsBeanList;
    private SynNewsBean mCurrSynNewsBean;
    private SynNewsListener mSynNewsListener;

    public class MyBinder extends Binder {
        public SynNewsService getService() {
            if (mSynNewsService == null) {
                mSynNewsService = new SynNewsService();
            }
            return mSynNewsService;
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SYN_NEWS_SUCCESS:
                    mCurrSynNewsBean = null;
                    isSynDoing = false;
                    if (mSynNewsBeanList.size() <= 0) {
                        mHandler.sendEmptyMessage(SYN_NEWS_ALL_DONE);
                    } else {
                        mHandler.sendEmptyMessage(SYN_NEWS_TO_DO);
                    }

                    break;
                case SYN_NEWS_FAIL:
                    mSynNewsListener.onFail(mCurrSynNewsBean.getFrom());
                    mSynNewsBeanList.add(mCurrSynNewsBean);
                    mCurrSynNewsBean = null;
                    isSynDoing = false;
                    mHandler.sendEmptyMessage(SYN_NEWS_TO_DO);
                    break;
                case SYN_NEWS_TO_DO:
                    if (!isSynDoing && mCurrSynNewsBean == null && mSynNewsBeanList.size() > 0) {
                        mCurrSynNewsBean = mSynNewsBeanList.remove(0);
                        mHandler.sendEmptyMessage(SYN_NEWS_DOING);
                    } else {
                        Log.e(TAG, "handleMessage: isSynDoing is " + isSynDoing + " mSynNewsBeanList size is " + mSynNewsBeanList.size());
                    }
                    break;
                case SYN_NEWS_DOING:
                    if (!isSynDoing && mCurrSynNewsBean != null) {
                        isSynDoing = true;
                        synNews(mCurrSynNewsBean);
                    }
                    break;
                case SYN_NEWS_ALL_DONE:
                    mSynNewsListener.onSuccess();
                    break;
            }
        }
    };

    /**
     * 开始同步
     *
     * @param bean
     */
    public void startSynNews(SynNewsBean bean, SynNewsListener listener) {
        if (mSynNewsBeanList == null) {
            mSynNewsBeanList = new ArrayList<>();
        }
        this.mSynNewsListener = listener;
        mSynNewsListener.onStartSyn();
        mSynNewsBeanList.add(bean);
        if (!isSynDoing) {
            mHandler.sendEmptyMessage(SYN_NEWS_TO_DO);
        }
    }

    /**
     * 开始同步
     *
     * @param beans
     */
    public void startSynNews(List<SynNewsBean> beans, SynNewsListener listener) {
        if (beans == null || beans.size() <= 0) {
            return;
        }
        this.mSynNewsListener = listener;
        if (mSynNewsBeanList == null) {
            mSynNewsBeanList = new ArrayList<>();
        }
        mSynNewsListener.onStartSyn();
        mSynNewsBeanList.addAll(beans);
        if (!isSynDoing) {
            mHandler.sendEmptyMessage(SYN_NEWS_TO_DO);
        }
    }

    /**
     * 获取当前正在同步的平台
     *
     * @return
     */
    public SynNewsBean getCurrSynNews() {
        return mCurrSynNewsBean;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSynNewsBeanList = new ArrayList<>();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void synNews(SynNewsBean bean) {
        mSynNewsListener.onSynDoing(bean.getFrom());
        int type = bean.getType();
        String url = "";
        if (type == NEWS_TYPE_DONGQIUDI) {
            url = NET_URL_DONGQIUDI;
            SynNewsUtils.synDongQiuDiData(url, mHandler);
        } else if (type == NEWS_TYPE_SINA) {
            url = NET_URL_SINA;
            SynNewsUtils.synSinaData(url, mHandler);
        } else if (type == NEWS_TYPE_WANGYI) {
            url = NET_URL_WANGYI;
            SynNewsUtils.synWangYiData(url, mHandler);
        } else if (type == NEWS_TYPE_HUPU) {
            url = NET_URL_HUPU;
            SynNewsUtils.synHuPuData(url, mHandler);
        } else if (type == NEWS_TYPE_ZHIBOBA) {
            url = NET_URL_ZHIBOBA;
            SynNewsUtils.synZhiBoBaData(url, mHandler);
        }
    }

    public static class SynNewsBean {

        String url;
        String from;
        int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }
}
