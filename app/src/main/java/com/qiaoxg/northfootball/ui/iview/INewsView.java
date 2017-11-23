package com.qiaoxg.northfootball.ui.iview;

/**
 * Created by admin on 2017/11/23.
 */

public interface INewsView {

    /**
     * 收藏结果
     *
     * @param isOk
     */
    void onCollectionResult(boolean isOk);

    /**
     * 查询结果
     *
     * @param isCollected
     */
    void isCollected(boolean isCollected);
}
