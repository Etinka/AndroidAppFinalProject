package com.colman.finalproject.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.colman.finalproject.room.ListConverters;
import com.colman.finalproject.room.TimestampConverters;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused", "NullableProblems"})
@Entity(tableName = "property_table")
@TypeConverters({TimestampConverters.class, ListConverters.class})
public class Property{
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
    private List<Comment> comments;
    @Nullable
    private ArrayList<String> imagesUrls = new ArrayList<>();
    @Nullable
    private Timestamp lastUpdate;
    @NonNull
    private double latitude;
    @NonNull
    private double longitude;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImagesUrls(@Nullable ArrayList<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @NonNull
    public List<Comment> getComments() {
        return comments;
    }

    @Nullable
    public ArrayList<String> getImagesUrls() {
        return imagesUrls;
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
    public List<String> getImages() {
        return imagesUrls;
    }

    public void setImages(@NonNull ArrayList<String> images) {
        this.imagesUrls = images;
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
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", floor='" + floor + '\'' +
                ", size='" + size + '\'' +
                ", houseType='" + houseType + '\'' +
                ", balcony='" + balcony + '\'' +
                ", comments=" + comments +
                ". images = " + imagesUrls +
                ", lastUpdate=" + lastUpdate +
                '}';
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