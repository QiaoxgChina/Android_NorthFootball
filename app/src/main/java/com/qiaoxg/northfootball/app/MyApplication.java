package com.qiaoxg.northfootball.app;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by admin on 2017/8/25.
 */

public class MyApplication extends Application {

    //全部上下文
    private static Context mAppContext;

    //屏幕宽度
    private static int mScreenWidth = 0;
    //屏幕高度
    private static int mScreenHeight = 0;

    private static WindowManager mWindowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        measureScreen();
    }

    /**
     * 获取app的上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return mAppContext;
    }

    /**
     * 获取屏幕的宽和高
     */
    private static void measureScreen() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }

    /**
     * 获取WindowManager
     */
    public static WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mAppContext.getSystemService(
                    Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        if (mScreenWidth == 0)
            measureScreen();

        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        if (mScreenHeight == 0)
            measureScreen();

        return mScreenHeight;
    }


}
