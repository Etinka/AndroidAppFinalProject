package com.colman.finalproject.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "NullableProblems"})
public class Property {

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
}