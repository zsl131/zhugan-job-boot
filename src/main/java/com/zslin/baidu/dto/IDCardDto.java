package com.zslin.baidu.dto;

/**
 * 身份证信息DTO对象
 * Created by zsl on 2018/11/9.
 */
public class IDCardDto {

    private String name;

    private String sex;

    //民族
    private String nation;

    private String birthday;

    private String address;

    private String cardNo;

    @Override
    public String toString() {
        return "IDCardDto{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }

    public IDCardDto() {
    }

    public IDCardDto(String name, String sex, String nation, String birthday, String address, String cardNo) {
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.birthday = birthday;
        this.address = address;
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
