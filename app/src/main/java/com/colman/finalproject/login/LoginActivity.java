package com.colman.finalproject.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.register.RegisterActivity;
import com.colman.finalproject.tabs.MainActivity;
import com.colman.finalproject.utils.UIUtils;
import com.colman.finalproject.view.LoaderButton;
import com.google.android.material.snackbar.Snackbar;

//TODO add error handling and edit text verification + loading and disabling buttons
//and move logic to view model
public class LoginActivity extends GagBaseActivity {
    private ConstraintSet constraintSet1 = new ConstraintSet();// create a Constraint Set
    private ConstraintSet constraintSet2 = new ConstraintSet(); // create a Constraint Set
    private EditText mEmailTV, mPasswordTV;
    private ConstraintLayout mConstraintLayout;
    private LoaderButton mLoginBtn;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        constraintSet2.clone(context, R.layout.activity_login_final); // get constraints from layout
        setContentView(R.layout.activity_login);
        initViews();
        constraintSet1.clone(mConstraintLayout); // get constraints from ConstraintSet

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            AutoTransition transition = new AutoTransition();
            transition.setDuration(1000);
            TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
            constraintSet2.applyTo(mConstraintLayout);
        }, 1000);
    }

    private void initViews() {
        mConstraintLayout = findViewById(R.id.constraintLayout);
        mEmailTV = findViewById(R.id.emailTV);
        mPasswordTV = findViewById(R.id.passwordTV);
        mLoginBtn = findViewById(R.id.loginBtn);

        mLoginBtn.setOnClickListener(v -> {
            hideSoftKeyBoard();

            mLoginBtn.handleLoadingStatus(true);
            mModel.signInUser(mEmailTV.getText().toString(), mPasswordTV.getText().toString());
        });

        findViewById(R.id.registerBtn).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        mModel.observeSignedInLiveData(this, isSignedIn -> {
            if (isSignedIn != null) {
                if (isSignedIn) {
                    MainActivity.launch(this);
                    finish();
                } else {
                    mLoginBtn.handleLoadingStatus(false);
                    UIUtils.showSnackbar(this, mConstraintLayout, "שם משתמש או סיסמה שגויים", Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
