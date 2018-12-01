package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 个人
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "c_personal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String openid;

    private String headimg;

    private String phone;

    private String name;

    private String identity;

    private String sex;

    private String address;

    /** 工作状态，0-未设置；1-工作中；2-待业 */
    @Column(name = "work_status")
    private String workStatus ="0";

    /** 个人说明 */
    private String remark;

    /** 个人标签 */
    private String tags;

    /** 个人标签的ID */
    @Column(name = "tags_ids")
    private String tagsIds;

    /** 是否手机认证，0-未认证；1-已认证；2-审核中；3-被驳回 */
    @Column(name = "check_phone")
    private String checkPhone = "0";

    /** 是否身份认证，0-未认证；1-已认证；2-审核中；3-被驳回 */
    @Column(name = "check_idcard")
    private String checkIdcard = "0";

    /** 是否身份认证，0-未认证；1-已认证；2-审核中；3-被驳回 */
    @Column(name = "check_company")
    private String checkCompany = "0";

    /** 是否有图片 */
    @Column(name = "has_pic")
    private String hasPic = "0";

    /** 是否有小视频 */
    @Column(name = "has_video")
    private String hasVideo = "0";

    /** 视频地址 */
    @Column(name = "video_url")
    private String videoUrl;

    /** 用户类型，0-未识别；1-人才用户；2-用人单位；10-平台管理员 */
    private String type = "0";

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_long")
    private Long createLong;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(String tagsIds) {
        this.tagsIds = tagsIds;
    }

    public String getCheckCompany() {
        return checkCompany;
    }

    public void setCheckCompany(String checkCompany) {
        this.checkCompany = checkCompany;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckPhone() {
        return checkPhone;
    }

    public void setCheckPhone(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public String getCheckIdcard() {
        return checkIdcard;
    }

    public void setCheckIdcard(String checkIdcard) {
        this.checkIdcard = checkIdcard;
    }

    public String getHasPic() {
        return hasPic;
    }

    public void setHasPic(String hasPic) {
        this.hasPic = hasPic;
    }

    public String getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(String hasVideo) {
        this.hasVideo = hasVideo;
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

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
