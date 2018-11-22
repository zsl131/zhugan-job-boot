package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.poi.ss.formula.functions.Na;

import javax.persistence.*;

/**
 * 区域用户
 * Created by zsl on 2018/11/21.
 */
@Entity
@Table(name = "c_area_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String phone;

    private String name;

    @Column(name = "county_code")
    private String countyCode;

    @Column(name = "county_name")
    private String countyName;

    @Column(name = "user_id")
    private Integer userId;

    /** 状态 */
    private String status;

    @Column(name = "create_time")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
