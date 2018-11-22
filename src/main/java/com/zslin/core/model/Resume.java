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
    @Column(name = "work_id")
    private String workId;

    /** 投递的工作类型名称 */
    @Column(name = "work_name")
    private String workName;

    /** 区域代码 */
    @Column(name = "area_code")
    private String areaCode;

    /** 区域名称 */
    @Column(name = "area_name")
    private String areaName;
}
