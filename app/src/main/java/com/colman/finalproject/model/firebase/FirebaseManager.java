package com.colman.finalproject.model.firebase;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.colman.finalproject.models.Property;
import com.colman.finalproject.utils.DateTimeUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager implements IFirebaseManager {
    private final String TAG = "FirebaseManager";
    private static FirebaseManager _instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference mPropertiesCollectionRef = mDb.collection("properties");
    private CollectionReference mUsersCollectionRef = mDb.collection("users");
    private MutableLiveData<List<Property>> mPropertiesLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSignedInLiveData = new MutableLiveData<>();

    private FirebaseManager() {
    }

    public static FirebaseManager getInstance() {
        if (_instance == null) {
            _instance = new FirebaseManager();
        }
        return _instance;
    }

    @Override
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public String getUserUid() {
        return mAuth.getUid();
    }

    @Override
    public void registerUser(String email, String password, String userName) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((authResult) -> {
            if (authResult.isSuccessful()) {
                Log.d(TAG, "registerUser:success");
                //TODO add registering user name to userInfos
            } else {
                Log.d(TAG, "registerUser:failure");
            }
            mSignedInLiveData.postValue(authResult.isSuccessful());
        });
    }

    @Override
    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((authResult) -> {
            if (authResult.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                //TODO add getting user info
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", authResult.getException());
            }
            mSignedInLiveData.postValue(authResult.isSuccessful());
        });
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }

    @Override
    public void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mSignedInLiveData.observe(lifecycleOwner, observer);
    }

    //Properties
    public void observePropertiesLiveData(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer) {
        mPropertiesLiveData.observe(lifecycleOwner, observer);
    }

    @Override
    public void getAllProperties() {
        getAllProperties(DateTimeUtils.getTimeStamp(2018, 1, 1));
    }

    @Override
    public void getAllProperties(Timestamp from) {
        mPropertiesCollectionRef.whereGreaterThan("lastUpdate", from).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Property> properties = new ArrayList<>();
            if (snapshot != null && !snapshot.isEmpty()) {
                properties = snapshot.toObjects(Property.class);
            }
            mPropertiesLiveData.postValue(properties);
            Log.d(TAG, "Current properties number: " + properties.size());
        });
    }

}