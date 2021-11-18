package com.example.foodhero.Models;

import java.util.Date;

public class RequestNormal {
    private String _id;
    private String ngo_id;
    private String res_id;
    private Date date;
    private String request_status;
    private  String food_id;

    public RequestNormal(String ngo_id, String res_id, String food_id) {
        this.ngo_id = ngo_id;
        this.res_id = res_id;
        this.food_id = food_id;
    }

    public RequestNormal(String _id, String ngo_id, String res_id, Date date, String request_status, String food_id) {
        this._id = _id;
        this.ngo_id = ngo_id;
        this.res_id = res_id;
        this.date = date;
        this.request_status = request_status;
        this.food_id = food_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}
