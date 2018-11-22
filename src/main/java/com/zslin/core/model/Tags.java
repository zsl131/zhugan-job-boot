package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 标签，用于让个人自己选择对应的标签
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "c_tags")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 说明 */
    private String text;

    /** 使用次数 */
    private Integer count = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
