package com.colman.finalproject.properties;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.finalproject.bases.GagBaseViewModel;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.utils.SingleLiveEvent;

import java.util.List;

public class PropertiesListViewModel extends GagBaseViewModel {

    private MutableLiveData<List<Property>> mPropertiesLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Integer> mSelectedPropertyLiveData = new SingleLiveEvent<>();

    public PropertiesListViewModel(@NonNull Application application) {
        super(application);
    }

    public void observePropertiesList(LifecycleOwner lifecycleOwner, Observer<List<Property>> observer) {
        mPropertiesLiveData.observe(lifecycleOwner, observer);
    }

    public void observeSelectedPropertyLiveData(LifecycleOwner lifecycleOwner, Observer<Integer> observer) {
        mSelectedPropertyLiveData.observe(lifecycleOwner, observer);
    }

    public void clickedOnItem(int itemId) {
        mSelectedPropertyLiveData.postValue(itemId);
    }

    public void viewReady(LifecycleOwner lifecycleOwner) {
        mModel.observePropertiesLiveData(lifecycleOwner, properties -> mPropertiesLiveData.postValue(properties));
    }
}
