package com.qiaoxg.northfootball.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiaoxg.northfootball.entity.UserInfo;

import java.lang.reflect.Type;

/**
 * Created by admin on 2017/8/31.
 */

public class GsonUtils {

    private static Gson mGson;

    public static Gson getInstance() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * format String 2 UserInfo
     *
     * @param string
     * @return UserInfo
     */
    public static UserInfo formatString2User(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return getInstance().fromJson(string, new TypeToken<UserInfo>() {
        }.getType());
    }
}
