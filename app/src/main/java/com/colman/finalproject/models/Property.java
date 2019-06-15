package com.colman.finalproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.colman.finalproject.room.Converters;
import com.google.firebase.Timestamp;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused", "NullableProblems"})
@Entity(tableName = "property_table")
@TypeConverters({Converters.class})
public class Property implements Parcelable{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "property_id")
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
    @Ignore
    private List<Comment> comments = new ArrayList<>();
    @Nullable
    private Timestamp lastUpdate;

    public Property() {
    }

    @Ignore
    public Property(int id, boolean elevator, boolean safeRoom, @NonNull String address, @NonNull String price, @NonNull String numberOfRooms, @NonNull String imageUrl, @NonNull String floor, @NonNull String size, @NonNull String houseType, @NonNull String balcony, @NonNull List<Comment> comments, @Nullable Timestamp lastUpdate) {
        this.id = id;
        this.elevator = elevator;
        this.safeRoom = safeRoom;
        this.address = address;
        this.price = price;
        this.numberOfRooms = numberOfRooms;
        this.imageUrl = imageUrl;
        this.floor = floor;
        this.size = size;
        this.houseType = houseType;
        this.balcony = balcony;
        this.comments = comments;
        this.lastUpdate = lastUpdate;
    }

    @Ignore
    public Property(Parcel in){
        this.id = in.readInt();
        this.elevator = in.readByte() != 0;
        this.safeRoom = in.readByte() !=0;
        this.address = Objects.requireNonNull(in.readString());
        this.price = Objects.requireNonNull(in.readString());
        this.numberOfRooms = Objects.requireNonNull(in.readString());
        this.imageUrl = Objects.requireNonNull(in.readString());
        this.floor = Objects.requireNonNull(in.readString());
        this.size = Objects.requireNonNull(in.readString());
        this.houseType = Objects.requireNonNull(in.readString());
        this.balcony = Objects.requireNonNull(in.readString());
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

    @NotNull
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
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", floor='" + floor + '\'' +
                ", size='" + size + '\'' +
                ", houseType='" + houseType + '\'' +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return id == property.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}