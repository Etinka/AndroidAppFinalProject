package com.colman.finalproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings({"unused", "NullableProblems"})
public class Property implements Parcelable {

    private int id = 0;
    private boolean elevator;
    private boolean safeRoom;
    @NonNull
    private String address = "";
    @NonNull
    private String price = "";
    @NonNull
    private String numberOfRooms = "";
    @NonNull
    private String imageUrl = "";
    @NonNull
    private String floor = "";
    @NonNull
    private String size = "";
    @NonNull
    private String houseType = "";
    @NonNull
    private String balcony = "";
    @NonNull
    private List<Comment> comments = new ArrayList<>();
    @Nullable
    private Timestamp lastUpdate;

    public Property() {
    }

    public Property(Parcel in){
        this.id = in.readInt();
        this.elevator = in.readByte() != 0;
        this.safeRoom = in.readByte() !=0;
        this.address = in.readString();
        this.price = in.readString();
        this.numberOfRooms = in.readString();
        this.imageUrl = in.readString();
        this.floor = in.readString();
        this.size = in.readString();
        this.houseType = in.readString();
        this.balcony = in.readString();
        this.comments = in.readArrayList(Comment.class.getClassLoader());
        this.lastUpdate = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public boolean isSafeRoom() {
        return safeRoom;
    }

    public void setSafeRoom(boolean safeRoom) {
        this.safeRoom = safeRoom;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    @NonNull
    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(@NonNull String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getFloor() {
        return floor;
    }

    public void setFloor(@NonNull String floor) {
        this.floor = floor;
    }

    @NonNull
    public String getSize() {
        return size;
    }

    public void setSize(@NonNull String size) {
        this.size = size;
    }

    @NonNull
    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(@NonNull String houseType) {
        this.houseType = houseType;
    }

    @NonNull
    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(@NonNull String balcony) {
        this.balcony = balcony;
    }

    @NonNull
    public List<Comment> getComments() {
        return comments;
    }

    @NonNull
    public List<Comment> getActiveComments() {
        List<Comment> active = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.isActive())
                active.add(comment);
        }
        return active;
    }

    public void setComments(@NonNull List<Comment> comments) {
        this.comments = comments;
    }

    @Nullable
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@Nullable Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", elevator=" + elevator +
                ", safeRoom=" + safeRoom +
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", floor='" + floor + '\'' +
                ", size='" + size + '\'' +
                ", houseType='" + houseType + '\'' +
                ", balcony='" + balcony + '\'' +
                ", comments=" + comments +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeByte((byte)(elevator ? 1 : 0));
        parcel.writeByte((byte)(safeRoom ? 1 : 0));
        parcel.writeString(address);
        parcel.writeString(price);
        parcel.writeString(numberOfRooms);
        parcel.writeString(imageUrl);
        parcel.writeString(floor);
        parcel.writeString(size);
        parcel.writeString(houseType);
        parcel.writeString(balcony);
        parcel.writeList(comments);
        parcel.writeParcelable(lastUpdate, i);
    }
}