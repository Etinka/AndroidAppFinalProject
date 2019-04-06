package com.colman.finalproject.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.Timestamp;

@SuppressWarnings({"unused", "NullableProblems"})
public class Comment implements Parcelable {
    @NonNull
    private String id = "";
    @NonNull
    private String text = "";
    @Nullable
    private String imageUrl = "";
    @NonNull
    private String userUid = "";
    @NonNull
    private Timestamp date = Timestamp.now();
    @NonNull
    private String userName = "";

    private boolean isActive = false;

    private int propertyId = 0;

    public Comment() {
    }

    public Comment(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.imageUrl = in.readString();
        this.userUid = in.readString();
        this.date = in.readParcelable(Timestamp.class.getClassLoader());
        this.userName = in.readString();
        this.isActive = in.readByte() !=0;
        this.propertyId = in.readInt();
    }

    public Comment(@NonNull String id, @NonNull String text, @Nullable String imageUrl, @NonNull String userUid, @NonNull Timestamp date, @NonNull String userName, boolean isActive, int propertyId) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.userUid = userUid;
        this.date = date;
        this.userName = userName;
        this.isActive = isActive;
        this.propertyId = propertyId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(@NonNull String userUid) {
        this.userUid = userUid;
    }

    @NonNull
    public Timestamp getDate() {
        return date;
    }

    public void setDate(@NonNull Timestamp date) {
        this.date = date;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userUid='" + userUid + '\'' +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                ", isActive=" + isActive +
                ", propertyId=" + propertyId +
                '}';
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}