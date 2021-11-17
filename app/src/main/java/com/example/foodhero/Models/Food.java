package com.example.foodhero.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Food implements Serializable {
    private String _id;
    private Ngo ngo_id;
    private Restuarant res_id;
    private Date date;
    private String description;
    private String type;
    private int no_of_dishes;
    private String food_status;
    private String pickup_time;
    private ArrayList<Request> requests;
    private String note;
    private String pickup_address;
    private String city;


    public Food(String _id, Ngo ngo_id, Restuarant res_id, Date date, String description, String type, int no_of_dishes, String food_status, String pickup_time, ArrayList<Request> requests, String note, String pickup_address, String city) {
        this._id = _id;
        this.ngo_id = ngo_id;
        this.res_id = res_id;
        this.date = date;
        this.description = description;
        this.type = type;
        this.no_of_dishes = no_of_dishes;
        this.food_status = food_status;
        this.pickup_time = pickup_time;
        this.requests = requests;
        this.note = note;
        this.pickup_address = pickup_address;
        this.city = city;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNo_of_dishes() {
        return no_of_dishes;
    }

    public void setNo_of_dishes(int no_of_dishes) {
        this.no_of_dishes = no_of_dishes;
    }

    public String getFood_status() {
        return food_status;
    }

    public void setFood_status(String food_status) {
        this.food_status = food_status;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
