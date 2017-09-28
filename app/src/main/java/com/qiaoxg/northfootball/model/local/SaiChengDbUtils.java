package com.qiaoxg.northfootball.model.local;

import com.qiaoxg.northfootball.entity.SaiChengBean;
import com.qiaoxg.northfootball.entity.SaiChengBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2017/9/8.
 */

public class SaiChengDbUtils {

    /**
     * @param bean
     */
    public static void add(SaiChengBean bean) {
        if (!isExits(bean)) {
            DaoManager.getInstance().getDaoSession().insert(bean);
        }
    }

    /**
     * @param pageIdx
     * @return
     */
    public static List<SaiChengBean> select(int pageIdx) {
        QueryBuilder<SaiChengBean> query = DaoManager.getInstance().getDaoSession().queryBuilder(SaiChengBean.class);
        return query.offset(pageIdx * 30).limit(30).orderAsc(SaiChengBeanDao.Properties.Date).list();
    }

    private static boolean isExits(SaiChengBean bean) {
        boolean isExits = false;
        QueryBuilder<SaiChengBean> query = DaoManager.getInstance().getDaoSession().queryBuilder(SaiChengBean.class);
        List list = query
                .where(SaiChengBeanDao.Properties.Zhu.eq(bean.getZhu()))
                .where(SaiChengBeanDao.Properties.Ke.eq(bean.getKe()))
                .list();
        if (list != null && list.size() > 0) {
            isExits = true;
        }
        return isExits;
    }

}
