package com.example.foodhero.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Request implements Serializable {
    private String _id;
    private Ngo ngo_id;
    private Restuarant res_id;
    private Date date;
    private String request_status;

    public Request(String _id, Ngo ngo_id, Restuarant res_id, Date date, String request_status) {
        this._id = _id;
        this.ngo_id = ngo_id;
        this.res_id = res_id;
        this.date = date;
        this.request_status = request_status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Ngo getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(Ngo ngo_id) {
        this.ngo_id = ngo_id;
    }

    public Restuarant getRes_id() {
        return res_id;
    }

    public void setRes_id(Restuarant res_id) {
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
}
