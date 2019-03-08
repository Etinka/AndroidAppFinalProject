package com.colman.finalproject.model.firebase;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.colman.finalproject.models.Property;
import com.google.firebase.Timestamp;

import java.util.List;

public interface IFirebaseManager {

    boolean isUserLoggedIn();

    String getUserUid();

    void registerUser(String email, String password, String userName);

    void signInUser(String email, String password);

    void logout();

    void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer);


    void getAllProperties();

    void getAllProperties(Timestamp from);

    void observePropertiesLiveData(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer);

}

