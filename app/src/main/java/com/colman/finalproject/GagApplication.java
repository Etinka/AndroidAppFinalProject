package com.colman.finalproject;

import android.app.Application;

import com.colman.finalproject.model.Model;

public class GagApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Model.getInstance().init(this);
    }
}
