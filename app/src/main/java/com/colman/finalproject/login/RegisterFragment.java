package com.colman.finalproject.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.utils.UIUtils;
import com.colman.finalproject.view.LoaderButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class RegisterFragment extends GagBaseFragment {
    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordValidator;

    private View mEmailErrorMsg;
    private View mPasswordErrorMsg;
    private LoaderButton mRegisterBtn;

    private RegisterViewModel mViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        initViews(view);
        registerObservers();
        return view;
    }

    private void initViews(@NonNull View view) {
        mEmail = view.findViewById(R.id.email);
        mUserName = view.findViewById(R.id.user_name);
        mPassword = view.findViewById(R.id.password);
        mPasswordValidator = view.findViewById(R.id.passwordValidator);

        mEmailErrorMsg = view.findViewById(R.id.email_error_msg);
        mPasswordErrorMsg = view.findViewById(R.id.password_error_msg);
        mRegisterBtn = view.findViewById(R.id.register_btn);


    }

    private void registerObservers() {

        mEmail.setOnFocusChangeListener(this::updateEmailErrorMsg);

        mPasswordValidator.setOnFocusChangeListener(this::updatePasswordErrorMsg);

        mPassword.setOnFocusChangeListener(this::updatePasswordErrorMsg);

        mRegisterBtn.setOnClickListener(button -> {
            mViewModel.clickedRegister(mEmail.getText().toString(), mPassword.getText().toString(), mUserName.getText().toString());
            if (!isValidEmail()) {
                mEmailErrorMsg.setVisibility(View.VISIBLE);
            }
            if (!isValidPassword()) {
                mPasswordErrorMsg.setVisibility(View.VISIBLE);
            }
            if (isValidEmail() && isValidPassword()) {
                mRegisterBtn.handleLoadingStatus(true);
                mModel.observeSignedInLiveData(this, isSuccessful -> {
                    mRegisterBtn.handleLoadingStatus(false);
                    if (isSuccessful != null && isSuccessful) {
                        Navigation.findNavController(requireView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToBottomNavFragment());
                    } else {
                        UIUtils.showSnackbar(requireContext(), mEmail, R.color.colorPrimary, "Email already exists", Snackbar.LENGTH_LONG);
                    }
                });

//                mModel.registerUser());
            }
        });
        mViewModel.init(getViewLifecycleOwner());

    }


    private boolean isValidEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mEmail.getText()).matches();
    }

    private boolean isValidPassword() {
        return mPassword.getText().toString().equals(mPasswordValidator.getText().toString()) && !TextUtils.isEmpty(mPassword.getText().toString());
    }

    private void updatePasswordErrorMsg(View view, boolean isFocused) {
        if (isFocused) {
            mPasswordErrorMsg.setVisibility(View.INVISIBLE);
        }
        if (!isFocused && !isValidPassword() && !TextUtils.isEmpty(mPasswordValidator.getText().toString())) {
            //The password is invalid
            mPasswordErrorMsg.setVisibility(View.VISIBLE);
        } else if (!isFocused) {
            //The password is valid
            mPasswordErrorMsg.setVisibility(View.INVISIBLE);
        }
    }

    private void updateEmailErrorMsg(View view, boolean isFocused) {
        if (!isFocused && !isValidEmail()) {
            //The email is invalid
            mEmailErrorMsg.setVisibility(View.VISIBLE);
        } else if (isFocused) {
            //The email is valid
            mEmailErrorMsg.setVisibility(View.INVISIBLE);
        }
    }
}
