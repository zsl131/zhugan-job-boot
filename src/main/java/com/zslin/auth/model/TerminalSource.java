package com.zslin.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 客户端可访问的接口
 * Created by zsl on 2018/10/29.
 */
@Entity
@Table(name = "a_terminal_source")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TerminalSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** Terminal ID */
    private Integer tid;

    /** AuthSource ID */
    private Integer aid;

    /** Terminal Token */
    private String token;

    /** Source SN */
    private String sn;

    /** Source Code */
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
