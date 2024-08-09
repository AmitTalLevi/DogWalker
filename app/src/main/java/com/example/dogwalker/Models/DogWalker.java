package com.example.dogwalker.Models;

import android.net.Uri;

import com.example.dogwalker.Utilities.DataManager;


public class DogWalker {
    private int rid;
    private String name = "";
    private String description = "";
    private Uri photo;
    private boolean isFavorite = false;
    private final DataManager manager = DataManager.getInstance();


    public DogWalker() {
    }


    public DogWalker(String name, String description, Uri photo) {
        this.rid = manager.generateID();
        this.name = name;
        this.description = description;
        this.photo = photo;
    }


    public DogWalker(int rid, String name, String description, Uri photo, Boolean isFavorite) {
        this.rid = rid;
        this.name = name;
        this.description = description;
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
}
