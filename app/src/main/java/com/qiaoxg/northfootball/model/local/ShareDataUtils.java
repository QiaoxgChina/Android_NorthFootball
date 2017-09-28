package com.qiaoxg.northfootball.model.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.qiaoxg.northfootball.app.MyApplication;
import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.model.GsonUtils;

/**
 * Created by admin on 2017/8/31.
 */

public class ShareDataUtils {

    private static final String SHARE_DATA_KEY_USER = "user";

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public static UserInfo getCurrLoginUser() {
        SharedPreferences sp = MyApplication.getAppContext()
                .getSharedPreferences(SHARE_DATA_KEY_USER, Context.MODE_PRIVATE);
        String userString = sp.getString(SHARE_DATA_KEY_USER, "");
        return GsonUtils.formatString2User(userString);
    }

    /**
     * 更新当前登录的user
     *
     * @param user
     */
    public static void updateCurrLoginUser(UserInfo user) {
        String userString = GsonUtils.getInstance().toJson(user);
        SharedPreferences sp = MyApplication.getAppContext()
                .getSharedPreferences(SHARE_DATA_KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SHARE_DATA_KEY_USER, userString);
        editor.commit();
    }
}
