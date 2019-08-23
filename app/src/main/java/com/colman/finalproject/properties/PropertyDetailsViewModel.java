package com.colman.finalproject.properties;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.utils.SingleLiveEvent;
import com.google.firebase.Timestamp;

public class PropertyDetailsViewModel extends GagBaseViewModel {

    private Property mProperty;
    private Comment mComment;
    private MutableLiveData<Property> mPropertyLiveData = new MutableLiveData<>();
    private MutableLiveData<Comment> mCommentLiveData = new MutableLiveData<>();
    private MediatorLiveData<Boolean> mModelPostingResult = new MediatorLiveData<>();
    private SingleLiveEvent<Boolean> mPostingResult = new SingleLiveEvent<>();

    public PropertyDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    void setPropertyId(int propertyId, LifecycleOwner lifecycleOwner, Observer<Property> observer) {
        mPropertyLiveData.observe(lifecycleOwner, observer);
        mModel.observePropertyLiveData(propertyId, lifecycleOwner, this::setProperty);
    }

    void setCommentId(String commentId, LifecycleOwner lifecycleOwner, Observer<Comment> observer) {
        mModelPostingResult.observe(lifecycleOwner, success -> mPostingResult.postValue(success));
        if (TextUtils.isEmpty(commentId) || commentId.equals("0")) {
            mComment = null;
            mPostingResult.postValue(null);
            return;
        }
        mCommentLiveData.observe(lifecycleOwner, observer);
        mModel.observeCommentLiveData(commentId, lifecycleOwner, comment -> {
            mComment = comment;
            mCommentLiveData.postValue(mComment);
        });
    }

    private void setProperty(Property property) {
        mProperty = property;
        mPropertyLiveData.postValue(mProperty);
    }

    private void addComment(@NonNull String comment, @Nullable Bitmap imageBitmap) {
        mModelPostingResult.addSource(mModel.addComment(
                new Comment(comment, null, mModel.getUserUid(), Timestamp.now(), mModel.getUserName(),
                        true, mProperty.getId()), imageBitmap), aBoolean -> mPostingResult.postValue(aBoolean));
    }

    private void updateComment(String comment) {
        mComment.setText(comment);
        if (mComment.getUserUid().equals(mModel.getUserUid())) {
            mModel.updateComment(mComment);
            mPostingResult.postValue(true);
        }
    }

    void clickedAddComment(@Nullable String comment, @Nullable Bitmap bitmap) {
        if (comment != null && !TextUtils.isEmpty(comment)) {
            if (mComment != null) {
                updateComment(comment);
            } else {
                addComment(comment, bitmap);
            }
        }
    }

    void deleteComment() {
        if (mComment != null && mComment.getUserUid().equals(mModel.getUserUid())) {
            mModel.deleteComment(mComment);
        }
    }

    MutableLiveData<Property> getPropertyLiveData() {
        return mPropertyLiveData;
    }

    LiveData<Boolean> getPostingResult() {
        return mPostingResult;
    }
}
