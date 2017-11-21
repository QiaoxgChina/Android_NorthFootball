package com.qiaoxg.northfootball.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2017/8/25.
 */

public class MyApplication extends Application {

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    /**
     * 获取app的上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return mAppContext;
    }

}
