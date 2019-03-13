package com.colman.finalproject.utils;

import android.util.Log;

import com.colman.finalproject.BuildConfig;

/**
 * Utility for logging - adds a Gag prefix to all tags.
 * Using SuppressWarnings since it's a utility and not sure all methods will be used.
 */
@SuppressWarnings("ALL")
public class Logger {
    private boolean shouldLog = BuildConfig.DEBUG;

    private String tag = "Gag";

    public Logger() {
    }

    public Logger(String tag) {
        this.tag = "Gag_" + tag;
    }

    public void logDebug(String message) {
        if (shouldLog) {
            Log.d(this.tag, message);
        }
    }

    public void logError(String message) {
        if (shouldLog) {
            Log.e(this.tag, message);
        }
    }

    public void logWarning(String message, Exception e) {
        if (shouldLog) {
            Log.w(this.tag, message, e);
        }
    }
}
