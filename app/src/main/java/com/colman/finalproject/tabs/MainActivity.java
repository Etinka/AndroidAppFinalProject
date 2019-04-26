package com.colman.finalproject.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;

public class MainActivity extends GagBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
