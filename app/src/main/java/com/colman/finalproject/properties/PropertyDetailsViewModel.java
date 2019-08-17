package com.colman.finalproject.properties;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

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
    private Comment mComment;
    private MutableLiveData<Property> mPropertyLiveData = new MutableLiveData<>();
    private MutableLiveData<Comment> mCommentLiveData = new MutableLiveData<>();

    public PropertyDetailsViewModel(@NonNull Application application) {
        super(application);
    }


    void setPropertyId(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mPropertyLiveData.observe(lifecycleOwner, observer);
        mModel.observePropertyLiveData(propertyId, lifecycleOwner, this::setProperty);
        mModel.getCommentsForProperty(propertyId);
    }

    void setCommentId(String commentId, LifecycleOwner lifecycleOwner, Observer<Comment> observer) {
        mCommentLiveData.observe(lifecycleOwner, observer);
        mModel.observeCommentLiveData(commentId, lifecycleOwner, comment -> {
            mComment = comment;
            mCommentLiveData.postValue(mComment);
        });
    }

    void observeProperty(LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mPropertyLiveData.observe(lifecycleOwner, observer);
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

    void addComment(@NonNull String comment, @Nullable Bitmap imageBitmap) {
        mModel.addComment(
                new Comment(comment, null, mModel.getUserUid(), Timestamp.now(), mModel.getUserName(), true, mProperty.getId()), imageBitmap);
    }

    void deleteComment(@NonNull Comment comment) {
        if (comment.getUserUid().equals(mModel.getUserUid())) {
            mModel.deleteComment(comment);
        }
    }

    private void updateComment(String comment) {
        mComment.setText(comment);
        if (mComment.getUserUid().equals(mModel.getUserUid())) {
            mModel.updateComment(mComment);
        }
    }

    void clickedAddComment(@Nullable String comment) {
        if (comment != null && !TextUtils.isEmpty(comment)) {
            if (mComment != null) {
                updateComment(comment);
            } else {
                addComment(comment, null);
            }
        }
    }

    void deleteComment() {
        if (mComment != null && mComment.getUserUid().equals(mModel.getUserUid())) {
            mModel.deleteComment(mComment);
        }
    }
}
