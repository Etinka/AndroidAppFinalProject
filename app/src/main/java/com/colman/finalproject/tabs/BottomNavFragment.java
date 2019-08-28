package com.colman.finalproject.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.model.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavFragment extends GagBaseFragment {
    private BottomNavigationView mBottomNavigation;
    private Model mModel = Model.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_nav, container, false);
        mBottomNavigation = view.findViewById(R.id.bottomNavigation);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.bottomNavFragment);
        NavigationUI.setupWithNavController(mBottomNavigation, navController);

        mModel.observeAuthStateLiveData(getViewLifecycleOwner(), isLoggedIn -> {
            if (!isLoggedIn) {
                Navigation.findNavController(requireView()).navigate(BottomNavFragmentDirections.actionBottomNavFragmentToSignInFragment());
            }
        });
    }
}