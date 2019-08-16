package com.colman.finalproject.properties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertiesListFragment extends GagBaseFragment {

    // View
    private View mRootView;
    private RecyclerView mPropertiesRecyclerView;
    private PropertiesAdapter mAdapter;

    //Data
    private List<Property> mProperties = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.properties_list_fragment, container, false);
            mPropertiesRecyclerView = mRootView.findViewById(R.id.properties_list);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertiesListViewModel viewModel = ViewModelProviders.of(this).get(PropertiesListViewModel.class);

        viewModel.observePropertiesList(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                this.mProperties.clear();
                this.mProperties.addAll(properties);
                if (mAdapter == null) {
                    mAdapter = new PropertiesAdapter(getContext(), this.mProperties);
                    mPropertiesRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(position -> viewModel.clickedOnItem(properties.get(position).getId()));
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.observeSelectedPropertyLiveData(getViewLifecycleOwner(), selectedItemId -> {
            PropertiesListFragmentDirections.ActionNavigationHomeToPropertyDetailsFragment directions =
                    PropertiesListFragmentDirections.actionNavigationHomeToPropertyDetailsFragment().setPropertyId(selectedItemId);
            Navigation.findNavController(Objects.requireNonNull(getView())).navigate(directions);
        });

        viewModel.viewReady(getViewLifecycleOwner());
    }
}
