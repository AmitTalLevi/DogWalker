package com.example.dogwalker.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.dogwalker.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;


public class MenuActivity extends AppCompatActivity {
    private ShapeableImageView menu_IMG_background;
    private ShapeableImageView menu_IMG_addDogWalker;
    private ShapeableImageView menu_IMG_addDog;
    private ShapeableImageView menu_IMG_allDogWalkers;
    private ShapeableImageView menu_IMG_allDogs;
    private ShapeableImageView menu_IMG_favorites;
    private ShapeableImageView menu_IMG_logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(menu_IMG_background);

        initViews();
    }


    private void signOutClicked() {
        logOut();
    }


    private void logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MenuActivity.this, LogInActivity.class));
                        finish();
                    }
                });
    }


    private void addDogWalkerClicked() {
        Intent intent = new Intent(MenuActivity.this, AddDogWalkerActivity.class);
        startActivity(intent);
    }

    private void addDogClicked() {
        Intent intent = new Intent(MenuActivity.this, AddDogActivity.class);
        startActivity(intent);
    }

    private void allDogWalkersClicked() {
        Intent intent = new Intent(MenuActivity.this, AllDogWalkersActivity.class);
        startActivity(intent);
        finish();
    }

    private void allDogsClicked() {
        Intent intent = new Intent(MenuActivity.this, AllDogsActivity.class);
        startActivity(intent);
        finish();
    }

    private void favoritesClicked() {
        Intent intent = new Intent(MenuActivity.this, AllFavoritesActivity.class);
        startActivity(intent);
        finish();
    }


    private void initViews() {
        menu_IMG_addDogWalker.setOnClickListener(v -> addDogWalkerClicked());
        menu_IMG_addDog.setOnClickListener(v -> addDogClicked());
        menu_IMG_allDogWalkers.setOnClickListener(v -> allDogWalkersClicked());
        menu_IMG_allDogs.setOnClickListener(v -> allDogsClicked());
        menu_IMG_favorites.setOnClickListener(v -> favoritesClicked());
        menu_IMG_logOut.setOnClickListener(v -> signOutClicked());
    }

    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_IMG_addDogWalker = findViewById(R.id.menu_IMG_addDogWalker);
        menu_IMG_addDog = findViewById(R.id.menu_IMG_addDog);
        menu_IMG_allDogWalkers = findViewById(R.id.menu_IMG_allDogWalkers);
        menu_IMG_allDogs = findViewById(R.id.menu_IMG_allDogs);
        menu_IMG_favorites = findViewById(R.id.menu_IMG_favorites);
        menu_IMG_logOut = findViewById(R.id.menu_IMG_logOut);
    }
}