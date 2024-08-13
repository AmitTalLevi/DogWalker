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

//view holder is the class that hold single object and creates the connection between
// the data and the view of a single object.
//The adapter connect between all the object and all the views
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<DogWalker> myFavorites;
    private DogWalkerCallback dogWalkerCallback;


    public FavoriteAdapter(Context context, ArrayList<DogWalker> favorites) {
        this.myFavorites = favorites;
    }


    public FavoriteAdapter setDogWalkerCallback(DogWalkerCallback dogWalkerCallback) {
        this.dogWalkerCallback = dogWalkerCallback;
        return this;
    }


    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        //this is the method that brings specific dogWalker and connects the data with the view holder
        DogWalker dogWalker = getItem(position);
        ImageLoader.getInstance().load(dogWalker.getPhoto(), holder.favorite_IMG_image);
        holder.favorite_LBL_name.setText(dogWalker.getName());
    }


    @Override
    public int getItemCount() {
        return myFavorites == null ? 0 : myFavorites.size();
    }


    private DogWalker getItem(int position) {
        return myFavorites.get(position);
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView favorite_IMG_image;
        private MaterialTextView favorite_LBL_name;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_IMG_image = itemView.findViewById(R.id.favorite_IMG_image);
            favorite_LBL_name = itemView.findViewById(R.id.favorite_LBL_name);

            itemView.setOnClickListener(v -> {
                if (dogWalkerCallback != null)
                    dogWalkerCallback.dogWalkerClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });
        }
    }
}
