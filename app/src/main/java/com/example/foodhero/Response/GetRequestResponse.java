package com.example.foodhero.Response;

import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Request;

import java.util.ArrayList;

public class GetRequestResponse {
    boolean success;
    String massage;
    ArrayList<Request> data;

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

    public ArrayList<Request> getData() {
        return data;
    }

    public void setData(ArrayList<Request> data) {
        this.data = data;
    }

    public GetRequestResponse(boolean success, String massage, ArrayList<Request> data) {
        this.success = success;
        this.massage = massage;
        this.data = data;
    }
}
