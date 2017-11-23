package com.qiaoxg.northfootball.presenter;

import com.qiaoxg.northfootball.entity.CollectionBean;
import com.qiaoxg.northfootball.model.INewsModel;
import com.qiaoxg.northfootball.model.imp.NewsModelImp;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.INewsView;

/**
 * Created by admin on 2017/11/23.
 */

public class NewsPresenter {

    private INewsView iNewsView;
    private INewsModel iNewsModel;

    public NewsPresenter(INewsView iView) {
        this.iNewsView = iView;
        this.iNewsModel = new NewsModelImp();
    }

    /**
     * 收藏
     *
     * @param isCollection
     * @param bean
     */
    public void collection(boolean isCollection, CollectionBean bean) {
        iNewsModel.collectionNews(isCollection, bean, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iNewsView.onCollectionResult(true);
            }

            @Override
            public void onFailed(String msgString) {
                iNewsView.onCollectionResult(false);
            }
        });
    }

    /**
     * 收藏状态查询
     *
     * @param userUuid
     * @param newsId
     */
    public void isCollected(String userUuid, String newsId) {
        iNewsModel.isCollected(userUuid, newsId, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iNewsView.isCollected((boolean) obj);
            }

            @Override
            public void onFailed(String msgString) {
                iNewsView.isCollected(false);
            }
        });
    }
}
