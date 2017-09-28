package com.qiaoxg.northfootball.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.entity.SaiChengBean;
import com.qiaoxg.northfootball.model.local.NewsDbUtils;
import com.qiaoxg.northfootball.model.local.SaiChengDbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.UUID;

import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NET_URL_SAICHENG;
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
 * Created by admin on 2017/8/31.
 */

public class SynSaiChengIntentService extends IntentService {

    private static final String TAG = "SynSaiChengIntentService";

    public static final String MY_SERVICE_ACTION_SYN_SAICHENG = "MY_SERVICE_ACTION_SYN_SAICHENG";
    public static final String MY_SERVICE_PARAM_KEY_TIP = "MY_SERVICE_PARAM_KEY_TIP";
    public static final String MY_SERVICE_PARAM_KEY_OK = "MY_SERVICE_PARAM_KEY_OK";

    private LocalBroadcastManager manager;

    public SynSaiChengIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        synSaiChengData(NET_URL_SAICHENG);
    }

    private void synSaiChengData(final String url) {
        Thread t = new Thread() {
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("section");
                for (Element element : elements) {
                    Elements dateElement = element.select(".time-label span");
                    String date = dateElement.text();
                    if (!TextUtils.isEmpty(date)) {

                        Elements matchElements = element.select(".match-list");
                        for (Element match : matchElements) {

                            SaiChengBean bean = new SaiChengBean();
                            bean.setDate(date);

                            Elements timeElement = match.select(".match-state");
                            String time = timeElement.text();
                            bean.setTime(time);

                            Elements teams = match.select(".team-name-list");
                            for (int i = 0; i < teams.size(); i++) {
                                Element team = teams.get(i);
                                if (i == 0) {
                                    Elements zhu = team.select("h4");
                                    String zhuTeam = zhu.text();
                                    bean.setZhu(zhuTeam);

                                    Elements zhuLogoElement = team.select("img");
                                    String zhuLogo = zhuLogoElement.attr("src").trim();
                                    bean.setZhuLogo(zhuLogo);
                                } else {
                                    Elements ke = team.select("h4");
                                    String keTeam = ke.text();
                                    bean.setKe(keTeam);

                                    Elements keLogoElement = team.select("img");
                                    String keLogo = keLogoElement.attr("src").trim();
                                    bean.setKeLogo(keLogo);
                                }
                            }


                            Elements matchElement = match.select(".match-played");
                            String matchString = matchElement.text();
                            Log.e(TAG, "run: matchString is " + matchString);
                            bean.setMatch(matchString);

                            SaiChengDbUtils.add(bean);
                        }
                    }
                }
                sendBroadcastMessage(true);
            }
        };
        t.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendBroadcastMessage(boolean isOk) {
        Intent i = new Intent();
        i.setAction(MY_SERVICE_ACTION_SYN_SAICHENG);
        i.putExtra(MY_SERVICE_PARAM_KEY_OK, isOk);
        manager.sendBroadcast(i);
    }
}
