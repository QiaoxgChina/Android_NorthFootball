package com.qiaoxg.northfootball.model.imp;

import android.text.TextUtils;

import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.event.UserLoginEvent;
import com.qiaoxg.northfootball.model.IUserModel;
import com.qiaoxg.northfootball.model.local.ShareDataUtils;
import com.qiaoxg.northfootball.model.local.UserDbUtils;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

/**
 * Created by admin on 2017/8/31.
 * 此class当中包含所有与user相关的方法和变量
 */

public class UserModelImp implements IUserModel {

    private static UserInfo mCurrUser;

    @Override
    public void getCurrLoginUser(BaseCallback callback) {
        if (mCurrUser == null) {
            mCurrUser = ShareDataUtils.getCurrLoginUser();
        }
        if (mCurrUser == null) {
            callback.onFailed("");
        } else {
            callback.onSuccess(mCurrUser);
        }

    }

    @Override
    public void updateCurrLoginUser(UserInfo userInfo, BaseCallback callback) {
        ShareDataUtils.updateCurrLoginUser(userInfo);
        EventBus.getDefault().post(new UserLoginEvent(true, userInfo));
        if (callback != null) {
            callback.onSuccess("");
        }
    }

    @Override
    public void login(String phone, String password, BaseCallback callback) {
        String msgString = checkPhoneAndPassword(phone, password);
        if (!TextUtils.isEmpty(msgString)) {
            callback.onFailed(msgString);
            return;
        }
        UserInfo userInfo = UserDbUtils.userLogin(phone, password);
        if (userInfo != null) {
            callback.onSuccess(userInfo);
            updateCurrLoginUser(userInfo, null);
        } else {
            callback.onFailed("密码或用户名不正确");
        }
    }

    @Override
    public void register(String phone, String password, BaseCallback callback) {
        String msgString = checkPhoneAndPassword(phone, password);
        if (!TextUtils.isEmpty(msgString)) {
            callback.onFailed(msgString);
            return;
        }
        if (UserDbUtils.userCheckIfExist(phone)) {
            callback.onFailed("该手机号已经注册");
            return;
        }
        UserInfo info = new UserInfo();
        String uuid = String.valueOf(UUID.randomUUID());
        info.setUserName("新用户");
        info.setHeaderPath("");
        info.setLoginDays(1);
        info.setPassword(password);
        info.setPhone(phone);
        info.setUuid(uuid);
        if (!UserDbUtils.userRegister(info)) {
            callback.onFailed("注册失败");
        } else {
            callback.onSuccess(info);
            updateCurrLoginUser(info, null);
        }
    }

    private String checkPhoneAndPassword(String phone, String password) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            return "手机号不正确";
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
            return "密码长度在6到16个字符之间";
        }
        return null;
    }
}
