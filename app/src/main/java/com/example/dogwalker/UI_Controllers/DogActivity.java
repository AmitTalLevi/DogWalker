package com.example.dogwalker.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.dogwalker.Models.Dog;
import com.example.dogwalker.R;
import com.example.dogwalker.Utilities.DataManager;
import com.example.dogwalker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class DogActivity extends AppCompatActivity {
    private ShapeableImageView dog_IMG_background;
    private ShapeableImageView dog_IMG_photo;
    private MaterialTextView dog_TXT_name;
    private MaterialTextView dog_TXT_phone;
    private MaterialTextView dog_TXT_email;
    private MaterialTextView dog_TXT_address;
    private ShapeableImageView dog_IMG_back;
    private ShapeableImageView dog_IMG_delete;
    private int rid;
    private DataManager manager;
    private Dog dog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        Intent intent = getIntent();
        this.rid = intent.getIntExtra("rid", -1);

        this.dog = manager.getDogById(rid);

        findViews();

        ImageLoader.getInstance().load(this.dog.getPhoto(), this.dog_IMG_photo);

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(dog_IMG_background);

        initViews();
    }

    private void backClicked() {
        Intent intent = new Intent(DogActivity.this, AllDogsActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void deleteDogClicked() {

        manager.deleteDog(this.rid);

        Intent intent = new Intent(DogActivity.this, AllDogsActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void initViews() {
        dog_TXT_name.setText(this.dog.getName());
        dog_TXT_phone.setText(this.dog.getPhone());
        dog_TXT_email.setText(this.dog.getEmail());
        dog_TXT_address.setText(this.dog.getAddress());
        dog_IMG_back.setOnClickListener(v -> backClicked());
        dog_IMG_delete.setOnClickListener(v -> deleteDogClicked());
    }

    private void findViews() {
        dog_IMG_background = findViewById(R.id.dog_IMG_background);
        dog_IMG_photo = findViewById(R.id.dog_IMG_photo);
        dog_TXT_name = findViewById(R.id.dog_TXT_name);
        dog_TXT_phone = findViewById(R.id.dog_TXT_phone);
        dog_TXT_email = findViewById(R.id.dog_TXT_email);
        dog_TXT_address = findViewById(R.id.dog_TXT_address);
        dog_IMG_back = findViewById(R.id.dog_IMG_back);
        dog_IMG_delete = findViewById(R.id.dog_IMG_deleteDog);
    }
}