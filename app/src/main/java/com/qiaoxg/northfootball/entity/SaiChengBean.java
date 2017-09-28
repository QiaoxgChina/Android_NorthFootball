package com.qiaoxg.northfootball.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SaiChengBean {

    @Id(autoincrement = true)
    Long id;
    public String date;
    public String time;
    public String match;
    public String zhu;
    public String zhuLogo;
    public String ke;
    public String keLogo;
    public int dateTime;

    @Generated(hash = 264731758)
    public SaiChengBean(Long id, String date, String time, String match, String zhu,
            String zhuLogo, String ke, String keLogo, int dateTime) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.match = match;
        this.zhu = zhu;
        this.zhuLogo = zhuLogo;
        this.ke = ke;
        this.keLogo = keLogo;
        this.dateTime = dateTime;
    }

    @Generated(hash = 535360500)
    public SaiChengBean() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getZhu() {
        return zhu;
    }

    public void setZhu(String zhu) {
        this.zhu = zhu;
    }

    public String getZhuLogo() {
        return zhuLogo;
    }

    public void setZhuLogo(String zhuLogo) {
        this.zhuLogo = zhuLogo;
    }

    public String getKe() {
        return ke;
    }

    public void setKe(String ke) {
        this.ke = ke;
    }

    public String getKeLogo() {
        return keLogo;
    }

    public void setKeLogo(String keLogo) {
        this.keLogo = keLogo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }
}
