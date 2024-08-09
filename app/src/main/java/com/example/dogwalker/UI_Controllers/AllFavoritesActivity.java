package com.example.dogwalker.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogwalker.R;
import com.example.dogwalker.Adapters.FavoriteAdapter;
import com.example.dogwalker.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class AllFavoritesActivity extends AppCompatActivity {
    private MaterialTextView allFavorites_TXT_pageName;
    private ShapeableImageView allFavorites_IMG_background;
    private RecyclerView allFavorites_LST;
    private ShapeableImageView allFavorites_IMG_back;
    private DataManager manager;
    private FavoriteAdapter favoriteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_favorites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.old_paper_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(allFavorites_IMG_background);

        initViews();
    }


    private void backClicked() {
        Intent intent = new Intent(AllFavoritesActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        allFavorites_IMG_back.setOnClickListener(v -> backClicked());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        allFavorites_LST.setLayoutManager(linearLayoutManager);

        this.favoriteAdapter = new FavoriteAdapter(this, this.manager.getMyUser().getFavorites());

        allFavorites_LST.setAdapter(favoriteAdapter);

        favoriteAdapter.setDogWalkerCallback((dogWalker, position) -> {

            Intent intent = new Intent(AllFavoritesActivity.this, DogWalkerActivity.class);
            intent.putExtra("rid", dogWalker.getRid());
            intent.putExtra("cameFromFavorites", true);
            startActivity(intent);
        });
    }


    private void findViews() {
        allFavorites_TXT_pageName = findViewById(R.id.allFavorites_TXT_pageName);
        allFavorites_IMG_background = findViewById(R.id.allFavorites_IMG_background);
        allFavorites_LST = findViewById(R.id.allFavorites_LST);
        allFavorites_IMG_back = findViewById(R.id.allFavorites_IMG_back);
    }
}