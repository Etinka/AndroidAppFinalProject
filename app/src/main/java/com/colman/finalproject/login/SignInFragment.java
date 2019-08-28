package com.colman.finalproject.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.utils.UIUtils;
import com.colman.finalproject.view.LoaderButton;
import com.google.android.material.snackbar.Snackbar;

public class SignInFragment extends GagBaseFragment {
    private ConstraintSet constraintSet1 = new ConstraintSet();
    private ConstraintSet constraintSet2 = new ConstraintSet();
    private EditText mEmailTV, mPasswordTV;
    private ConstraintLayout mConstraintLayout;
    private LoaderButton mLoginBtn;
    private LoginViewModel mViewModel;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        constraintSet2.clone(requireContext(), R.layout.fragment_sign_in_final); // get constraints from layout
        initViews(view);
        registerObservers(view);
        constraintSet1.clone(mConstraintLayout); // get constraints from ConstraintSet

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            AutoTransition transition = new AutoTransition();
            transition.setDuration(1000);
            TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
            constraintSet2.applyTo(mConstraintLayout);
        }, 1000);
    }

    private void initViews(@NonNull View view) {
        mConstraintLayout = view.findViewById(R.id.constraintLayout);
        mEmailTV = view.findViewById(R.id.emailTV);
        mPasswordTV = view.findViewById(R.id.passwordTV);
        mLoginBtn = view.findViewById(R.id.loginBtn);
    }

    private void registerObservers(View view) {
        mLoginBtn.setOnClickListener(v -> {
            hideSoftKeyBoard();
            mViewModel.clickedLogin(mEmailTV.getText().toString(), mPasswordTV.getText().toString());
        });

        view.findViewById(R.id.registerBtn).setOnClickListener(v -> mViewModel.clickedRegister());

        mViewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isSignedIn -> {
            if (isSignedIn != null) {
                if (isSignedIn) {
                    Navigation.findNavController(view).navigate(SignInFragmentDirections.actionSignInFragmentToBottomNavFragment());
                } else {
                    UIUtils.showSnackbar(requireContext(), mConstraintLayout, "שם משתמש או סיסמה שגויים", Snackbar.LENGTH_LONG);
                }
            }
        });

        mViewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> mLoginBtn.handleLoadingStatus(isLoading));

        mViewModel.getMoveToRegister().observe(getViewLifecycleOwner(), show -> {
            if (show) {
                Navigation.findNavController(view).navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment());
            }
        });

        mViewModel.init(getViewLifecycleOwner());
    }
}