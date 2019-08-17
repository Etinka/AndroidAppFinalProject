package com.colman.finalproject.properties;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.models.PropertyAndComments;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetailsViewModel extends GagBaseViewModel {

    private Property mProperty;
    private MutableLiveData<Property> mPropertyLiveData = new MutableLiveData<>();

    public PropertyDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    void setPropertyId(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mPropertyLiveData.observe(lifecycleOwner, observer);
        mModel.observePropertyLiveData(propertyId, lifecycleOwner, this::setProperty);
        mModel.getCommentsForProperty(propertyId);
    }

    private void setProperty(List<PropertyAndComments> propertyAndCommentsList) {
        if (propertyAndCommentsList != null && propertyAndCommentsList.size() > 0) {
            Property property = propertyAndCommentsList.get(0).getProperty();
            List<Comment> comments = new ArrayList<>();
            for (PropertyAndComments propertyAndComment : propertyAndCommentsList) {
                comments.add(propertyAndComment.getComments());
            }
            property.setComments(comments);
            mProperty = property;
            mPropertyLiveData.postValue(mProperty);
        }
    }

    void addComment(@NonNull String comment, @Nullable String imageUrl) {
        mModel.addComment(
                new Comment(comment, imageUrl, mModel.getUserUid(), Timestamp.now(), mModel.getUserName(), true, mProperty.getId()));
    }

    void updateComment(@NonNull Comment comment) {
        if (comment.getUserUid().equals(mModel.getUserUid())) {
            mModel.updateComment(comment);
        }
    }

    void deleteComment(@NonNull Comment comment) {
        if (comment.getUserUid().equals(mModel.getUserUid())) {
            mModel.deleteComment(comment);
        }
    }
}
