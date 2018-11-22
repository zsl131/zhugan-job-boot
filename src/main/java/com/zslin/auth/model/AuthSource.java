package com.zslin.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 权限资源
 * Created by zsl on 2018/10/29.
 */
@Entity
@Table(name = "a_auth_source")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    /** SN是唯一标识，如：userService.add */
    private String sn;

    /** code是别名，如：A01用于给其他人使用 */
    private String code;

    /** 状态，0-停用；1-启用 */
    private String status;

    /** 参数 */
    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
