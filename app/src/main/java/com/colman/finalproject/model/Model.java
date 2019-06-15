package com.colman.finalproject.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.model.firebase.IFirebaseListener;
import com.colman.finalproject.model.firebase.IFirebaseManager;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.room.PropertyRepository;
import com.colman.finalproject.utils.Logger;

import java.util.List;

public class Model {
    private Logger logger = new Logger(this.getClass().getSimpleName());
    private static String LAST_UPDATED_KEY = "lastUpdatedTimestamp";

    private static Model _instance;
    private IFirebaseManager mFirebaseManager = FirebaseManager.getInstance();
    private PropertyRepository mRepository;
    private SharedPreferences sharedPreferences;

    private Model() {
    }

    public static Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

    public void init(Application application) {
        mRepository = new PropertyRepository(application);
        sharedPreferences = application.getSharedPreferences("RepositoryPrefs", Context.MODE_PRIVATE);
        mFirebaseManager.getAllProperties(getLastUpdatedTimestamp(), firebaseListener);
    }

    private IFirebaseListener firebaseListener = new IFirebaseListener() {
        @Override
        public void updatedProperties(List<Property> properties) {
            logger.logDebug("Received update from Firebase");
            long timestamp = getLastUpdatedTimestamp();

            long lastUpdated = timestamp;
            if (properties != null) {
                for (Property property : properties) {
                    mRepository.insert(property);
                    if (property.getLastUpdate() != null && lastUpdated < property.getLastUpdate().toDate().getTime()) {
                        lastUpdated = property.getLastUpdate().toDate().getTime();
                    }
                }
                if (lastUpdated != timestamp) {
                    mFirebaseManager.updatePropertyListener(lastUpdated, firebaseListener);
                }
                setLastUpdatedTimestamp(lastUpdated);
            }
        }
    };

    private long getLastUpdatedTimestamp() {
        return sharedPreferences.getLong(LAST_UPDATED_KEY, -1);
    }

    private void setLastUpdatedTimestamp(long lastUpdated) {
        sharedPreferences.edit().putLong(LAST_UPDATED_KEY, lastUpdated).apply();
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

    public void observePropertiesLiveData(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer) {
        mRepository.getAllProperties().observe(lifecycleOwner, observer);
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

    public void observePropertyLiveData(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mRepository.getPropertyById(propertyId).observe(lifecycleOwner, observer);
    }
}
