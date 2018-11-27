package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 用人单位认证申请
 * Created by zsl on 2018/11/26.
 */
@Entity
@Table(name = "c_company_auth_apply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyAuthApply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String openid;

    private String headimg;

    @Column(name = "personal_name")
    private String personalName;

    @Column(name = "personal_identity")
    private String personalIdentity;

    @Column(name = "personal_phone")
    private String personalPhone;

    /** 法人身份证正面图片 */
    @Column(name = "front_pic")
    private String frontPic;

    /** 营业执照正面图片 */
    @Column(name = "license_pic")
    private String licensePic;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_long")
    private Long createLong;

    /** 状态，0-待审核；1-通过；2-驳回 */
    private String status="0";

    /** 审核原因 */
    private String reason;

    /** 公司名称 */
    @Column(name = "company_name")
    private String companyName;

    /** 经营地址 */
    @Column(name = "company_address")
    private String companyAddress;

    /** 统一代码 */
    @Column(name = "company_no")
    private String companyNo;

    /** 法人姓名 */
    @Column(name = "boss_name")
    private String bossName;

    /** 注册资金 */
    /*@Column(name = "company_money")
    private String companyMoney;*/

    /** 公司成立日期 */
    @Column(name = "company_start_date")
    private String companyStartDate;

    /** 公司营业期限 */
    @Column(name = "company_end_date")
    private String companyEndDate;

    /** 表单ID */
    private String formid;

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }
/*public String getCompanyMoney() {
        return companyMoney;
    }

    public void setCompanyMoney(String companyMoney) {
        this.companyMoney = companyMoney;
    }*/

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPersonalIdentity() {
        return personalIdentity;
    }

    public void setPersonalIdentity(String personalIdentity) {
        this.personalIdentity = personalIdentity;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateLong() {
        return createLong;
    }

    public void setCreateLong(Long createLong) {
        this.createLong = createLong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyStartDate() {
        return companyStartDate;
    }

    public void setCompanyStartDate(String companyStartDate) {
        this.companyStartDate = companyStartDate;
    }

    public String getCompanyEndDate() {
        return companyEndDate;
    }

    public void setCompanyEndDate(String companyEndDate) {
        this.companyEndDate = companyEndDate;
    }

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    /** 经营范围 */
    private String companyArea;
}
