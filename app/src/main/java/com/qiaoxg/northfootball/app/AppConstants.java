package com.qiaoxg.northfootball.app;

/**
 * Created by admin on 2017/8/25.
 */

public class AppConstants {

    private static String APP_LOCAL_PATH = "/BearFootball/";

    public static String LOCAL_HTML_PATH = APP_LOCAL_PATH + "/html/";

    public static final String DCIM_CAMERA_PATH = "/DCIM/Camera/";

    //消息来源的网址
    public static final String NET_URL_DONGQIUDI = "http://www.dongqiudi.com/";
    public static final String NET_URL_SINA = "http://sports.sina.cn/premierleague/manutd?vt=4&cid=72264";
    public static final String NET_URL_WANGYI = "http://3g.163.com/touch/sport_sub.html?cid=isocce";
    public static final String NET_URL_HUPU = "https://m.hupu.com/soccer/epl/news";
    public static final String NET_URL_ZHIBOBA = "http://m.zhibo8.cc/news.htm";

    //赛程数据来源网址
    public static final String NET_URL_SAICHENG = "https://m.hupu.com/soccer/match";

    //新闻来源
    public static final String NEWS_FROM_TYPE_DONGQIUDI = "懂球帝";
    public static final String NEWS_FROM_TYPE_SINA = "新浪体育";
    public static final String NEWS_FROM_TYPE_WANGYI = "网易体育";
    public static final String NEWS_FROM_TYPE_HUPU = "虎扑体育";
    public static final String NEWS_FROM_TYPE_ZHIBOBA = "直播吧";

    //新闻类别
    public static final int NEWS_TYPE_HOT = 0;//热点
    public static final int NEWS_TYPE_DONGQIUDI = 1;//懂球帝
    public static final int NEWS_TYPE_SINA = 2;//新浪
    public static final int NEWS_TYPE_WANGYI = 3;//网易
    public static final int NEWS_TYPE_HUPU = 4;//虎扑
    public static final int NEWS_TYPE_ZHIBOBA = 5;//直播吧
    public static final int NEWS_TYPE_ALL = 10;//所有

    public static final String PARAM_URL = "PARAM_URL";
}
