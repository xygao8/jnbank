package com.iflytek.bank.pojo;

import java.io.Serializable;

/**
 * 〈支行网点信息〉
 * 〈功能详细描述〉
 *
 * @author [作者] xygao8
 * @version [版本号, 2020-06-08]
 **/
public class BranchInfoPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String city; //地市名

    private String branchName;//支行名

    private String phoneNbr;//联系电话

    private String address;//支行地址

    private String branchNameDesc;//网点名称或说法

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranchNameDesc() {
        return branchNameDesc;
    }

    public void setBranchNameDesc(String branchNameDesc) {
        this.branchNameDesc = branchNameDesc;
    }



    @Override
    public String toString() {
        return "BranchInfoPojo{" +
                "city='" + city + '\'' +
                ", branchName='" + branchName + '\'' +
                ", phoneNbr='" + phoneNbr + '\'' +
                ", address='" + address + '\'' +
                ", branchNameDesc='" + branchNameDesc + '\'' +
                '}';
    }
}