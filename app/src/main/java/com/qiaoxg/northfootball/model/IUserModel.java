package com.qiaoxg.northfootball.model;

import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;

/**
 * Created by admin on 2017/8/31.
 */

public interface IUserModel {

    /**
     * 获取当前登录的user
     *
     * @param callback
     */
    void getCurrLoginUser(BaseCallback callback);

    /**
     * 更新当前登录的user
     *
     * @param userInfo
     * @param callback
     */
    void updateCurrLoginUser(UserInfo userInfo, BaseCallback callback);

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @param callback
     */
    void login(String phone, String password, BaseCallback callback);

    /**
     * 用户注册
     *
     * @param phone
     * @param password
     * @param callback
     */
    void register(String phone, String password, BaseCallback callback);
}
