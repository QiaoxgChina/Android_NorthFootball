package com.qiaoxg.northfootball.model.local;

import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.entity.UserInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class UserDbUtils {

    /**
     * 用户注册
     *
     * @param user
     * @return 是否注册成功
     */
    public static boolean userRegister(UserInfo user) {
        long id = DaoManager.getInstance().getDaoSession().insert(user);
        if (id >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @return
     */
    public static UserInfo userLogin(String phone, String password) {
        QueryBuilder<UserInfo> queryBuilder = DaoManager.getInstance().getDaoSession().queryBuilder(UserInfo.class);
        List<UserInfo> list = queryBuilder.where(UserInfoDao.Properties.Phone.eq(phone))
                .where(UserInfoDao.Properties.Password.eq(password))
                .orderDesc(UserInfoDao.Properties.Id)
                .list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 检查手机号是否注册
     *
     * @param phone
     * @return
     */
    public static boolean userCheckIfExist(String phone) {
        QueryBuilder<UserInfo> queryBuilder = DaoManager.getInstance().getDaoSession().queryBuilder(UserInfo.class);
        List<UserInfo> list = queryBuilder.where(UserInfoDao.Properties.Phone.eq(phone))
                .list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

}
