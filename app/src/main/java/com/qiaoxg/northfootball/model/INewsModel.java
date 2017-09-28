package com.qiaoxg.northfootball.model;

import android.content.Context;

import com.qiaoxg.northfootball.presenter.callback.BaseCallback;

/**
 * Created by admin on 2017/8/31.
 */

public interface INewsModel {

    /**
     * 同步数据
     *
     * @param type
     * @param context
     * @param callback
     */
    void startSynNews(int type, Context context, BaseCallback callback);

    /**
     * 查询新闻列表
     *
     * @param pageIdx
     * @param type
     * @param callback
     */
    void selectNewsList(int pageIdx, int type, BaseCallback callback);

    /**
     * 清空新闻
     *
     * @param callback
     */
    void clearNewsData(BaseCallback callback);
}
