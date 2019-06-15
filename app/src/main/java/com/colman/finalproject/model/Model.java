package com.colman.finalproject.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.model.firebase.IFirebaseManager;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.room.PropertyRepository;
import com.colman.finalproject.utils.Logger;

import java.util.List;

public class Model {
    private Logger logger = new Logger(this.getClass().getSimpleName());

    private static Model _instance;
    private IFirebaseManager mFirebaseManager = FirebaseManager.getInstance();
    private PropertyRepository mRepository;
    private SharedPreferences sharedPreferences;
    private MediatorLiveData<List<Property>> liveDataMerger = new MediatorLiveData<>();

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
        long timestamp = sharedPreferences.getLong("timestamp", -1);
        liveDataMerger.addSource(mRepository.getAllProperties(), value -> {
            logger.logDebug("Received update from Room");
            if (value != null) {
                liveDataMerger.setValue(value);
            }
        });
        liveDataMerger.addSource(mFirebaseManager.getAllProperties(timestamp), value -> {
            logger.logDebug("Received update from Firebase");
            long lastUpdated = timestamp;
            if (value != null) {
                for (Property property : value) {
                    mRepository.insert(property);
                    if (property.getLastUpdate() != null && lastUpdated < property.getLastUpdate().toDate().getTime()) {
                        lastUpdated = property.getLastUpdate().toDate().getTime();
                    }
                }
                if (lastUpdated != timestamp) {
                    mFirebaseManager.updatePropertyListener(lastUpdated);
                }
                sharedPreferences.edit().putLong("timestamp", lastUpdated).apply();
            }
        });
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
        liveDataMerger.observe(lifecycleOwner, observer);
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
