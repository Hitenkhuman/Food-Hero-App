package com.example.foodhero.Models;

import java.io.Serializable;

public class Food implements Serializable {
    private String foodid=null;
    private String resid=null;
    private String ngoid=null;
    private String resName=null;
    private String ngoName=null;
    private int resImgUrl;
    private int ngoIImgUrl;
    private String date=null;
    private String discription=null;
    private String type=null;
    private int noOfDishes=0;
    private String status=null;

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getNgoid() {
        return ngoid;
    }

    public void setNgoid(String ngoid) {
        this.ngoid = ngoid;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public int getResImgUrl() {
        return resImgUrl;
    }

    public void setResImgUrl(int resImgUrl) {
        this.resImgUrl = resImgUrl;
    }

    public int getNgoIImgUrl() {
        return ngoIImgUrl;
    }

    public void setNgoIImgUrl(int ngoIImgUrl) {
        this.ngoIImgUrl = ngoIImgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNoOfDishes() {
        return noOfDishes;
    }

    public void setNoOfDishes(int noOfDishes) {
        this.noOfDishes = noOfDishes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Food(String foodid, String resid, String ngoid, String resName, String ngoName, int resImgUrl, int ngoIImgUrl, String date, String discription, String type, int noOfDishes, String status) {
        this.foodid = foodid;
        this.resid = resid;
        this.ngoid = ngoid;
        this.resName = resName;
        this.ngoName = ngoName;
        this.resImgUrl = resImgUrl;
        this.ngoIImgUrl = ngoIImgUrl;
        this.date = date;
        this.discription = discription;
        this.type = type;
        this.noOfDishes = noOfDishes;
        this.status = status;
    }


}
