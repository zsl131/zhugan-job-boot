package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 个人身份认证
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "c_personal_auth_apply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalAuthApply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String openid;

    private String headimg;

    /** 身份证正面 */
    @Column(name = "front_pic")
    private String frontPic;

    /** 身份证背面 */
    @Column(name = "back_pic")
    private String backPic;

    /** 手持身份证 */
    @Column(name = "hand_pic")
    private String handPic;

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

    /** 姓名 */
    private String name;

    /** 身份证号 */
    private String identity;

    /** 性别 */
    private String sex;

    /** 家庭住址 */
    private String address;

    /** 小程序传入的表单ID，用于发模板消息 */
    private String formId;

    @Column(name = "verify_date")
    private String verifyDate;

    @Column(name = "verify_time")
    private String verifyTime;

    @Column(name = "verify_long")
    private Long verifyLong;

    @Column(name = "verify_user")
    private String verifyUser;

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Long getVerifyLong() {
        return verifyLong;
    }

    public void setVerifyLong(Long verifyLong) {
        this.verifyLong = verifyLong;
    }

    public String getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getHandPic() {
        return handPic;
    }

    public void setHandPic(String handPic) {
        this.handPic = handPic;
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
}
