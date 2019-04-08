package com.colman.finalproject.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.tabs.MainActivity;
import com.colman.finalproject.view.LoaderButton;

import java.util.regex.Pattern;

public class RegisterActivity extends GagBaseActivity {

    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordValidator;

    private View mEmailErrorMsg;
    private View mPasswordErrorMsg;
    private LoaderButton mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        mUserName = findViewById(R.id.user_name);
        mPassword = findViewById(R.id.password);
        mPasswordValidator = findViewById(R.id.passwordValidator);

        mEmailErrorMsg = findViewById(R.id.email_error_msg);
        mPasswordErrorMsg = findViewById(R.id.password_error_msg);
        mRegisterBtn = findViewById(R.id.register_btn);

        mEmail.setOnFocusChangeListener(this::updateEmailErrorMsg);

        mPasswordValidator.setOnFocusChangeListener(this::updatePasswordErrorMsg);

        mPassword.setOnFocusChangeListener(this::updatePasswordErrorMsg);

        mRegisterBtn.setOnClickListener(view -> {
            if(!isValidEmail()) {
                mEmailErrorMsg.setVisibility(View.VISIBLE);
            }
            if(!isValidPassword()) {
                mPasswordErrorMsg.setVisibility(View.VISIBLE);
            }
            if(isValidEmail() && isValidPassword()) {
                mRegisterBtn.handleLoadingStatus(true);
                mModel.observeSignedInLiveData(this, isSuccessful -> {
                    mRegisterBtn.handleLoadingStatus(false);
                    if (isSuccessful != null && isSuccessful) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(mEmail, "Email already exists", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(Color.LTGRAY);
                        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }
                });

                mModel.registerUser(mEmail.getText().toString(), mPassword.getText().toString(), mUserName.getText().toString());
            }
        });

    }

    private boolean isValidEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mEmail.getText()).matches();
    }

    private boolean isValidPassword() {
        return mPassword.getText().toString().equals(mPasswordValidator.getText().toString()) && !TextUtils.isEmpty(mPassword.getText().toString());
    }

    private void updatePasswordErrorMsg(View view, boolean isFocused){
        if(isFocused){
            mPasswordErrorMsg.setVisibility(View.INVISIBLE);
        }
        if(!isFocused && !isValidPassword() && !TextUtils.isEmpty(mPasswordValidator.getText().toString())) {
            //The password is invalid
            mPasswordErrorMsg.setVisibility(View.VISIBLE);
        }
        else if(!isFocused){
            //The password is valid
            mPasswordErrorMsg.setVisibility(View.INVISIBLE);
        }
    }

    private void updateEmailErrorMsg(View view, boolean isFocused) {
        if(!isFocused && !isValidEmail()) {
            //The email is invalid
            mEmailErrorMsg.setVisibility(View.VISIBLE);
        } else if (isFocused) {
            //The email is valid
            mEmailErrorMsg.setVisibility(View.INVISIBLE);
        }
    }
}
