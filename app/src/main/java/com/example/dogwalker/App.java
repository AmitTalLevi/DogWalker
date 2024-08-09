package com.example.dogwalker;

import android.app.Application;

import com.example.dogwalker.Utilities.ImageLoader;
import com.example.dogwalker.Utilities.SignalManager;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SignalManager.init(this);
        ImageLoader.initImageLoader(this);
    }
}
