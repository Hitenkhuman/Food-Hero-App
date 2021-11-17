package com.example.foodhero.Response;

import com.example.foodhero.Models.Ngo;

import java.util.ArrayList;

public class GetNgoResponse {
    boolean success;
    String massage;
    ArrayList <Ngo> data;

    public GetNgoResponse(boolean success, String massage, ArrayList<Ngo> data) {
        this.success = success;
        this.massage = massage;
        this.data = data;
    }

    public boolean getSuccess() {
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

    public ArrayList<Ngo> getData() {
        return data;
    }

    public void setData(ArrayList<Ngo> data) {
        this.data = data;
    }
}
