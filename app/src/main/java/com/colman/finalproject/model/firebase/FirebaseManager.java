package com.colman.finalproject.model.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.utils.DateTimeUtils;
import com.colman.finalproject.utils.Logger;
import com.colman.finalproject.utils.SingleLiveEvent;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FirebaseManager{
    public static String EMPTY_USER_NAME = "משתמש";
    private Logger logger = new Logger(this.getClass().getSimpleName());

    private static FirebaseManager _instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference mPropertiesCollectionRef = mDb.collection("properties");
    private CollectionReference mCommentsCollectionRef = mDb.collection("comments");
    private MutableLiveData<Boolean> mAuthStateLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSignedInLiveData = new SingleLiveEvent<>();
    private ListenerRegistration listenerRegistration;

    private FirebaseManager() {
        mAuth.addAuthStateListener(firebaseAuth -> mAuthStateLiveData.postValue(isUserLoggedIn()));
    }

    public static FirebaseManager getInstance() {
        if (_instance == null) {
            _instance = new FirebaseManager();
        }
        return _instance;
    }

    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public String getUserUid() {
        return mAuth.getUid();
    }

    public String getUserName() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getDisplayName();
        }
        return EMPTY_USER_NAME;
    }

    public String getUserEmail() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }
        return EMPTY_USER_NAME;
    }

    public void updateUserDetails(String userName) {
        if (mAuth.getCurrentUser() != null) {
            logger.logDebug("updateProfile");
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
            mAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
        }
    }

    public void registerUser(String email, String password, String userName) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((authResult) -> {
            if (authResult.isSuccessful()) {
                logger.logDebug("registerUser:success");
                updateUserDetails(userName);
            } else {
                logger.logDebug("registerUser:failure");
            }
            mSignedInLiveData.postValue(authResult.isSuccessful());
        });
    }

    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((authResult) -> {
            if (authResult.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                logger.logDebug("signInWithEmail:success");
            } else {
                // If sign in fails, display a message to the user.
                logger.logWarning("signInWithEmail:failure", authResult.getException());
            }
            mSignedInLiveData.postValue(authResult.isSuccessful());
        });
    }

    public void logout() {
        mAuth.signOut();
    }

    public void observeSignedInLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mSignedInLiveData.observe(lifecycleOwner, observer);
    }

    public void observeAuthStateLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mAuthStateLiveData.observe(lifecycleOwner, observer);
    }

    //Properties
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

    public void addComment(Comment comment) {
        mCommentsCollectionRef.add(comment).addOnCompleteListener(task -> {
            if (task.getResult() != null && task.isSuccessful()) {
                comment.setId(task.getResult().getId());
                task.getResult().set(comment);
            }
        });
    }

    public void deleteComment(Comment comment) {
        comment.setActive(false);
        mCommentsCollectionRef.document(comment.getId()).set(comment);
    }

    public void updateComment(Comment comment) {
        mCommentsCollectionRef.document(comment.getId()).set(comment);
    }

    public void getAllProperties(long from, IFirebaseListener listener) {
        if (from <= 0) {
            getAllProperties(DateTimeUtils.getTimeStamp(2018, 1, 1), listener);
        } else {
            getAllProperties(DateTimeUtils.getTimestampFromLong(from), listener);
        }
    }

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
                    properties.get(i).setImages(((ArrayList<String>) Objects.requireNonNull(snapshot.getDocuments().get(i).get("imagesUrls"))));
                }

                listener.updatedProperties(properties);
                logger.logDebug("Current properties number: " + properties.size());
            }
        });
    }

    public void saveImage(Bitmap imageBitmap, final Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        Date d = new Date();
        final StorageReference imageStorageRef = storageRef.child("image_"  + d.getTime() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageStorageRef.putBytes(data);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }

            // Continue with the task to get the download URL
            return imageStorageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                assert downloadUri != null;
                listener.onComplete(downloadUri.toString());
            } else {
                listener.onComplete(null);
            }
        });
    }
}