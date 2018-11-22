package com.zslin.wx.model;

import javax.persistence.*;

/**
 * 微信小程序配置文件
 * Created by zsl on 2018/10/21.
 */
@Entity
@Table(name = "wx_mini_config")
public class MiniConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String appid;

    private String secret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
