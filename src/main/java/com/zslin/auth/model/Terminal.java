package com.zslin.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 终端即客户端
 * Created by zsl on 2018/10/29.
 */
@Entity
@Table(name = "a_terminal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String remark;

    /** 权限代码 */
    private String token;

    /** 状态，1-启用；0-停用 */
    private String status;

    /** 级别，0-最高级别，访问所有；1-访问部份 */
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
