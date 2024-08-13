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
import com.example.dogwalker.Adapters.DogAdapter;
import com.example.dogwalker.R;
import com.example.dogwalker.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;


public class AllDogsActivity extends AppCompatActivity {
    private ShapeableImageView allDogs_IMG_background;
    private RecyclerView allDogs_LST;
    private ShapeableImageView allDogs_IMG_back;
    private DataManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_dogs);
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
                .into(allDogs_IMG_background);

        initViews();
    }


    private void backClicked() {
        Intent intent = new Intent(AllDogsActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void initViews() {
        allDogs_IMG_back.setOnClickListener(v -> backClicked());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        allDogs_LST.setLayoutManager(linearLayoutManager);

        DogAdapter dogAdapter = new DogAdapter(this, this.manager.getMyUser().getDogs());

        allDogs_LST.setAdapter(dogAdapter);

        dogAdapter.setDogCallback((dog, position) -> {

            Intent intent = new Intent(AllDogsActivity.this, DogActivity.class);
            intent.putExtra("rid", dog.getRid());
            intent.putExtra("cameFromAllDogs", true);
            startActivity(intent);
        });
    }


    private void findViews() {
        allDogs_IMG_background = findViewById(R.id.allDogs_IMG_background);
        allDogs_LST = findViewById(R.id.allDogs_LST);
        allDogs_IMG_back = findViewById(R.id.allDogs_IMG_back);
    }
}