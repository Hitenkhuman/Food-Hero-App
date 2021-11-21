package com.example.foodhero.Response;

import com.example.foodhero.Models.Admin;
import com.example.foodhero.Models.Food;

import java.util.ArrayList;

public class GetAdminResponse {
    boolean success;
    String massage;

    public GetAdminResponse(boolean success, String massage, ArrayList<Admin> data) {
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

    public ArrayList<Admin> getData() {
        return data;
    }

    public void setData(ArrayList<Admin> data) {
        this.data = data;
    }

    ArrayList<Admin> data;
}
