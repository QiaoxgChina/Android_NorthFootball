package com.qiaoxg.northfootball.event;

import com.qiaoxg.northfootball.entity.UserInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by admin on 2017/8/31.
 */

public class UserLoginEvent extends EventBus {

    boolean isOk;
    UserInfo userInfo;

    public UserLoginEvent(boolean isOk, UserInfo user) {
        this.isOk = isOk;
        this.userInfo = user;
    }

    public boolean isOk() {
        return isOk;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
