package com.example.dogwalker.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.dogwalker.R;
import com.example.dogwalker.Models.DogWalker;
import com.example.dogwalker.Utilities.DataManager;
import com.example.dogwalker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class DogWalkerActivity extends AppCompatActivity {
    private ShapeableImageView dog_walker_IMG_background;
    private ShapeableImageView dog_walker_IMG_photo;
    private MaterialTextView dog_walker_TXT_name;
    private MaterialTextView dog_walker_TXT_phone;
    private MaterialTextView dog_walker_TXT_email;
    private MaterialTextView dog_walker_TXT_address;
    private MaterialTextView dog_walker_TXT_description;
    private MaterialTextView dog_walker_TXT_hourlyWage;
    private MaterialTextView dog_walker_TXT_experience;
    private ShapeableImageView dog_walker_IMG_back;
    private ShapeableImageView dog_walker_IMG_addFavorite;
    private ShapeableImageView dog_walker_IMG_removeFavorite;
    private ShapeableImageView dog_walker_IMG_delete;
    private int rid;
    private boolean cameFromAllDogWalkers;
    private boolean cameFromFavorites;
    private DataManager manager;
    private DogWalker dogWalker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dog_walker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        Intent intent = getIntent();
        this.rid = intent.getIntExtra("rid", -1);

        this.dogWalker = manager.getDogWalkerById(rid);

        this.cameFromAllDogWalkers = intent.getBooleanExtra("cameFromAllDogWalkers", false);
        this.cameFromFavorites = intent.getBooleanExtra("cameFromFavorites", false);

        findViews();

        ImageLoader.getInstance().load(this.dogWalker.getPhoto(), this.dog_walker_IMG_photo);

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(dog_walker_IMG_background);

        initViews();
    }


    private void backClicked() {
        if (this.cameFromAllDogWalkers) {
            Intent intent = new Intent(DogWalkerActivity.this, AllDogWalkersActivity.class);
            this.cameFromAllDogWalkers = false;
            startActivity(intent);
            this.finish();
        } else if (this.cameFromFavorites) {
            Intent intent = new Intent(DogWalkerActivity.this, AllFavoritesActivity.class);
            this.cameFromFavorites = false;
            startActivity(intent);
            this.finish();
        }
    }


    private void addFavoriteClicked() {
        dog_walker_IMG_addFavorite.setVisibility(View.INVISIBLE);
        dog_walker_IMG_removeFavorite.setVisibility(View.VISIBLE);

        manager.addFavorite(this.dogWalker, this.rid);
    }


    private void removeFavoriteClicked() {
        dog_walker_IMG_addFavorite.setVisibility(View.VISIBLE);
        dog_walker_IMG_removeFavorite.setVisibility(View.INVISIBLE);

        manager.removeFavorite(this.rid);
    }


    private void deleteDogWalkerClicked() {

        manager.deleteDogWalker(this.rid);

        Intent intent = new Intent(DogWalkerActivity.this, AllDogWalkersActivity.class);
        this.cameFromAllDogWalkers = false;
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        dog_walker_TXT_name.setText(this.dogWalker.getName());
        dog_walker_TXT_phone.setText(this.dogWalker.getPhone());
        dog_walker_TXT_email.setText(this.dogWalker.getEmail());
        dog_walker_TXT_address.setText(this.dogWalker.getAddress());
        dog_walker_TXT_description.setText(this.dogWalker.getDescription());
        dog_walker_TXT_hourlyWage.setText(String.valueOf(this.dogWalker.getHourlyWage()));
        dog_walker_TXT_experience.setText(String.valueOf(this.dogWalker.getExperience()));

        if (this.dogWalker.isFavorite()) {
            dog_walker_IMG_addFavorite.setVisibility(View.INVISIBLE);
            dog_walker_IMG_removeFavorite.setVisibility(View.VISIBLE);
        } else {
            dog_walker_IMG_addFavorite.setVisibility(View.VISIBLE);
            dog_walker_IMG_removeFavorite.setVisibility(View.INVISIBLE);
        }

        if (this.cameFromFavorites)
            dog_walker_IMG_delete.setVisibility(View.INVISIBLE);

        dog_walker_IMG_removeFavorite.setOnClickListener(v -> removeFavoriteClicked());
        dog_walker_IMG_addFavorite.setOnClickListener(v -> addFavoriteClicked());
        dog_walker_IMG_back.setOnClickListener(v -> backClicked());
        dog_walker_IMG_delete.setOnClickListener(v -> deleteDogWalkerClicked());
    }


    private void findViews() {
        dog_walker_IMG_background = findViewById(R.id.dog_walker_IMG_background);
        dog_walker_IMG_photo = findViewById(R.id.dog_walker_IMG_photo);
        dog_walker_TXT_name = findViewById(R.id.dog_walker_TXT_name);
        dog_walker_TXT_phone = findViewById(R.id.dog_walker_TXT_phone);
        dog_walker_TXT_email = findViewById(R.id.dog_walker_TXT_email);
        dog_walker_TXT_address = findViewById(R.id.dog_walker_TXT_address);
        dog_walker_TXT_description = findViewById(R.id.dog_walker_TXT_description);
        dog_walker_TXT_hourlyWage = findViewById(R.id.dog_walker_TXT_hourlyWage);
        dog_walker_TXT_experience = findViewById(R.id.dog_walker_TXT_experience);

        dog_walker_IMG_back = findViewById(R.id.dog_walker_IMG_back);
        dog_walker_IMG_addFavorite = findViewById(R.id.dog_walker_IMG_addFavorite);
        dog_walker_IMG_removeFavorite = findViewById(R.id.dog_walker_IMG_removeFavorite);
        dog_walker_IMG_delete = findViewById(R.id.dog_walker_IMG_deleteDogWalker);
    }
}