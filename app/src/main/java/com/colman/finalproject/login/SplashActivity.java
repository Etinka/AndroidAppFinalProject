package com.colman.finalproject.login;

import android.os.Bundle;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.tabs.MainActivity;

public class SplashActivity extends GagBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logger.logDebug("Is user logged in: " + mModel.isUserLoggedIn());
        if (mModel.isUserLoggedIn()) {
            MainActivity.launch(this);
            finish();
        } else {
            LoginActivity.launch(this);
            finish();
        }
    }
}
