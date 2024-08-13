package com.example.dogwalker.Models;

import android.net.Uri;

import com.example.dogwalker.Utilities.DataManager;


public class DogWalker {
    private int rid;
    private String name = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    private String description = "";
    private double hourlyWage = 0;
    private double experience = 0;
    private Uri photo;
    private boolean isFavorite = false;
    private final DataManager manager = DataManager.getInstance();


    public DogWalker() {
    }


    public DogWalker(String name,String phone,String email,String address, String description,double hourlyWage, double experience, Uri photo) {
        this.rid = manager.generateID();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.hourlyWage = hourlyWage;
        this.experience = experience;
        this.photo = photo;
    }


    public DogWalker(int rid,String name,String phone,String email,String address, String description,double hourlyWage, double experience, Uri photo, Boolean isFavorite) {
        this.rid = rid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.hourlyWage = hourlyWage;
        this.experience = experience;
        this.photo = photo;
        this.isFavorite = isFavorite;
    }


    public String getName() {
        return name;
    }


    public DogWalker setName(String name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return description;
    }


    public DogWalker setDescription(String description) {
        this.description = description;
        return this;
    }


    public Uri getPhoto() {
        return photo;
    }


    public DogWalker setPhoto(Uri photo) {
        this.photo = photo;
        return this;
    }


    public int getRid() {
        return rid;
    }


    public DogWalker setRid(int rid) {
        this.rid = rid;
        return this;
    }


    public boolean isFavorite() {
        return isFavorite;
    }


    public DogWalker setFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public DogWalker setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DogWalker setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DogWalker setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public DogWalker setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
        return this;
    }

    public double getExperience() {
        return experience;
    }

    public DogWalker setExperience(double experience) {
        this.experience = experience;
        return this;
    }
}
