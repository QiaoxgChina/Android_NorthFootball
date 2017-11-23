package com.qiaoxg.northfootball.model;

import android.content.Context;

import com.qiaoxg.northfootball.entity.CollectionBean;
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

    /**
     * 收藏新闻
     *
     * @param isCollection
     * @param bean
     * @param callback
     */
    void collectionNews(boolean isCollection, CollectionBean bean, BaseCallback callback);

    /**
     * 查询收藏列表
     *
     * @param pageIdx
     * @param callback
     */
    void selectCollection(int pageIdx, BaseCallback callback);

    /**
     * 查询新闻的收藏状态
     *
     * @param userId
     * @param newsId
     * @param callback
     */
    void isCollected(String userId, String newsId, BaseCallback callback);
}
