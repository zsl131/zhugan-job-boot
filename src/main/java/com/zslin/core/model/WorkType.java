package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 工种，工作岗位
 * Created by zsl on 2018/11/13.
 */
@Entity
@Table(name = "c_work_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 名称 */
    private String name;

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
}
