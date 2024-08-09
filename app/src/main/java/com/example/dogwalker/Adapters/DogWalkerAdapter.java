package com.example.dogwalker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogwalker.R;
import com.example.dogwalker.Interfaces.DogWalkerCallback;
import com.example.dogwalker.Models.DogWalker;
import com.example.dogwalker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class DogWalkerAdapter extends RecyclerView.Adapter<DogWalkerAdapter.DogWalkerViewHolder> {
    private Context context;
    private ArrayList<DogWalker> dogWalkers;
    private DogWalkerCallback dogWalkerCallback;


    public DogWalkerAdapter(Context context, ArrayList<DogWalker> dogWalkers) {
        this.context = context;
        this.dogWalkers = dogWalkers;
    }


    public DogWalkerAdapter setDogWalkerCallback(DogWalkerCallback dogWalkerCallback) {
        this.dogWalkerCallback = dogWalkerCallback;
        return this;
    }


    @NonNull
    @Override
    public DogWalkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_walker_item, parent, false);
        return new DogWalkerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DogWalkerViewHolder holder, int position) {
        DogWalker dogWalker = getItem(position);
        ImageLoader.getInstance().load(dogWalker.getPhoto(), holder.dog_walker_IMG_image);
        holder.dog_walker_LBL_name.setText(dogWalker.getName());
    }


    @Override
    public int getItemCount() {
        return dogWalkers.size();
    }


    private DogWalker getItem(int position) {
        return dogWalkers.get(position);
    }


    public class DogWalkerViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView dog_walker_IMG_image;
        private MaterialTextView dog_walker_LBL_name;

        public DogWalkerViewHolder(@NonNull View itemView) {
            super(itemView);
            dog_walker_IMG_image = itemView.findViewById(R.id.dog_walker_IMG_image);
            dog_walker_LBL_name = itemView.findViewById(R.id.dog_walker_LBL_name);

            itemView.setOnClickListener(v -> {
                if (dogWalkerCallback != null)
                    dogWalkerCallback.dogWalkerClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });
        }
    }
}
