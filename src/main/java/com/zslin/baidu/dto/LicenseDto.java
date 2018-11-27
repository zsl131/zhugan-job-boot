package com.zslin.baidu.dto;

/**
 * 营业执照DTO对象
 * Created by zsl on 2018/11/27.
 */
public class LicenseDto {

    /** 注册资本 */
    private String money;

    /** 社会信用代码 或 证件编号 */
    private String companyCode;

    /** 单位名称 */
    private String companyName;

    /** 法人 */
    private String boss;

    /** 成立日期 */
    private String startDate;

    /** 有效期 */
    private String endDate;

    /** 地址 */
    private String address;

    @Override
    public String toString() {
        return "LicenseDto{" +
                "money='" + money + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", boss='" + boss + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public LicenseDto(String money, String companyCode, String companyName, String boss, String startDate, String endDate, String address) {
        this.money = money;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.boss = boss;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
    }

    public LicenseDto() {
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
