package com.example.dogwalker.Models;

import android.net.Uri;

import com.example.dogwalker.Utilities.DataManager;

public class Dog {
    private int rid;
    private String name = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    private Uri photo;

    public Dog() {
    }

    public Dog(String name, String phone, String email, String address, Uri photo) {
        DataManager manager = DataManager.getInstance();
        this.rid = manager.generateDogID();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public Dog setName(String name) {
        this.name = name;
        return this;
    }

    public Uri getPhoto() {
        return photo;
    }

    public Dog setPhoto(Uri photo) {
        this.photo = photo;
        return this;
    }

    public int getRid() {
        return rid;
    }

    public Dog setRid(int rid) {
        this.rid = rid;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Dog setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Dog setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Dog setAddress(String address) {
        this.address = address;
        return this;
    }
}
