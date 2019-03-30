package com.colman.finalproject.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.widget.EditText;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.utils.UIUtils;

import java.util.regex.Pattern;

public class RegisterActivity extends GagBaseActivity {

    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordValidator;

    private Snackbar errorSnackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        mUserName = findViewById(R.id.user_name);
        mPassword = findViewById(R.id.password);
        mPasswordValidator = findViewById(R.id.passwordValidator);

        mEmail.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused && !isValidEmail(mEmail.getText().toString())) {
                errorSnackbar = UIUtils.showSnackbar(view.getContext(), mEmail, "Invalid Email", Snackbar.LENGTH_INDEFINITE);
            } else if (isFocused && errorSnackbar != null) {
                errorSnackbar.dismiss();
            }
        });

    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
