package com.example.dogwalker.Models;

import java.util.ArrayList;

public class User {
    private String uid;
    private ArrayList<Integer> myDogsRids;
    private ArrayList<Dog> dogs;
    private ArrayList<Integer> myDogWalkersRids;
    private ArrayList<DogWalker> dogWalkers;
    private ArrayList<DogWalker> favorites;


    public User() {
        this.myDogsRids = new ArrayList<>();
        this.dogs = new ArrayList<>();
        this.dogWalkers = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.myDogWalkersRids = new ArrayList<>();
    }


    public User(String uid) {
        this.uid = uid;
        this.myDogsRids = new ArrayList<>();
        this.dogs = new ArrayList<>();
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

    public ArrayList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<Dog> dogs) {
        this.dogs = dogs;
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

    public ArrayList<Integer> getMyDogsRids() {
        return myDogsRids;
    }

    public ArrayList<Integer> getMyDogWalkersRids() {
        return myDogWalkersRids;
    }

    public void initMyDogWalkersRids() {
        for (int i = 0; i < this.dogWalkers.size(); i++)
            this.myDogWalkersRids.add(dogWalkers.get(i).getRid());
    }

    public void initMyDogsRids() {
        for (int i = 0; i < this.dogs.size(); i++)
            this.myDogsRids.add(dogs.get(i).getRid());
    }
}
