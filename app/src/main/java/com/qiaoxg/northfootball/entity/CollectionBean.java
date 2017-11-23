package com.qiaoxg.northfootball.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by admin on 2017/8/25.
 */

@Entity
public class CollectionBean {

    @Id(autoincrement = true)
    Long id;
    String userUuid;//用户id
    String newsId;//新闻id
    String dateTime;//收藏时间
    String saiChengId;//赛程id

    @Generated(hash = 1084060909)
    public CollectionBean(Long id, String userUuid, String newsId, String dateTime,
                          String saiChengId) {
        this.id = id;
        this.userUuid = userUuid;
        this.newsId = newsId;
        this.dateTime = dateTime;
        this.saiChengId = saiChengId;
    }

    @Generated(hash = 1423617684)
    public CollectionBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUuid() {
        return this.userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSaiChengId() {
        return this.saiChengId;
    }

    public void setSaiChengId(String saiChengId) {
        this.saiChengId = saiChengId;
    }

}
