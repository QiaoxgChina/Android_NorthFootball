package com.qiaoxg.northfootball.model.imp;

import android.content.Context;
import android.content.Intent;

import com.qiaoxg.northfootball.entity.CollectionBean;
import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.model.INewsModel;
import com.qiaoxg.northfootball.model.local.CollectionDbUtils;
import com.qiaoxg.northfootball.model.local.NewsDbUtils;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.service.SynNewsIntentService;

import java.util.List;

import static com.qiaoxg.northfootball.service.SynNewsIntentService.SYN_NEWS_FROM_TYPE;

/**
 * Created by admin on 2017/8/31.
 */

public class NewsModelImp implements INewsModel {

    private static final String TAG = "NewsModelImp";

    @Override
    public void startSynNews(int type, Context context, BaseCallback callback) {
        Intent i = new Intent(context, SynNewsIntentService.class);
        i.putExtra(SYN_NEWS_FROM_TYPE, type);
        context.startService(i);
    }

    @Override
    public void selectNewsList(int pageIdx, int type, BaseCallback callback) {
        List<NewsBean> list = NewsDbUtils.newsSelectByType(pageIdx, type);
        if (list != null && list.size() > 0) {
            callback.onSuccess(list);
        } else {
            callback.onFailed("没有数据");
        }
    }

    @Override
    public void clearNewsData(BaseCallback callback) {
        NewsDbUtils.newsClearAll();
    }

    @Override
    public void collectionNews(boolean isCollection, CollectionBean bean, BaseCallback callback) {
        CollectionDbUtils.collection(isCollection, bean);
    }

    @Override
    public void selectCollection(int pageIdx, BaseCallback callback) {
        List<CollectionBean> beans = CollectionDbUtils.select(pageIdx);
        if (beans != null && beans.size() >= 0) {
            callback.onSuccess(beans);
        } else {
            callback.onFailed("没有数据");
        }
    }

    @Override
    public void isCollected(String userId, String newsId, BaseCallback callback) {
        callback.onSuccess(CollectionDbUtils.isCollected(userId, newsId));
    }
}
