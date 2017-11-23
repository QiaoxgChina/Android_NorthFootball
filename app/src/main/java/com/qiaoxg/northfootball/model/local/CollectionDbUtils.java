package com.qiaoxg.northfootball.model.local;

import com.qiaoxg.northfootball.entity.CollectionBean;
import com.qiaoxg.northfootball.entity.CollectionBeanDao;
import com.qiaoxg.northfootball.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */

public class CollectionDbUtils {

    /**
     * 收藏添加或删除
     *
     * @param isCollection
     * @param bean
     */
    public static void collection(boolean isCollection, CollectionBean bean) {
        DaoSession session = DaoManager.getInstance().getDaoSession();
        if (isCollection) {
            session.insert(bean);
        } else {
            session.delete(bean);
        }
    }

    /**
     * 查询收藏
     *
     * @param pageIndex
     * @return
     */
    public static List<CollectionBean> select(int pageIndex) {
        QueryBuilder<CollectionBean> query = DaoManager.getInstance().getDaoSession().queryBuilder(CollectionBean.class);
        return query.offset(pageIndex * 30).limit(30).orderAsc(CollectionBeanDao.Properties.DateTime).list();
    }

    /**
     * 查询收藏状态
     *
     * @param userId
     * @param newsId
     * @return
     */
    public static boolean isCollected(String userId, String newsId) {
        QueryBuilder<CollectionBean> query = DaoManager.getInstance().getDaoSession().queryBuilder(CollectionBean.class);
        List list = query.where(CollectionBeanDao.Properties.UserUuid.eq(userId))
                .where(CollectionBeanDao.Properties.NewsId.eq(newsId))
                .orderAsc(CollectionBeanDao.Properties.DateTime)
                .list();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
