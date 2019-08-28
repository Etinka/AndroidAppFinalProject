package com.colman.finalproject.login;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.utils.SingleLiveEvent;

import java.util.regex.Pattern;

public class RegisterViewModel extends GagBaseViewModel {
    private MutableLiveData<Boolean> mLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<RegisterViewState> mViewState = new SingleLiveEvent<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    void init(LifecycleOwner lifecycleOwner) {
        mModel.observeSignedInLiveData(lifecycleOwner, isLoggedIn -> {
            mIsLoggedIn.postValue(isLoggedIn);
            if (!isLoggedIn) {
                mLoadingLiveData.postValue(false);
            }
        });
    }

    LiveData<Boolean> getLoading() {
        return mLoadingLiveData;
    }

    LiveData<RegisterViewState> getViewState() {
        return mViewState;
    }

    void clickedRegister(String email, String password, String password2, String userName) {
        boolean isEmailValid = isValidEmail(email);
        boolean isPasswordValid = isValidPassword(password, password2);
        if (!isEmailValid || !isPasswordValid) {
            mViewState.postValue(new RegisterViewState(!isPasswordValid, !isEmailValid));
        } else {
            mLoadingLiveData.postValue(true);
            mModel.registerUser(email, password, userName);
        }
    }

    private boolean isValidEmail(String text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(text).matches();
    }

    private boolean isValidPassword(String password, String password2) {
        return password.equals(password2) && !TextUtils.isEmpty(password);
    }
}