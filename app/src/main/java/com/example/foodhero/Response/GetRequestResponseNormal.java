package com.example.foodhero.Response;

import com.example.foodhero.Models.RequestNormal;
import com.example.foodhero.Models.Restuarant;

import java.util.ArrayList;

public class GetRequestResponseNormal {
    boolean success;
    String massage;
    ArrayList<RequestNormal> data;

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

    public ArrayList<RequestNormal> getData() {
        return data;
    }

    public void setData(ArrayList<RequestNormal> data) {
        this.data = data;
    }

    public GetRequestResponseNormal(boolean success, String massage, ArrayList<RequestNormal> data) {
        this.success = success;
        this.massage = massage;
        this.data = data;
    }
}
