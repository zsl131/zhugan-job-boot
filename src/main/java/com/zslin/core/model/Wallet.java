package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 钱包，账户
 *  - 用于记录用户可以访问的次数和用户可以刷新的次数
 * Created by zsl on 2018/11/28.
 */
@Entity
@Table(name = "c_wallet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 个人的查看权限次数,个人查看企业招聘信息 */
    @Column(name = "personal_view_count")
    private Integer personalViewCount = 0;

    /** 个人的刷新权限次数 */
    @Column(name = "personal_refresh_count")
    private Integer personalRefreshCount = 0;

    /** 单位的查看权限次数，单位查看个人简历 */
    @Column(name = "company_view_count")
    private Integer companyViewCount = 0;

    /** 单位的刷新权限次数 */
    @Column(name = "company_refresh_count")
    private Integer companyRefreshCount = 0;

    private String openid;

    private String name;

    private String identity;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_no")
    private String companyNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalViewCount() {
        return personalViewCount;
    }

    public void setPersonalViewCount(Integer personalViewCount) {
        this.personalViewCount = personalViewCount;
    }

    public Integer getPersonalRefreshCount() {
        return personalRefreshCount;
    }

    public void setPersonalRefreshCount(Integer personalRefreshCount) {
        this.personalRefreshCount = personalRefreshCount;
    }

    public Integer getCompanyViewCount() {
        return companyViewCount;
    }

    public void setCompanyViewCount(Integer companyViewCount) {
        this.companyViewCount = companyViewCount;
    }

    public Integer getCompanyRefreshCount() {
        return companyRefreshCount;
    }

    public void setCompanyRefreshCount(Integer companyRefreshCount) {
        this.companyRefreshCount = companyRefreshCount;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }
}
