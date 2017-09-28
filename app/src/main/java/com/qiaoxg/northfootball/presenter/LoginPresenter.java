package com.qiaoxg.northfootball.presenter;

import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.model.IUserModel;
import com.qiaoxg.northfootball.model.imp.UserModelImp;
import com.qiaoxg.northfootball.model.local.UserDbUtils;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.ILoginView;
import com.qiaoxg.northfootball.ui.iview.IRegisterView;

import java.util.UUID;

/**
 * Created by admin on 2017/8/31.
 */

public class LoginPresenter {

    private ILoginView iView;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginView iview) {
        this.iView = iview;
        iUserModel = new UserModelImp();
    }

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     */
    public void login(String phone, String password) {
        iUserModel.login(phone, password, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iView.onLoginResult(true, obj);
            }

            @Override
            public void onFailed(String msgString) {
                iView.onLoginResult(false, "登录失败");
            }
        });
    }
}
