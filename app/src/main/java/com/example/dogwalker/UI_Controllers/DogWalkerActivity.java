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
    private MaterialTextView dog_walker_TXT_name;
    private ShapeableImageView dog_walker_IMG_dishPhoto;
    private MaterialTextView dog_walker_TXT_dishDescription;
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

        ImageLoader.getInstance().load(this.dogWalker.getPhoto(), this.dog_walker_IMG_dishPhoto);

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

        dog_walker_TXT_dishDescription.setText(this.dogWalker.getDescription());

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
        dog_walker_TXT_name = findViewById(R.id.dog_walker_TXT_name);
        dog_walker_IMG_dishPhoto = findViewById(R.id.dog_walker_IMG_dishPhoto);
        dog_walker_TXT_dishDescription = findViewById(R.id.dog_walker_TXT_dishDescription);
        dog_walker_IMG_back = findViewById(R.id.dog_walker_IMG_back);
        dog_walker_IMG_addFavorite = findViewById(R.id.dog_walker_IMG_addFavorite);
        dog_walker_IMG_removeFavorite = findViewById(R.id.dog_walker_IMG_removeFavorite);
        dog_walker_IMG_delete = findViewById(R.id.dog_walker_IMG_deleteDogWalker);
    }
}