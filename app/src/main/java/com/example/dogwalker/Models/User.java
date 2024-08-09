package com.example.dogwalker.Models;

import java.util.ArrayList;

public class User {
    private String uid;
    private ArrayList<Integer> myDogWalkersRids;
    private ArrayList<DogWalker> dogWalkers;
    private ArrayList<DogWalker> favorites;


    public User() {
        this.dogWalkers = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.myDogWalkersRids = new ArrayList<>();
    }


    public User(String uid) {
        this.uid = uid;
        this.dogWalkers = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.myDogWalkersRids = new ArrayList<>();
    }


    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }


    public ArrayList<DogWalker> getDogWalkers() {
        return dogWalkers;
    }


    public void setDogWalkers(ArrayList<DogWalker> dogWalkers) {
        this.dogWalkers = dogWalkers;
    }


    public ArrayList<DogWalker> getFavorites() {
        return favorites;
    }


    public void setFavorites(ArrayList<DogWalker> favorites) {
        this.favorites = favorites;
    }


    public void initFavorites() {
        for (int i = 0; i < this.dogWalkers.size(); i++) {
            if (dogWalkers.get(i).isFavorite())
                this.favorites.add(dogWalkers.get(i));
        }
    }


    public ArrayList<Integer> getMyDogWalkersRids() {
        return myDogWalkersRids;
    }


    public void initMyDogWalkersRids() {
        for (int i = 0; i < this.dogWalkers.size(); i++)
            this.myDogWalkersRids.add(dogWalkers.get(i).getRid());
    }
}
