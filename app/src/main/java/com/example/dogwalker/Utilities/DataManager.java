package com.example.dogwalker.Utilities;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dogwalker.Models.DogWalker;
import com.example.dogwalker.Models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class DataManager {
    private static DataManager instance;
    private FirebaseUser user;
    private User myUser;
    private DatabaseReference ref;
    private FirebaseDatabase firebaseRTDatabase;


    private DataManager() {
    }


    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }


    public FirebaseUser getUser() {
        return user;
    }


    public void setUser(FirebaseUser user) {
        this.user = user;
        this.myUser = new User(this.user.getUid());
        this.firebaseRTDatabase = FirebaseDatabase.getInstance();
        this.ref = firebaseRTDatabase.getReference(this.myUser.getUid() + "");
    }


    public User getMyUser() {
        return myUser;
    }


    public void addFavorite(DogWalker dogWalker, int rid) {
        for (int i = 0; i < this.myUser.getDogWalkers().size(); i++) {
            if (this.myUser.getDogWalkers().get(i).getRid() == rid) {
                this.myUser.getDogWalkers().get(i).setFavorite(true);
                this.myUser.getFavorites().add(dogWalker);
            }
        }

        addDogWalkerToAllFavoritesOnFirebase(dogWalker);
    }


    public void removeFavorite(int rid) {
        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (this.myUser.getFavorites().get(i).getRid() == rid) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getDogWalkers().size(); i++) {
            if (this.myUser.getDogWalkers().get(i).getRid() == rid) {
                this.myUser.getDogWalkers().get(i).setFavorite(false);
            }
        }

        removeDogWalkerFromAllFavoritesOnFirebase(getDogWalkerById(rid), false);
    }


    public void deleteDogWalker(int rid) {
        deleteDogWalkerFromFirebase(getDogWalkerById(rid));

        for (int i = 0; i < this.myUser.getFavorites().size(); i++) {
            if (this.myUser.getFavorites().get(i).getRid() == rid) {
                this.myUser.getFavorites().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getDogWalkers().size(); i++) {
            if (this.myUser.getDogWalkers().get(i).getRid() == rid) {
                this.myUser.getDogWalkers().remove(i);
            }
        }

        for (int i = 0; i < this.myUser.getMyDogWalkersRids().size(); i++) {
            if (this.myUser.getMyDogWalkersRids().get(i) == rid)
                this.myUser.getMyDogWalkersRids().remove(i);
        }
    }


    public void addDogWalker(DogWalker dogWalker) {
        this.myUser.getDogWalkers().add(dogWalker);

        addDogWalkerToAllDogWalkersOnFirebase(dogWalker);
    }


    public DatabaseReference getRef() {
        return ref;
    }


    // init user's data from firebase when when finish the authentication.
    // if the user exist, get his data.
    // If not create new user in firebase.
    public void initUserDataFromFirebase() {
        DatabaseReference myRef = firebaseRTDatabase.getReference(getUser().getUid()).child("all_dog_walkers");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through each dog walker ID
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {

                    DataSnapshot snapshot = iterator.next();
                    int id = Integer.parseInt(snapshot.getKey());
                    // Retrieve the data for that dog walker
                    String name = snapshot.child("name").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String uri = snapshot.child("uri").getValue(String.class);
                    boolean isFavorite = snapshot.child("isFavorite").getValue(Boolean.class);

                    DogWalker dogWalker = new DogWalker();
                    dogWalker.setName(name).setDescription(description).setFavorite(isFavorite).setRid(id);
                    if (uri != null)
                        dogWalker.setPhoto(Uri.parse(uri));
                    else
                        dogWalker.setPhoto(null);

                    myUser.getDogWalkers().add(dogWalker);
                }
                myUser.initFavorites();
                myUser.initMyDogWalkersRids();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.w("DogWalkerData", "Failed to read value.", databaseError.toException());
            }
        });
    }


    //Add dog walker to all dog walkers list.
    private void addDogWalkerToAllDogWalkersOnFirebase(DogWalker dogWalker) {
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("id").setValue(dogWalker.getRid());
        if (dogWalker.getPhoto() != null)
            this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("uri").setValue(dogWalker.getPhoto().toString());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("isFavorite").setValue(dogWalker.isFavorite());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("name").setValue(dogWalker.getName());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("phone").setValue(dogWalker.getPhone());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("email").setValue(dogWalker.getEmail());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("address").setValue(dogWalker.getAddress());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("description").setValue(dogWalker.getDescription());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("hourlyWage").setValue(dogWalker.getHourlyWage());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("experience").setValue(dogWalker.getExperience());
    }


    // update the user's data: 1. The favorite status of specific dogWalker in all dog walkers list.
    //                         2. Add the dogWalker to all Favorites list.
    public void addDogWalkerToAllFavoritesOnFirebase(DogWalker dogWalker) {
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("isFavorite").setValue(dogWalker.isFavorite());

        this.ref.child("all_favorites").child("" + dogWalker.getRid()).child("id").setValue(dogWalker.getRid());
        if (dogWalker.getPhoto() != null)
            this.ref.child("all_favorites").child("" + dogWalker.getRid()).child("uri").setValue(dogWalker.getPhoto().toString());
        this.ref.child("all_favorites").child("" + dogWalker.getRid()).child("isFavorite").setValue(dogWalker.isFavorite());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("name").setValue(dogWalker.getName());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("phone").setValue(dogWalker.getPhone());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("email").setValue(dogWalker.getEmail());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("address").setValue(dogWalker.getAddress());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("description").setValue(dogWalker.getDescription());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("hourlyWage").setValue(dogWalker.getHourlyWage());
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("experience").setValue(dogWalker.getExperience());
    }


    // update the user's all dog walkers and all favorite lists when deleting a dog walker.
    public void deleteDogWalkerFromFirebase(DogWalker dogWalker) {
        this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).removeValue();

        removeDogWalkerFromAllFavoritesOnFirebase(dogWalker, true);
    }


    // update the user's data: 1. The favorite status of specific dog walker in all dog walkers list.
    //                         2. Remove the dog walker from all Favorites list.
    public void removeDogWalkerFromAllFavoritesOnFirebase(DogWalker dogWalker, Boolean dogWalkerDeleted) {
        this.ref.child("all_favorites").child("" + dogWalker.getRid()).removeValue();

        if (!dogWalkerDeleted)
            this.ref.child("all_dog_walkers").child("" + dogWalker.getRid()).child("isFavorite").setValue(dogWalker.isFavorite());
    }


    public DogWalker getDogWalkerById(int rid) {
        for (int i = 0; i < this.getMyUser().getDogWalkers().size(); i++) {
            if (this.getMyUser().getDogWalkers().get(i).getRid() == rid)
                return this.getMyUser().getDogWalkers().get(i);
        }
        return null;
    }


    public int generateID() {
        int id = 555;

        while (myUser.getMyDogWalkersRids().contains(id))
            id++;

        myUser.getMyDogWalkersRids().add(id);

        return id;
    }
}
