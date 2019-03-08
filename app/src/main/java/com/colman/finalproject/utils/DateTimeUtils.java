package com.colman.finalproject.utils;

import com.google.firebase.Timestamp;

import java.util.Calendar;

public class DateTimeUtils {

    public static Timestamp getTimeStamp(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        return new Timestamp(calendar.getTime());
    }
}
