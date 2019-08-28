package com.colman.finalproject.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.utils.UIUtils;
import com.colman.finalproject.view.LoaderButton;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends GagBaseFragment {

    private SettingsViewModel mViewModel;
    private EditText mUserName, mEmail, mPassword, mPasswordValidator;
    private LoaderButton mUpdateButton, mLogoutButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        getViews(view);
        return view;
    }

    private void getViews(View view) {
        mUserName = view.findViewById(R.id.user_name);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mPasswordValidator = view.findViewById(R.id.passwordValidator);
        mUpdateButton = view.findViewById(R.id.update_btn);
        mLogoutButton = view.findViewById(R.id.logout_btn);

        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.afterUserNameTextChanged(s);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        mUpdateButton.setOnClickListener(view -> {
            hideSoftKeyBoard();
            mViewModel.updateUserDetails(mUserName.getText().toString());
        });

        mLogoutButton.setOnClickListener(view -> mViewModel.logOut());

        mViewModel.getLoading().observe(getViewLifecycleOwner(),
                isLoading -> mUpdateButton.handleLoadingStatus(isLoading));

        mViewModel.getButtonState().observe(getViewLifecycleOwner(),
                isEnabled -> mUpdateButton.setEnabled(isEnabled));

        mViewModel.getShowSnackbar().observe(getViewLifecycleOwner(),
                message -> UIUtils.showSnackbar(requireContext(), requireView(), message, Snackbar.LENGTH_SHORT));

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            mUserName.setText(userInfo.getName());
            mEmail.setText(userInfo.getEmail());
            mPassword.setText(userInfo.getEmail());
            mPasswordValidator.setText(userInfo.getEmail());
        });
    }

}
