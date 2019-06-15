package com.colman.finalproject.properties;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.google.firebase.Timestamp;

public class PropertyDetailsViewModel extends AndroidViewModel {
    private Model mModel = Model.getInstance();

    private Property mProperty;
    private MutableLiveData<Property> mPropertyLiveData = new MutableLiveData<>();

    public PropertyDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    void setPropertyId(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mPropertyLiveData.observe(lifecycleOwner, observer);
        mModel.observePropertyLiveData(propertyId, lifecycleOwner, property -> {
            mProperty = property;
            mPropertyLiveData.postValue(property);
        });

        mModel.observeCommentsLiveData(propertyId, lifecycleOwner, commentList -> {
            if (mProperty != null) {
                mProperty.setComments(commentList);
                mPropertyLiveData.postValue(mProperty);
            }
        });
    }

    void addComment(@NonNull String comment, @Nullable String imageUrl) {
        mModel.addComment(
                new Comment(comment, imageUrl, mModel.getUserUid(), Timestamp.now(), mModel.getUserName(), true, mProperty.getId()));
    }

    void deleteComment(@NonNull Comment comment) {
        if(comment.getUserUid().equals(mModel.getUserUid())) {
            mModel.deleteComment(comment);
        }
    }
}
