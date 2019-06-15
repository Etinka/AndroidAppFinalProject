package com.colman.finalproject.settings;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.models.UserInfo;

public class SettingsViewModel extends GagBaseViewModel {
    private MutableLiveData<UserInfo> mInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEnableButtonLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mShowSnackbarLiveData = new MutableLiveData<>();
    private UserInfo mUserInfo = new UserInfo();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        mUserInfo.setName(mModel.getUserName());
        mUserInfo.setEmail(mModel.getUserEmail());
        mInfoLiveData.postValue(mUserInfo);
    }

    void observeInfoLiveData(LifecycleOwner lifecycleOwner, Observer<UserInfo> observer) {
        mInfoLiveData.observe(lifecycleOwner, observer);
    }

    void observeLoadingLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mLoadingLiveData.observe(lifecycleOwner, observer);
    }

    void observeEnableButtonLiveData(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        mEnableButtonLiveData.observe(lifecycleOwner, observer);
    }

    void observeSnackbarLiveData(LifecycleOwner lifecycleOwner, Observer<String> observer) {
        mShowSnackbarLiveData.observe(lifecycleOwner, observer);
    }

    void updateUserDetails(String userName) {
        mLoadingLiveData.postValue(true);
        mModel.updateUserDetails(userName.trim());
        mLoadingLiveData.postValue(false);
        mShowSnackbarLiveData.postValue(getApplication().getString(R.string.user_name_updated));
    }

    void afterUserNameTextChanged(Editable s) {
        mEnableButtonLiveData.postValue(!s.toString().trim().equals(mUserInfo.getName()));
    }

    void logOut(){
        mModel.logout();
    }
}
