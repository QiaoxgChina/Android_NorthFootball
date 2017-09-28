package com.qiaoxg.northfootball.model.local;

import com.qiaoxg.northfootball.app.MyApplication;
import com.qiaoxg.northfootball.entity.DaoMaster;
import com.qiaoxg.northfootball.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by admin on 2017/4/10.
 */

public class DaoManager {

    /**
     * 实现功能
     * 1.创建数据库
     * 2.创建数据库的表
     * 3.对数据库的升级
     * 4.对数据库的增删查改
     */

    //TAG
    public static final String TAG = DaoManager.class.getSimpleName();
    //数据库名称
    private static final String DB_NAME = "com_qiaoxg_football.db";
    //多线程访问
    private volatile static DaoManager manager;
    //操作类
    public static DaoMaster.DevOpenHelper helper;
    //核心类
    private static DaoMaster daoMaster;
    private DaoSession daoSession;


    private DaoManager() {
    }

    //单例模式
    public static DaoManager getInstance() {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager();
                }

            }
        }
        return manager;
    }

    /**
     * 判断是否存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            helper = new DaoMaster.DevOpenHelper(MyApplication.getAppContext(), DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 完成对数据库的操作，只是个接口
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭DaoSession
     */
    public void closeDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    /**
     * 关闭Helper
     */
    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    /**
     * 关闭所有的操作
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }
}
