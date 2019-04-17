package com.colman.finalproject.model;

import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.model.firebase.IFirebaseManager;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.google.firebase.Timestamp;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class Model {
    private static Model _instance;
    private IFirebaseManager mFirebaseManager = FirebaseManager.getInstance();

    private Model() {
    }

    public static Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

    public boolean isUserLoggedIn() {
        return mFirebaseManager.isUserLoggedIn();
    }

    public String getUserUid() {
        return mFirebaseManager.getUserUid();
    }

    public void registerUser(String email, String password, String userName) {
        mFirebaseManager.registerUser(email, password, userName);
    }

    public void signInUser(String email, String password) {
        mFirebaseManager.signInUser(email, password);
    }

    public void logout() {
        mFirebaseManager.logout();
    }

    public void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mFirebaseManager.observeSignedInLiveData(lifecycleOwner, observer);
    }

    public void getAllProperties() {
        mFirebaseManager.getAllProperties();
    }

    public void getAllProperties(Timestamp from) {
        mFirebaseManager.getAllProperties(from);
    }

    public void observePropertiesLiveData(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer) {
        mFirebaseManager.observePropertiesLiveData(lifecycleOwner, observer);
    }

    public void observeCommentsLiveData(LifecycleOwner lifecycleOwner, Observer<List<Comment>> observer) {
        mFirebaseManager.observeCommentsLiveData(lifecycleOwner, observer);
    }

    public void addComment(Comment comment) {
        mFirebaseManager.addComment(comment);
    }

    public void getCommentsForProperty(int propertyId) {
        mFirebaseManager.getCommentsForProperty(propertyId);
    }

}
