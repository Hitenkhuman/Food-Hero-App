package com.example.foodhero.Response;

import com.example.foodhero.Models.FoodNormal;

import java.util.ArrayList;

public class GetFoodResponseNormal {
    boolean success;
    String massage;
    ArrayList<FoodNormal> data;

    public GetFoodResponseNormal(boolean success, String massage, ArrayList<FoodNormal> data) {
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

    public ArrayList<FoodNormal> getData() {
        return data;
    }

    public void setData(ArrayList<FoodNormal> data) {
        this.data = data;
    }
}
