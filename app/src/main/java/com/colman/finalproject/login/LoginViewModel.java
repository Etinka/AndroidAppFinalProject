package com.colman.finalproject.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.utils.SingleLiveEvent;

public class LoginViewModel extends GagBaseViewModel {
    private MutableLiveData<Boolean> mLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mMoveToRegister = new SingleLiveEvent<>();

    public LoginViewModel(@NonNull Application application) {
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

    void observeLoadingLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mLoadingLiveData.observe(lifecycleOwner, observer);
    }

    void observeMoveToRegisterLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mMoveToRegister.observe(lifecycleOwner, observer);
    }

    void clickedLogin(String email, String password) {
        mLoadingLiveData.postValue(true);
        mModel.signInUser(email, password);
    }

    void clickedRegister() {
        mMoveToRegister.postValue(true);
    }
}