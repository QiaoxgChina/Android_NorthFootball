package com.qiaoxg.northfootball.utils;

import android.view.View;
import android.widget.Toast;

import com.qiaoxg.northfootball.app.MyApplication;

/**
 * Created by admin on 2017/8/31.
 */

public class UIHelper {

    private static Toast mToast;

    /**
     * 显示Toast提示
     *
     * @param msgString 提示文字
     */
    public static void showToast(String msgString) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getAppContext(), msgString, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgString);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示Toast提示
     *
     * @param msgStringId 提示文字
     */
    public static void showToast(int msgStringId) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getAppContext(), msgStringId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgStringId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示or隐藏view
     *
     * @param v      想要控制的view
     * @param isShow 想要的结果：显示或者隐藏
     */
    public static void showView(View v, boolean isShow) {
        if (v == null) return;
        int visibleInt = View.GONE;
        if (isShow) {
            visibleInt = View.VISIBLE;
        }
        v.setVisibility(visibleInt);
    }
}
