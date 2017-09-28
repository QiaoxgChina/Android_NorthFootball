package com.qiaoxg.northfootball.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.model.local.NewsDbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.UUID;

import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_ZHIBOBA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_FROM_TYPE_ZHIBOBA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ZHIBOBA;

/**
 * Created by admin on 2017/8/31.
 */

public class SynNewsIntentService extends IntentService {

    private static final String TAG = "SynNewsIntentService";

    public static final String SYN_NEWS_FROM_TYPE = "SYN_NEWS_FROM_TYPE";
    public static final String MY_SERVICE_ACTION_SYN_NEWS = "MY_SERVICE_ACTION_SYN_NEWS";
    public static final String MY_SERVICE_PARAM_KEY_TIP = "MY_SERVICE_PARAM_KEY_TIP";
    public static final String MY_SERVICE_PARAM_KEY_OK = "MY_SERVICE_PARAM_KEY_OK";

    private static LocalBroadcastManager manager;

    public SynNewsIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = LocalBroadcastManager.getInstance(this);
        sendBroadcastMessage("同步数据中...", false);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        int type = intent.getIntExtra(SYN_NEWS_FROM_TYPE, 0);
        String url = "";
        if (type == NEWS_TYPE_DONGQIUDI) {
            url = NET_URL_DONGQIUDI;
            SynNewsUtils.synDongQiuDiData(url, null);
        } else if (type == NEWS_TYPE_SINA) {
            url = NET_URL_SINA;
            SynNewsUtils.synSinaData(url, null);
        } else if (type == NEWS_TYPE_WANGYI) {
            url = NET_URL_WANGYI;
            SynNewsUtils.synWangYiData(url, null);
        } else if (type == NEWS_TYPE_HUPU) {
            url = NET_URL_HUPU;
            SynNewsUtils.synHuPuData(url, null);
        } else if (type == NEWS_TYPE_ZHIBOBA) {
            url = NET_URL_ZHIBOBA;
            SynNewsUtils.synZhiBoBaData(url, null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void sendBroadcastMessage(String msgString, boolean isOk) {
        Intent i = new Intent();
        i.setAction(MY_SERVICE_ACTION_SYN_NEWS);
        i.putExtra(MY_SERVICE_PARAM_KEY_TIP, msgString);
        i.putExtra(MY_SERVICE_PARAM_KEY_OK, isOk);
        manager.sendBroadcast(i);
    }
}
