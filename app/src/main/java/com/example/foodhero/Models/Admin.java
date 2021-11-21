package com.example.foodhero.Models;

public class Admin {
    private String _id;
    private String email;
    private String password;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String _id, String email, String password) {
        this._id = _id;
        this.email = email;
        this.password = password;
    }
}
