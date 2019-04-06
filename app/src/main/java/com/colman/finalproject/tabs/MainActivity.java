package com.colman.finalproject.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.properties.PropertiesListFragment;
import com.colman.finalproject.properties.PropertyDetailsFragment;
import com.colman.finalproject.utils.FragmentsTypes;

public class MainActivity extends GagBaseActivity {
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    public void showOtherScreen(FragmentsTypes fragmentsTypes, Bundle bundle) {
        Fragment nextScreen = null;
        switch (fragmentsTypes) {
            case PROPERTY_SCREEN:
                nextScreen = new PropertyDetailsFragment();
                break;
        }

        if (nextScreen != null) {
            nextScreen.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_container, nextScreen, fragmentsTypes.name())
                    .addToBackStack(fragmentsTypes.name())
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentScreen = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentScreen = new PropertiesListFragment();
                    break;
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    break;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    break;
            }

            if (currentScreen != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.screen_container, currentScreen, FragmentsTypes.PROPERTIES_LIST.name())
                        .commit();
                return true;
            }


            return false;
        }
    };

    public static void launch(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
