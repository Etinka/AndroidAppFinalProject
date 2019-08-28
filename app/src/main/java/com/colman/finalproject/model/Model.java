package com.colman.finalproject.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.model.firebase.IFirebaseListener;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.room.PropertyRepository;
import com.colman.finalproject.utils.Logger;

import java.util.List;

import javax.annotation.Nullable;

public class Model {
    private Logger mLogger = new Logger(this.getClass().getSimpleName());
    private static String LAST_UPDATED_KEY = "lastUpdatedTimestamp";

    private static Model _instance;
    private FirebaseManager mFirebaseManager = FirebaseManager.getInstance();
    private PropertyRepository mRepository;
    private SharedPreferences mSharedPreferences;

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
        mSharedPreferences = application.getSharedPreferences("RepositoryPrefs", Context.MODE_PRIVATE);
        mFirebaseManager.getAllProperties(getLastUpdatedTimestamp(), firebaseListener);
    }

    private IFirebaseListener firebaseListener = new IFirebaseListener() {
        @Override
        public void updatedProperties(List<Property> properties) {
            mLogger.logDebug("Received update from Firebase");
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

        @Override
        public void updatedCommentsForProperty(int propertyId, List<Comment> commentList) {
            if (commentList != null) {
                for (Comment comment : commentList) {
                    if (!comment.getId().isEmpty()) {
                        mRepository.insert(comment);
                    }
                }
            }
        }
    };

    private long getLastUpdatedTimestamp() {
        return mSharedPreferences.getLong(LAST_UPDATED_KEY, -1);
    }

    private void setLastUpdatedTimestamp(long lastUpdated) {
        mSharedPreferences.edit().putLong(LAST_UPDATED_KEY, lastUpdated).apply();
    }

    public boolean isUserLoggedIn() {
        return mFirebaseManager.isUserLoggedIn();
    }

    public String getUserUid() {
        return mFirebaseManager.getUserUid();
    }

    public String getUserName() {
        return mFirebaseManager.getUserName();
    }

    public String getUserEmail() {
        return mFirebaseManager.getUserEmail();
    }

    public void registerUser(String email, String password, String userName) {
        mFirebaseManager.registerUser(email, password, userName);
    }

    public void signInUser(String email, String password) {
        mFirebaseManager.signInUser(email, password);
    }

    public void updateUserDetails(String userName) {
        mFirebaseManager.updateUserDetails(userName);
    }

    public void logout() {
        mFirebaseManager.logout();
    }

    public void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mFirebaseManager.observeSignedInLiveData(lifecycleOwner, observer);
    }

    public void observeAuthStateLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mFirebaseManager.observeAuthStateLiveData(lifecycleOwner, observer);
    }

    public void observePropertiesLiveData(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer) {
        mRepository.getAllProperties().observe(lifecycleOwner, observer);
    }

    public LiveData<Boolean> addComment(Comment comment, @Nullable Bitmap imageBitmap) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        if (imageBitmap != null) {
            mFirebaseManager.saveImage(imageBitmap, url -> {
                comment.setImageUrl(url);
                mFirebaseManager.addComment(comment);
                done.postValue(true);
            });
        } else {
            mFirebaseManager.addComment(comment);
            done.postValue(true);
        }
        return done;
    }

    public void updateComment(Comment comment) {
        mFirebaseManager.updateComment(comment);
    }

    public void deleteComment(Comment comment) {
        mFirebaseManager.deleteComment(comment);
    }

    private void getCommentsForProperty(int propertyId) {
        mFirebaseManager.getCommentsForProperty(propertyId, firebaseListener);
    }

    public void observePropertyLiveData(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        MutableLiveData<Property> propertyLiveData = new MutableLiveData<>();
        propertyLiveData.observe(lifecycleOwner, observer);
        getCommentsForProperty(propertyId);
        mRepository.getPropertyById(propertyId).observe(lifecycleOwner, property ->
                mRepository.getCommentByPropertyId(propertyId).observe(lifecycleOwner, comments -> {
                    property.setComments(comments);
                    propertyLiveData.postValue(property);
                }));
    }

    public void observeCommentLiveData(String commentId, LifecycleOwner lifecycleOwner, Observer<Comment> observer) {
        mRepository.getCommentById(commentId).observe(lifecycleOwner, observer);
    }

    public interface SaveImageListener {
        void onComplete(String url);
    }
}
