package com.colman.finalproject.properties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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
            rootView = inflater.inflate(R.layout.properties_list_fragment, container, false);
            propertiesList = rootView.findViewById(R.id.properties_list);

            mModel.observePropertiesLiveData(getViewLifecycleOwner(), properties -> {
                this.properties.clear();
                if (properties != null) {
                    this.properties.addAll(properties);
                    if (adapter == null) {
                        adapter = new PropertiesAdapter(getContext(), this.properties);
                        propertiesList.setAdapter(adapter);
                        adapter.setOnItemClickListener(position -> {
                            PropertiesListFragmentDirections.ActionNavigationHomeToPropertyDetailsFragment directions =
                                    PropertiesListFragmentDirections.actionNavigationHomeToPropertyDetailsFragment(properties.get(position));
                            Navigation.findNavController(rootView).navigate(directions);
                        });
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        return rootView;
    }
}
