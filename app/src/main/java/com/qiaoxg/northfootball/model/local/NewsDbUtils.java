package com.qiaoxg.northfootball.model.local;

import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.entity.NewsBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ALL;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HOT;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_WANGYI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ZHIBOBA;

/**
 * Created by admin on 2017/8/31.
 */

public class NewsDbUtils {

    private static final String TAG = "NewsDbUtils";

    /**
     * 查询所有的news
     *
     * @return
     */
    public static List<NewsBean> newsSelectByType(int pageIdx, int type) {
        int pageMax = 10;
        int offSet = pageIdx * pageMax;
        QueryBuilder<NewsBean> builder = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        if (type == NEWS_TYPE_ALL) {
            return builder.orderDesc(NewsBeanDao.Properties.Id)
                    .offset(offSet)
                    .limit(pageMax)
                    .list();
        } else if (type == NEWS_TYPE_HOT) {
            return newsSelectHotNews(pageIdx);
        } else {
            return builder.where(NewsBeanDao.Properties.Type.eq(type))
                    .orderDesc(NewsBeanDao.Properties.DateTime)
                    .offset(offSet)
                    .limit(pageMax)
                    .list();
        }
    }

    private static List<NewsBean> newsSelectHotNews(int pageIdx) {
        int pageMax = 3;
        int offSet = pageIdx * pageMax;
        List<NewsBean> newsBeanList = new ArrayList<>();
        QueryBuilder<NewsBean> builder = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List<NewsBean> dongqiudi = builder
                .where(NewsBeanDao.Properties.Type.eq(NEWS_TYPE_DONGQIUDI))
                .orderDesc(NewsBeanDao.Properties.Id)
                .offset(offSet)
                .limit(pageMax)
                .list();
        newsBeanList.addAll(dongqiudi);

        QueryBuilder<NewsBean> builder2 = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List<NewsBean> sina = builder2
                .where(NewsBeanDao.Properties.Type.eq(NEWS_TYPE_SINA))
                .orderDesc(NewsBeanDao.Properties.Id)
                .offset(offSet)
                .limit(pageMax)
                .list();
        newsBeanList.addAll(sina);

        QueryBuilder<NewsBean> builder3 = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List<NewsBean> wangyi = builder3
                .where(NewsBeanDao.Properties.Type.eq(NEWS_TYPE_WANGYI))
                .orderDesc(NewsBeanDao.Properties.Id)
                .offset(offSet)
                .limit(pageMax)
                .list();
        newsBeanList.addAll(wangyi);

        QueryBuilder<NewsBean> builder4 = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List<NewsBean> hupu = builder4
                .where(NewsBeanDao.Properties.Type.eq(NEWS_TYPE_HUPU))
                .orderDesc(NewsBeanDao.Properties.Id)
                .offset(offSet)
                .limit(pageMax)
                .list();
        newsBeanList.addAll(hupu);

        QueryBuilder<NewsBean> builder5 = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List<NewsBean> zhiboba = builder5.orderDesc(NewsBeanDao.Properties.Id)
                .where(NewsBeanDao.Properties.Type.eq(NEWS_TYPE_ZHIBOBA))
                .offset(offSet)
                .limit(pageMax)
                .list();
        newsBeanList.addAll(zhiboba);
        return newsBeanList;
    }

    /**
     * 插入news
     *
     * @param news
     */
    public static void newsAdd(NewsBean news) {
        if (!newsCheckIfExist(news.getLink())) {
            DaoManager.getInstance().getDaoSession().insert(news);
        }
    }

    public static boolean newsCheckIfExist(String link) {
        QueryBuilder<NewsBean> builder = DaoManager.getInstance().getDaoSession().queryBuilder(NewsBean.class);
        List list = builder.where(NewsBeanDao.Properties.Link.eq(link))
                .orderDesc(NewsBeanDao.Properties.DateTime)
                .list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 清空新闻
     */
    public static void newsClearAll() {
        DaoManager.getInstance().getDaoSession().deleteAll(NewsBean.class);
    }
}
