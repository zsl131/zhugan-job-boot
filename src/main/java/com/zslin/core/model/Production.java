package com.zslin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 产品
 *  - 用于给个人或单位购买或领取的
 *  -
 * Created by zsl on 2018/11/28.
 */
@Entity
@Table(name = "c_production")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 产品名称 */
    private String name;

    /** 目标对象，personal、company... */
    @Column(name = "target_user")
    private String targetUser;

    /** 类型，1-查看次数；2-刷新次数 */
    private String type;

    /** 价值次数 */
    @Column(name = "worth_count")
    private Integer worthCount;

    /** 价格 */
    private Float price;

    /** 原价 */
    @Column(name = "ori_price")
    private Float oriPrice;

    /** 状态，0-不启用；1-启用 */
    private String status;

    /** 每用户限领次数，0：不限次数 */
    @Column(name = "amount_limit")
    private Integer amountLimit = 0;

    /** 说明 */
    private String remark;

    /** 购买次数 */
    @Column(name = "buy_count")
    private Integer buyCount = 0;

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Float getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(Float oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(Integer amountLimit) {
        this.amountLimit = amountLimit;
    }

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

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWorthCount() {
        return worthCount;
    }

    public void setWorthCount(Integer worthCount) {
        this.worthCount = worthCount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
