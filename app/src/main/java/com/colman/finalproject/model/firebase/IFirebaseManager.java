package com.colman.finalproject.model.firebase;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.colman.finalproject.models.Comment;

public interface IFirebaseManager {

    boolean isUserLoggedIn();

    String getUserUid();

    void registerUser(String email, String password, String userName);

    void signInUser(String email, String password);

    void logout();

    void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer);

    void getAllProperties(long from, IFirebaseListener listener);

    void updatePropertyListener(long from, IFirebaseListener listener);

    void getCommentsForProperty(int propertyId, IFirebaseListener listener);

    void addComment(Comment comment);

    void deleteComment(Comment comment);

}

