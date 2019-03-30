package com.colman.finalproject.properties;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertiesListFragment extends GagBaseFragment {

    // View
    private View rootView;
    private RecyclerView propertiesList;
    private PropertiesAdapter adapter;

    //Data
    private List<Property> properties = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.properties_list, container, false);
            propertiesList = rootView.findViewById(R.id.properties_list);

            mModel.observePropertiesLiveData(this, properties -> {
                this.properties.clear();
                if(properties != null) {
                    this.properties.addAll(properties);
                    if (adapter == null) {
                        adapter = new PropertiesAdapter(getContext(), this.properties);
                        propertiesList.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            mModel.getAllProperties();
        }

        return rootView;
    }
}
