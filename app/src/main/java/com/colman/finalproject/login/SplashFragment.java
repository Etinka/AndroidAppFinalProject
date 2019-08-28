package com.colman.finalproject.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.model.Model;

public class SplashFragment extends GagBaseFragment {
    private Model mModel = Model.getInstance();

    public SplashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mModel.isUserLoggedIn()) {
            Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavFragment());
        } else {
            Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment());
        }
    }
}