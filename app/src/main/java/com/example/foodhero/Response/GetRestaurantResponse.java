package com.example.foodhero.Response;

import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;

import java.util.ArrayList;

public class GetRestaurantResponse {
    boolean success;
    String massage;
    ArrayList<Restuarant> data;

    public GetRestaurantResponse(boolean success, String massage, ArrayList<Restuarant> data) {
        this.success = success;
        this.massage = massage;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public ArrayList<Restuarant> getData() {
        return data;
    }

    public void setData(ArrayList<Restuarant> data) {
        this.data = data;
    }
}
