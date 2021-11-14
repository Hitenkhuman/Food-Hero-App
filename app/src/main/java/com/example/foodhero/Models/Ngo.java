package com.example.foodhero.Models;

import java.io.Serializable;

public class Ngo implements Serializable {
    private String id;
    private String name;
    private String mobile;
    private String emailid;
    private String password;
    private int imgurl;
    private String openingtime;
    private String closingtime;
    private String state;
    private String district;
    private String address;
    private String deviceToken;
    private String authid;
    private String verificationStatus;

    public Ngo(String id, String name, String mobile, String emailid, String password, int imgurl, String openingtime, String closingtime, String state, String district, String address, String deviceToken, String authid, String verificationStatus) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.emailid = emailid;
        this.password = password;
        this.imgurl = imgurl;
        this.openingtime = openingtime;
        this.closingtime = closingtime;
        this.state = state;
        this.district = district;
        this.address = address;
        this.deviceToken = deviceToken;
        this.authid = authid;
        this.verificationStatus = verificationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    public String getOpeningtime() {
        return openingtime;
    }

    public void setOpeningtime(String openingtime) {
        this.openingtime = openingtime;
    }

    public String getClosingtime() {
        return closingtime;
    }

    public void setClosingtime(String closingtime) {
        this.closingtime = closingtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
