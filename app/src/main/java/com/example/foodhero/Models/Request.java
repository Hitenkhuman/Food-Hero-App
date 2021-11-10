package com.example.foodhero.Models;

public class Request {
    int NGOImage;
    String NGO_name;
    String request_time;

    public Request(int pic, String NGO_name, String request_time) {
        this.NGOImage = pic;
        this.NGO_name = NGO_name;
        this.request_time = request_time;
    }

    public int getPic() {
        return NGOImage;
    }

    public void setPic(int pic) {
        this.NGOImage = pic;
    }

    public String getNGO_name() {
        return NGO_name;
    }

    public void setNGO_name(String NGO_name) {
        this.NGO_name = NGO_name;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }
}
