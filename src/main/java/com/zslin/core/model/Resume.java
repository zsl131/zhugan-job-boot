package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 简历
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "c_resume")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String openid;

    private String nickname;

    private String headimg;

    private String name;

    private String identity;

    private String phone;

    /** 投递的工作类型ID */
    @Column(name = "work_ids")
    private String workIds;

    /** 投递的工作类型名称 */
    @Column(name = "work_names")
    private String workNames;

    /** 区域代码 */
    @Column(name = "area_code")
    private String areaCode;

    /** 区域名称 */
    @Column(name = "area_name")
    private String areaName;

    private String tags;

    private String remark;

    /** 状态，是否显示 */
    private String status;

    /** 被查看次数 */
    @Column(name = "read_count")
    private Integer readCount = 0;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkIds() {
        return workIds;
    }

    public void setWorkIds(String workIds) {
        this.workIds = workIds;
    }

    public String getWorkNames() {
        return workNames;
    }

    public void setWorkNames(String workNames) {
        this.workNames = workNames;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
