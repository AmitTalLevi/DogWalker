package com.example.dogwalker.Utilities;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dogwalker.R;

public class ImageLoader {
    private static ImageLoader instance;
    private static Context appContext;


    private ImageLoader(Context context) {
        appContext = context;
    }


    public static ImageLoader getInstance() {
        return instance;
    }


    public static ImageLoader initImageLoader(Context context) {
        if (instance == null)
            instance = new ImageLoader(context);
        return instance;
    }


    public void load(Uri uri, ImageView imageView) {
        Glide.
                with(appContext)
                .load(uri)
                .placeholder(R.drawable.ic_no_image)
                .into(imageView);
    }
}
