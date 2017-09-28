package com.qiaoxg.northfootball.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by admin on 2017/8/25.
 */

@Entity
public class NewsBean {

    @Id(autoincrement = true)
    Long id;
    String link;//链接
    String title;//标题
    String dateTime;//发表时间
    String from;//来源
    String uuid;
    String imgUrl;//图片链接
    int type;//类型
    String commentCount;//评论数


    @Generated(hash = 887209672)
    public NewsBean(Long id, String link, String title, String dateTime,
            String from, String uuid, String imgUrl, int type,
            String commentCount) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.dateTime = dateTime;
        this.from = from;
        this.uuid = uuid;
        this.imgUrl = imgUrl;
        this.type = type;
        this.commentCount = commentCount;
    }

    @Generated(hash = 1662878226)
    public NewsBean() {
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
