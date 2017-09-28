package com.qiaoxg.northfootball.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/8/31.
 */

@Entity
public class UserInfo {
    @Id(autoincrement = true)
    Long id;
    String userName;
    String phone;
    String password;
    String uuid;
    int loginDays;
    String headerPath;

    @Generated(hash = 2082476219)
    public UserInfo(Long id, String userName, String phone, String password,
            String uuid, int loginDays, String headerPath) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.uuid = uuid;
        this.loginDays = loginDays;
        this.headerPath = headerPath;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public int getLoginDays() {
        return this.loginDays;
    }
    public void setLoginDays(int loginDays) {
        this.loginDays = loginDays;
    }
    public String getHeaderPath() {
        return this.headerPath;
    }
    public void setHeaderPath(String headerPath) {
        this.headerPath = headerPath;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
