package com.example.foodhero.Response;

import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Ngo;

import java.util.ArrayList;

public class GetFoodResponse {
    boolean success;
    String massage;
    ArrayList<Food> data;

    public GetFoodResponse(boolean success, String massage, ArrayList<Food> data) {
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

    public ArrayList<Food> getData() {
        return data;
    }

    public void setData(ArrayList<Food> data) {
        this.data = data;
    }
}
