package com.colman.finalproject.bases;

import android.view.inputmethod.InputMethodManager;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.utils.Logger;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GagBaseActivity extends AppCompatActivity {
    protected Logger logger = new Logger(this.getClass().getSimpleName());
    protected Model mModel = Model.getInstance();

    protected void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }
}
