package com.colman.finalproject.bases;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.utils.SingleLiveEvent;

public class GagBaseViewModel extends AndroidViewModel {
    protected Model mModel = Model.getInstance();
    protected MutableLiveData<Boolean> mIsLoggedIn = new SingleLiveEvent<>();

    public GagBaseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return mIsLoggedIn;
    }
}
