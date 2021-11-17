package com.example.foodhero.Models;

import java.io.Serializable;
import java.util.Date;

public class Restuarant implements Serializable {
    private String _id;
    private String name;
    private String mobile;
    private String email;
    private String password;
    private String imgurl;
    private String opening_time;
    private String closing_time;
    private String state;
    private String city;
    private String district;
    private String address;
    private String devicetoken;
    private String authid;
    private Date joindate;
    private int reports;
    private boolean isVerifyed;

    public Restuarant(String _id, String name, String mobile, String email, String password, String imgurl, String opening_time, String closing_time, String state, String city, String district, String address, String devicetoken, String authid, Date joindate, int reports, boolean isVerifyed) {
        this._id = _id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.imgurl = imgurl;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.state = state;
        this.city = city;
        this.district = district;
        this.address = address;
        this.devicetoken = devicetoken;
        this.authid = authid;
        this.joindate = joindate;
        this.reports = reports;
        this.isVerifyed = isVerifyed;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public boolean isVerifyed() {
        return isVerifyed;
    }

    public void setVerifyed(boolean verifyed) {
        isVerifyed = verifyed;
    }
}
