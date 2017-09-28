package com.qiaoxg.northfootball.model;

import android.content.Context;

import com.qiaoxg.northfootball.presenter.callback.BaseCallback;

/**
 * Created by admin on 2017/8/31.
 */

public interface ISaiChengModel {

    /**
     * 同步赛程
     *
     * @param context
     * @param callback
     */
    void synSaiCheng(Context context, BaseCallback callback);

    /**
     * 查询赛程
     *
     * @param pageIdx
     * @param type
     * @param callback
     */
    void selectSaiChengList(int pageIdx, int type, BaseCallback callback);

}
