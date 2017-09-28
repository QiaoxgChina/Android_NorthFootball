package com.qiaoxg.northfootball.presenter;

import com.qiaoxg.northfootball.model.IUserModel;
import com.qiaoxg.northfootball.model.imp.UserModelImp;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.ILoginView;
import com.qiaoxg.northfootball.ui.iview.IMineView;

/**
 * Created by admin on 2017/8/31.
 */

public class MinePresenter {

    private IMineView iView;
    private IUserModel iUserModel;

    public MinePresenter(IMineView iview) {
        this.iView = iview;
        iUserModel = new UserModelImp();
    }

    /**
     * 获取当前登录的User
     */
    public void getCurrLoginUser() {
        iUserModel.getCurrLoginUser(new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iView.onCheckLogin(true, obj);
            }

            @Override
            public void onFailed(String msgString) {
                iView.onCheckLogin(false, msgString);
            }
        });
    }

}
