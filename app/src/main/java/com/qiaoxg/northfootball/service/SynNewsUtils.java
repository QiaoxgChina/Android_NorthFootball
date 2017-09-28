package com.qiaoxg.northfootball.service;

import android.os.Handler;
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
import static com.qiaoxg.northfootball.service.SynNewsService.SYN_NEWS_SUCCESS;

/**
 * Created by admin on 2017/9/27.
 */

public class SynNewsUtils {

    private static final String TAG = "SynNewsUtils";

    public static void synZhiBoBaData(final String url, final Handler mHandler) {
        Thread t = new Thread() {
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("li");
                for (Element element : elements) {
                    String type = element.attr("type");
                    if (type.equals("zuqiu")) {
                        Elements titleElement = element.select("h2 a");
                        String title = titleElement.text().trim();
                        String link = titleElement.attr("href").trim();

                        Elements dataElement = element.select(".pass_time");
                        String date = dataElement.text();

                        Elements imgElement = element.select("img");
                        String imgUrl = imgElement.attr("src").trim();

                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(link)) {
                            NewsBean news = new NewsBean();
                            news.setId(null);
                            news.setTitle(title);
                            news.setDateTime("2017-" + date);
                            news.setFrom(NEWS_FROM_TYPE_ZHIBOBA);
                            news.setLink(link);
                            news.setImgUrl(imgUrl);
                            news.setUuid(String.valueOf(UUID.randomUUID()));
                            news.setType(NEWS_TYPE_ZHIBOBA);
                            news.setCommentCount("0");
                            NewsDbUtils.newsAdd(news);
                        }
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(SYN_NEWS_SUCCESS);
                } else {
                    SynNewsIntentService.sendBroadcastMessage("同步数据中...", true);
                }
            }
        };
        t.start();
    }


    public static void synHuPuData(final String url, final Handler mHandler) {
        Thread t = new Thread() {
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("li");
                for (Element element : elements) {
                    Elements titleElement = element.select("h3");
                    String title = titleElement.text().trim();
                    Elements linkElement = element.select("a");
                    String link = linkElement.attr("href").trim();
                    Elements dataElement = element.select(".news-time");
                    String date = dataElement.text();
                    Elements imgElement = element.select(".img-wrap");
                    String imgUrl = imgElement.attr("style").trim();
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(link)) {
                        NewsBean news = new NewsBean();
                        news.setId(null);
                        news.setTitle(title);
                        news.setDateTime(date);
                        news.setFrom(NEWS_FROM_TYPE_HUPU);
                        news.setLink(link);
                        news.setImgUrl(imgUrl.substring(21, imgUrl.length() - 2));
                        news.setUuid(String.valueOf(UUID.randomUUID()));
                        news.setType(NEWS_TYPE_HUPU);
                        news.setCommentCount("0");
                        NewsDbUtils.newsAdd(news);
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(SYN_NEWS_SUCCESS);
                } else {
                    SynNewsIntentService.sendBroadcastMessage("同步数据中...", true);
                }
            }
        };
        t.start();
    }

    public static void synWangYiData(final String url, final Handler mHandler) {
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
                    Elements linkElement = element.select("a");
                    String link = linkElement.attr("href").trim();

                    Elements titleElement = element.select(".main_wrap_title");
                    String title = titleElement.text().trim();

                    Elements imgElement = element.select("img");
                    String imgUrl = imgElement.attr("src").trim();

                    Elements commentCountElement = element.select(".main_wrap_info_tieCount");
                    String commentCount = commentCountElement.text();

                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(commentCount) && !TextUtils.isEmpty(link)) {
                        NewsBean news = new NewsBean();
                        news.setId(null);
                        news.setTitle(title);
                        news.setDateTime(null);
                        news.setFrom(NEWS_FROM_TYPE_WANGYI);
                        news.setLink(link);
                        news.setImgUrl(imgUrl);
                        news.setCommentCount(commentCount);
                        news.setType(NEWS_TYPE_WANGYI);
                        news.setUuid(String.valueOf(UUID.randomUUID()));
                        NewsDbUtils.newsAdd(news);
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(SYN_NEWS_SUCCESS);
                } else {
                    SynNewsIntentService.sendBroadcastMessage("同步数据中...", true);
                }
            }
        };
        t.start();
    }

    /**
     * 同步懂球帝数据
     *
     * @param url
     */
    public static void synDongQiuDiData(final String url, final Handler mHandler) {
        Thread t = new Thread() {
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("li");
                for (Element element : elements) {
                    Elements titleElement = element.select("h2 a");
                    String title = titleElement.text().trim();
                    String link = titleElement.attr("href").trim();
                    Elements dataElement = element.select(".time");
                    String date = dataElement.text();

                    Elements imgElement = element.select("img");
                    String imgUrl = imgElement.attr("src").trim();
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(link)) {
                        NewsBean news = new NewsBean();
                        news.setId(null);
                        news.setTitle(title);
                        news.setDateTime(date.length() > 19 ? date.substring(0, 19) : date);//2017-09-01 11:55:56
                        news.setFrom(NEWS_FROM_TYPE_DONGQIUDI);
                        news.setLink(link);
                        news.setImgUrl(imgUrl);
                        news.setUuid(String.valueOf(UUID.randomUUID()));
                        news.setType(NEWS_TYPE_DONGQIUDI);
                        news.setCommentCount("0");
                        NewsDbUtils.newsAdd(news);
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(SYN_NEWS_SUCCESS);
                } else {
                    SynNewsIntentService.sendBroadcastMessage("同步数据中...", true);
                }
            }
        };
        t.start();
    }

    public static void synSinaData(final String url, final Handler mHandler) {
        Thread t = new Thread() {
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("a");
                Log.e(TAG, "run: elements size  is " + elements.size());
                for (Element element : elements) {
                    String link = element.attr("href").trim();//https://sports.sina.cn/premierleague/manutd/
                    Log.e(TAG, "link is " + link);
                    if (link.startsWith("https://sports.sina.cn/premierleague/manutd/")) {
                        Elements titleElement = element.select("h2");
                        String title = titleElement.text().trim();
                        Log.e(TAG, "run: title is " + title);
                        Elements imgElement = element.select("img");
                        String imgUrl = imgElement.attr("data-src").trim();
                        Log.e(TAG, "run: imgUrl is " + imgUrl);
                        Elements commentCountElement = element.select(".m_f_con_n");
                        String commentCount = commentCountElement.text();
                        Log.e(TAG, "run: commentCount is " + commentCount);
                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(commentCount) && !TextUtils.isEmpty(link)) {
                            NewsBean news = new NewsBean();
                            news.setId(null);
                            news.setTitle(title);
                            news.setDateTime(null);
                            news.setFrom(NEWS_FROM_TYPE_SINA);
                            news.setLink(link);
                            news.setImgUrl(imgUrl);
                            news.setCommentCount(commentCount);
                            news.setType(NEWS_TYPE_SINA);
                            news.setUuid(String.valueOf(UUID.randomUUID()));
                            NewsDbUtils.newsAdd(news);
                        }
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(SYN_NEWS_SUCCESS);
                } else {
                    SynNewsIntentService.sendBroadcastMessage("同步数据中...", true);
                }

            }
        };
        t.start();
    }
}
