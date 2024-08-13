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
import com.example.dogwalker.Adapters.DogWalkerAdapter;
import com.example.dogwalker.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;


public class AllDogWalkersActivity extends AppCompatActivity {
    private ShapeableImageView allDogWalkers_IMG_background;
    private RecyclerView allDogWalkers_LST;
    private ShapeableImageView allDogWalkers_IMG_back;
    private DataManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_dog_walkers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(allDogWalkers_IMG_background);

        initViews();
    }


    private void backClicked() {
        Intent intent = new Intent(AllDogWalkersActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        allDogWalkers_IMG_back.setOnClickListener(v -> backClicked());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        allDogWalkers_LST.setLayoutManager(linearLayoutManager);

        DogWalkerAdapter dogWalkerAdapter = new DogWalkerAdapter(this, this.manager.getMyUser().getDogWalkers());

        allDogWalkers_LST.setAdapter(dogWalkerAdapter);

        dogWalkerAdapter.setDogWalkerCallback((dogWalker, position) -> {

            Intent intent = new Intent(AllDogWalkersActivity.this, DogWalkerActivity.class);
            intent.putExtra("rid", dogWalker.getRid());
            intent.putExtra("cameFromAllDogWalkers", true);
            startActivity(intent);
        });
    }


    private void findViews() {
        allDogWalkers_IMG_background = findViewById(R.id.allDogWalkers_IMG_background);
        allDogWalkers_LST = findViewById(R.id.allDogWalkers_LST);
        allDogWalkers_IMG_back = findViewById(R.id.allDogWalkers_IMG_back);
    }
}