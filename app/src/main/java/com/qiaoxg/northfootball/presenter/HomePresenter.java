package com.qiaoxg.northfootball.presenter;

import com.qiaoxg.northfootball.model.INewsModel;
import com.qiaoxg.northfootball.model.IUserModel;
import com.qiaoxg.northfootball.model.imp.NewsModelImp;
import com.qiaoxg.northfootball.model.imp.UserModelImp;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.IBaseView;
import com.qiaoxg.northfootball.ui.iview.IHomeView;
import com.qiaoxg.northfootball.ui.iview.IMineView;

/**
 * Created by admin on 2017/8/31.
 */

public class HomePresenter {

    private IHomeView iView;
    private INewsModel iModel;

    public HomePresenter(IHomeView iview) {
        this.iView = iview;
        iModel = new NewsModelImp();
    }

    /**
     * 获取新闻列表
     */
    public void getNewsList(int pageIdx, int type) {
        iModel.selectNewsList(pageIdx, type, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iView.onSelectNews(true, obj);
            }

            @Override
            public void onFailed(String msgString) {
                iView.onSelectNews(false, msgString);
            }
        });
    }

}
