package com.colman.finalproject.model.firebase;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.colman.finalproject.models.Comment;

import java.util.List;

public interface IFirebaseManager {

    boolean isUserLoggedIn();

    String getUserUid();

    void registerUser(String email, String password, String userName);

    void signInUser(String email, String password);

    void logout();

    void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer);

    void getAllProperties(long from, IFirebaseListener listener);

    void updatePropertyListener(long from, IFirebaseListener listener);

    void observeCommentsLiveData(LifecycleOwner lifecycleOwner, Observer<List<Comment>> observer);

    void getCommentsForProperty(int propertyId);

    void addComment(Comment comment);

}

