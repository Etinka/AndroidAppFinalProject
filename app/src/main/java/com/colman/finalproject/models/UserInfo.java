package com.colman.finalproject.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class UserInfo {
    @NonNull
    private String id;
    @NonNull
    private String email;
    @Nullable
    private String name;

    public UserInfo(@NonNull String id, @NonNull String email, @Nullable String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}