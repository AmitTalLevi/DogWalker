package com.example.dogwalker.UI_Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.dogwalker.R;
import com.example.dogwalker.Models.DogWalker;
import com.example.dogwalker.Utilities.DataManager;
import com.example.dogwalker.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;


public class AddDogWalkerActivity extends AppCompatActivity {
    private ShapeableImageView addDogWalker_IMG_background;
    private ShapeableImageView addDogWalker_IMG_dishPhoto;
    private LinearProgressIndicator addDogWalker_PI_uploadIndicator;
    private ShapeableImageView addDogWalker_IMG_addPhoto;
    private ShapeableImageView addDogWalker_IMG_save;
    private ShapeableImageView addDogWalker_IMG_cancel;
    private EditText addDogWalker_TXT_name;
    private EditText addDogWalker_TXT_phone;
    private EditText addDogWalker_TXT_email;
    private EditText addDogWalker_TXT_address;
    private EditText addDogWalker_TXT_description;
    private EditText addDogWalker_TXT_hourlyWage;
    private EditText addDogWalker_TXT_experience;
    private Uri imageUri;
    private DataManager manager;
    private StorageReference storageReference;
    private Uri firebaseImage;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            imageUri = result.getData().getData();
                            Glide.with(getApplicationContext())
                                    .load(imageUri)
                                    .into(addDogWalker_IMG_dishPhoto);
                            SignalManager.getInstance().toast("Image selected successfully");
                            uploadImage(imageUri);
                        } else {
                            SignalManager.getInstance().toast("No image selected");
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_dog_walker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        FirebaseApp.initializeApp(AddDogWalkerActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(addDogWalker_IMG_background);

        initViews();
    }


    private void addPhotoClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }


    private void uploadImage(Uri image) {
        StorageReference reference = storageReference.child(UUID.randomUUID().toString());
        reference.putFile(image)
                .addOnSuccessListener(taskSnapshot -> {
                    reference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                firebaseImage = uri;
                                addDogWalker_IMG_save.setVisibility(View.VISIBLE);
                                SignalManager.getInstance().toast("Image uploaded successfully");
                            })
                            .addOnFailureListener(e -> {
                                SignalManager.getInstance().toast("Fail to get the download url");
                            });
                })
                .addOnFailureListener(e -> {
                    SignalManager.getInstance().toast("Fail to upload image");
                })
                .addOnProgressListener(snapshot -> {
                    addDogWalker_IMG_save.setVisibility(View.INVISIBLE);
                    addDogWalker_PI_uploadIndicator.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                    addDogWalker_PI_uploadIndicator.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
                });
    }


    private void cancelClicked() {
        Intent intent = new Intent(AddDogWalkerActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void saveClicked() {
        String name = addDogWalker_TXT_name.getText().toString();
        String phone = addDogWalker_TXT_phone.getText().toString();
        String email = addDogWalker_TXT_email.getText().toString();
        String address = addDogWalker_TXT_address.getText().toString();
        String description = addDogWalker_TXT_description.getText().toString();
        double hourlyWage = Double.parseDouble(addDogWalker_TXT_hourlyWage.getText().toString());
        double experience = Double.parseDouble(addDogWalker_TXT_experience.getText().toString());

        DogWalker dogWalker;

        if (this.firebaseImage == null)
            dogWalker = new DogWalker(name, phone, email, address, description, hourlyWage, experience, this.imageUri);
        else
            dogWalker = new DogWalker(name, phone, email, address, description, hourlyWage, experience, this.firebaseImage);

        this.manager.addDogWalker(dogWalker);

        Intent intent = new Intent(AddDogWalkerActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }


    private void initViews() {
        addDogWalker_IMG_addPhoto.setOnClickListener(v -> addPhotoClicked());
        addDogWalker_IMG_save.setOnClickListener(v -> saveClicked());
        addDogWalker_IMG_cancel.setOnClickListener((v -> cancelClicked()));
    }


    private void findViews() {
        addDogWalker_IMG_background = findViewById(R.id.addDogWalker_IMG_background);

        addDogWalker_IMG_dishPhoto = findViewById(R.id.addDogWalker_IMG_photo);
        addDogWalker_PI_uploadIndicator = findViewById(R.id.addDogWalker_PI_uploadIndicator);
        addDogWalker_IMG_addPhoto = findViewById(R.id.addDogWalker_IMG_addPhoto);

        addDogWalker_TXT_name = findViewById(R.id.addDogWalker_TXT_name);
        addDogWalker_TXT_phone = findViewById(R.id.addDogWalker_TXT_phone);
        addDogWalker_TXT_email = findViewById(R.id.addDogWalker_TXT_email);
        addDogWalker_TXT_address = findViewById(R.id.addDogWalker_TXT_address);
        addDogWalker_TXT_description = findViewById(R.id.addDogWalker_TXT_description);
        addDogWalker_TXT_hourlyWage = findViewById(R.id.addDogWalker_TXT_hourlyWage);
        addDogWalker_TXT_experience = findViewById(R.id.addDogWalker_TXT_experience);

        addDogWalker_IMG_save = findViewById(R.id.addDogWalker_IMG_save);
        addDogWalker_IMG_cancel = findViewById(R.id.addDogWalker_IMG_cancel);
    }
}
