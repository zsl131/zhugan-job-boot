package com.zslin.baidu.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 百度接口使用记录
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "b_use_record")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 接口名称 */
    private String name;

    /** 接口名称，中文 */
    @Column(name = "name_ch")
    private String nameCh;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_long")
    private Long createLong;

    /** 请求之后的结果 */
    @Lob
    private String result;

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

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
