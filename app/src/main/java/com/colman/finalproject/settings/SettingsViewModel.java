package com.colman.finalproject.settings;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    LiveData<UserInfo> getUserInfo() {
        return mInfoLiveData;
    }

    LiveData<Boolean> getLoading() {
        return mLoadingLiveData;
    }

    LiveData<Boolean> getButtonState() {
        return mEnableButtonLiveData;
    }

    LiveData<String> getShowSnackbar() {
        return mShowSnackbarLiveData;
    }

    void updateUserDetails(String userName) {
        mLoadingLiveData.postValue(true);
        mModel.updateUserDetails(userName.trim());
        mUserInfo.setName(userName.trim());
        mShowSnackbarLiveData.postValue(getApplication().getString(R.string.user_name_updated));
        mEnableButtonLiveData.postValue(false);
        mLoadingLiveData.postValue(false);
    }

    void afterUserNameTextChanged(Editable s) {
        mEnableButtonLiveData.postValue(!s.toString().trim().equals(mUserInfo.getName()));
    }

    void logOut() {
        mModel.logout();
    }
}
