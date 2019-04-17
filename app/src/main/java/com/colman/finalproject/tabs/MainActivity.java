package com.colman.finalproject.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseActivity;
import com.colman.finalproject.map.MapFragment;
import com.colman.finalproject.properties.PropertiesListFragment;
import com.colman.finalproject.properties.PropertyDetailsFragment;
import com.colman.finalproject.utils.FragmentsTypes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends GagBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            = item -> {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment currentScreen = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentScreen = new PropertiesListFragment();
                        break;
                    case R.id.navigation_map:
                        currentScreen = new MapFragment();
                        break;
                    case R.id.navigation_notifications:
                        break;
                }

                if (currentScreen != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.screen_container, currentScreen, FragmentsTypes.PROPERTIES_LIST.name())
                            .commit();
                    return true;
                }

                return false;
            };

    public static void launch(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
