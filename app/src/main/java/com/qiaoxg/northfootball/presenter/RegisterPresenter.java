package com.qiaoxg.northfootball.presenter;

import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.model.IUserModel;
import com.qiaoxg.northfootball.model.imp.UserModelImp;
import com.qiaoxg.northfootball.model.local.UserDbUtils;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.IRegisterView;

import java.util.UUID;

/**
 * Created by admin on 2017/8/31.
 */

public class RegisterPresenter {

    private IRegisterView iView;
    private IUserModel iUserModel;

    public RegisterPresenter(IRegisterView iview) {
        this.iUserModel = new UserModelImp();
        this.iView = iview;
    }

    /**
     * 用户注册
     *
     * @param phone
     * @param password
     */
    public void register(String phone, String password) {

        iUserModel.register(phone, password, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iView.onRegisterResult(true, obj);
            }

            @Override
            public void onFailed(String msgString) {
                iView.onRegisterResult(false, msgString);
            }
        });
    }
}
