package com.colman.finalproject.model.firebase;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.utils.DateTimeUtils;
import com.colman.finalproject.utils.Logger;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager implements IFirebaseManager {
    private Logger logger = new Logger(this.getClass().getSimpleName());

    private static FirebaseManager _instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference mPropertiesCollectionRef = mDb.collection("properties");
    private CollectionReference mCommentsCollectionRef = mDb.collection("comments");
    private CollectionReference mUsersCollectionRef = mDb.collection("users");
    private MutableLiveData<Boolean> mSignedInLiveData = new MutableLiveData<>();
    private ListenerRegistration listenerRegistration;

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
                logger.logDebug("registerUser:success");
                //TODO add registering user name to userInfos
            } else {
                logger.logDebug("registerUser:failure");
            }
            mSignedInLiveData.postValue(authResult.isSuccessful());
        });
    }

    @Override
    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((authResult) -> {
            if (authResult.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                logger.logDebug("signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                //TODO add getting user info
            } else {
                // If sign in fails, display a message to the user.
                logger.logWarning("signInWithEmail:failure", authResult.getException());
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
    @Override
    public void getCommentsForProperty(int propertyId, IFirebaseListener listener) {
        mCommentsCollectionRef.whereEqualTo("propertyId", propertyId).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                logger.logWarning("getCommentsForProperty failed.", e);
                return;
            }
            List<Comment> comments = new ArrayList<>();
            if (snapshot != null && !snapshot.isEmpty()) {
                comments = snapshot.toObjects(Comment.class);
            }
            listener.updatedCommentsForProperty(propertyId, comments);
        });
    }

    @Override
    public void addComment(Comment comment) {
        mCommentsCollectionRef.add(comment);
    }

    @Override
    public void getAllProperties(long from, IFirebaseListener listener) {
        if (from <= 0) {
            getAllProperties(DateTimeUtils.getTimeStamp(2018, 1, 1), listener);
        } else {
            getAllProperties(DateTimeUtils.getTimestampFromLong(from), listener);
        }
    }

    @Override
    public void updatePropertyListener(long from, IFirebaseListener listener) {
        listenerRegistration.remove();
        getAllProperties(DateTimeUtils.getTimestampFromLong(from), listener);
    }

    private void getAllProperties(Timestamp from, IFirebaseListener listener) {
        listenerRegistration = mPropertiesCollectionRef.whereGreaterThan("lastUpdate", from).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                logger.logWarning("Listen failed.", e);
                return;
            }
            if (snapshot != null && !snapshot.isEmpty()) {
                logger.logDebug("Current properties snapshot.getDocumentChanges() number: " + snapshot.getDocumentChanges().size());
                List<Property> properties = new ArrayList<>();
                snapshot.getDocumentChanges().get(0).getDocument().toObject(Property.class);
                for (DocumentChange docChange : snapshot.getDocumentChanges()) {
                    properties.add(docChange.getDocument().toObject(Property.class));
                }

                properties = snapshot.toObjects(Property.class);
                for (int i = 0; i < properties.size(); i++) {
                    properties.get(i).setImages(((List<String>) snapshot.getDocuments().get(i).get("imagesUrls")));
                }

                listener.updatedProperties(properties);
                logger.logDebug("Current properties number: " + properties.size());
            }
        });
    }
}