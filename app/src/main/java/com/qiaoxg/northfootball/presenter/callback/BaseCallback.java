package com.qiaoxg.northfootball.presenter.callback;

/**
 * Created by admin on 2017/8/31.
 */

public interface BaseCallback {

    void onSuccess(Object obj);

    void onFailed(String msgString);
}
