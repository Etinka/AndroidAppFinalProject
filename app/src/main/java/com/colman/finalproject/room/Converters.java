package com.colman.finalproject.room;

import androidx.room.TypeConverter;

import com.colman.finalproject.utils.DateTimeUtils;
import com.google.firebase.Timestamp;

@SuppressWarnings("WeakerAccess")
public class Converters {
    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : DateTimeUtils.getTimestampFromLong(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toDate().getTime();
    }
}
