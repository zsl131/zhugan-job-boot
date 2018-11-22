package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 反馈
 * Created by zsl on 2018/10/25.
 */
@Entity
@Table(name = "c_feedback")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String openid;

    private String headimg;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_long")
    private Long createLong;

    private String status ="0";

    @Lob
    private String content;

    @Lob
    private String reply;

    @Column(name = "reply_date")
    private String replyDate;

    @Column(name = "reply_time")
    private String replyTime;

    @Column(name = "reply_long")
    private Long replyLong;

    /** 区域代码 */
    @Column(name = "area_code")
    private String areaCode;

    /** 区域名称 */
    @Column(name = "area_name")
    private String areaName;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public Long getReplyLong() {
        return replyLong;
    }

    public void setReplyLong(Long replyLong) {
        this.replyLong = replyLong;
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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
