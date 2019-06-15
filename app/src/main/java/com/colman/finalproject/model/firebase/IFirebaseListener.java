package com.colman.finalproject.model.firebase;

import com.colman.finalproject.models.Property;

import java.util.List;

public interface IFirebaseListener {

    void updatedProperties(List<Property> properties);
}
