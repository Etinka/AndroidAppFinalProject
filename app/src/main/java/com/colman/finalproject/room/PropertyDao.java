package com.colman.finalproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.colman.finalproject.models.Property;

import java.util.List;

@Dao
public interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProperties(Property property);

    @Query("DELETE FROM property_table")
    void deleteAll();

    @Query("SELECT * from property_table")
    LiveData<List<Property>> getAllProperty();

}
