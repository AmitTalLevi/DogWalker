package com.example.dogwalker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogwalker.Interfaces.DogCallback;
import com.example.dogwalker.R;
import com.example.dogwalker.Models.Dog;
import com.example.dogwalker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {
    private Context context;
    private ArrayList<Dog> dogs;
    private DogCallback dogCallback;

    public DogAdapter(Context context, ArrayList<Dog> dogs) {
        this.context = context;
        this.dogs = dogs;
    }

    public DogAdapter setDogCallback(DogCallback dogCallback) {
        this.dogCallback = dogCallback;
        return this;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = getItem(position);
        ImageLoader.getInstance().load(dog.getPhoto(), holder.dog_IMG_image);
        holder.dog_LBL_name.setText(dog.getName());
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }


    private Dog getItem(int position) {
        return dogs.get(position);
    }


    public class DogViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView dog_IMG_image;
        private MaterialTextView dog_LBL_name;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            dog_IMG_image = itemView.findViewById(R.id.dog_IMG_image);
            dog_LBL_name = itemView.findViewById(R.id.dog_LBL_name);

            itemView.setOnClickListener(v -> {
                if (dogCallback != null)
                    dogCallback.dogClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });
        }
    }
}
