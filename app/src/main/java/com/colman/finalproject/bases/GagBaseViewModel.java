package com.colman.finalproject.bases;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.colman.finalproject.model.Model;

public class GagBaseViewModel extends AndroidViewModel {
    protected Model mModel = Model.getInstance();

    public GagBaseViewModel(@NonNull Application application) {
        super(application);
    }
}
