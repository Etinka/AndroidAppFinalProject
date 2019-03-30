package com.colman.finalproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

public class UIUtils {

    public static Snackbar showSnackbar(Context context, @NonNull View view, @NonNull CharSequence text, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        View snackbarView = snackbar.getView();
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        snackbar.show();

        return snackbar;
    }
}
