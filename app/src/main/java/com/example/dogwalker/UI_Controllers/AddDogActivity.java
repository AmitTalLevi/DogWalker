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
import com.example.dogwalker.Models.Dog;
import com.example.dogwalker.R;
import com.example.dogwalker.Utilities.DataManager;
import com.example.dogwalker.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;


public class AddDogActivity extends AppCompatActivity {
    private ShapeableImageView addDog_IMG_background;
    private ShapeableImageView addDog_IMG_photo;
    private LinearProgressIndicator addDog_PI_uploadIndicator;
    private ShapeableImageView addDog_IMG_addPhoto;
    private ShapeableImageView addDog_IMG_save;
    private ShapeableImageView addDog_IMG_cancel;
    private EditText addDog_TXT_name;
    private EditText addDog_TXT_phone;
    private EditText addDog_TXT_email;
    private EditText addDog_TXT_address;
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
                                    .into(addDog_IMG_photo);
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
        setContentView(R.layout.activity_add_dog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = DataManager.getInstance();

        FirebaseApp.initializeApp(AddDogActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference();

        findViews();

        Glide
                .with(this)
                .load(R.drawable.background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(addDog_IMG_background);

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
                                addDog_IMG_save.setVisibility(View.VISIBLE);
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
                    addDog_IMG_save.setVisibility(View.INVISIBLE);
                    addDog_PI_uploadIndicator.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                    addDog_PI_uploadIndicator.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
                });
    }


    private void cancelClicked() {
        Intent intent = new Intent(AddDogActivity.this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void saveClicked() {
        String name = addDog_TXT_name.getText().toString();
        String phone = addDog_TXT_phone.getText().toString();
        String email = addDog_TXT_email.getText().toString();
        String address = addDog_TXT_address.getText().toString();

        Dog dog;

        if (this.firebaseImage == null)
            dog = new Dog(name, phone, email, address, this.imageUri);
        else
            dog = new Dog(name, phone, email, address, this.firebaseImage);

        this.manager.addDog(dog);

        Intent intent = new Intent(AddDogActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }


    private void initViews() {
        addDog_IMG_addPhoto.setOnClickListener(v -> addPhotoClicked());
        addDog_IMG_save.setOnClickListener(v -> saveClicked());
        addDog_IMG_cancel.setOnClickListener((v -> cancelClicked()));
    }


    private void findViews() {
        addDog_IMG_background = findViewById(R.id.addDog_IMG_background);

        addDog_IMG_photo = findViewById(R.id.addDog_IMG_photo);
        addDog_PI_uploadIndicator = findViewById(R.id.addDog_PI_uploadIndicator);
        addDog_IMG_addPhoto = findViewById(R.id.addDog_IMG_addPhoto);

        addDog_TXT_name = findViewById(R.id.addDog_TXT_name);
        addDog_TXT_phone = findViewById(R.id.addDog_TXT_phone);
        addDog_TXT_email = findViewById(R.id.addDog_TXT_email);
        addDog_TXT_address = findViewById(R.id.addDog_TXT_address);

        addDog_IMG_save = findViewById(R.id.addDog_IMG_save);
        addDog_IMG_cancel = findViewById(R.id.addDog_IMG_cancel);
    }
}
