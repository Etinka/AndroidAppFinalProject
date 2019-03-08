package com.colman.finalproject.bases;

import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.colman.finalproject.model.Model;

import java.util.Objects;

public abstract class GagBaseActivity extends AppCompatActivity {

    protected Model mModel = Model.getInstance();

    protected void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }
}
