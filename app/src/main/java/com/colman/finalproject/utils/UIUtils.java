package com.colman.finalproject.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class UIUtils {

    private static Snackbar showSnackbar(Context context, @NonNull View view, @ColorRes int textColor, @NonNull CharSequence text, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        int snackbarTextId = com.google.android.material.R.id.snackbar_text;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTextColor(ContextCompat.getColor(context, textColor));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackbar(Context context, @NonNull View view, @NonNull CharSequence text, int duration) {
        return showSnackbar(context, view, android.R.color.white, text, duration);
    }
}
