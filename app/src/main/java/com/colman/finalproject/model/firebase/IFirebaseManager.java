package com.colman.finalproject.model.firebase;

import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.google.firebase.Timestamp;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

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

    void observeCommentsLiveData(LifecycleOwner lifecycleOwner, Observer<List<Comment>> observer);

    void getCommentsForProperty(int propertyId);

    void addComment(Comment comment);

}

