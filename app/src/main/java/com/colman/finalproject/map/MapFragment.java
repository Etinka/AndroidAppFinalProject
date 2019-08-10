package com.colman.finalproject.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.properties.PropertiesListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapFragment extends GagBaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private PropertiesListViewModel mViewModel;
    private GoogleMap mGoogleMap = null;

    public MapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mViewModel = ViewModelProviders.of(this).get(PropertiesListViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.observePropertiesList(getViewLifecycleOwner(), properties -> {
            if (properties != null && mGoogleMap != null) {
                double maxLong = 0;
                double maxLat = 0;
                double minLong = 40;
                double minLat = 40;
                for (Property property : properties) {
                    logger.logDebug("onMapReady: " + property.getLatitude() + " " + property.getLongitude());
                    Marker marker = mGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(new LatLng(property.getLatitude(), property.getLongitude()))
                                    .title(property.getAddress())
                    );
                    marker.setTag(property.getId());
                    if (property.getLatitude() > maxLat) {
                        maxLat = property.getLatitude();
                    } else if (property.getLatitude() < minLat) {
                        minLat = property.getLatitude();
                    }
                    if (property.getLongitude() > maxLong) {
                        maxLong = property.getLongitude();
                    } else if (property.getLongitude() < minLong) {
                        minLong = property.getLongitude();
                    }
                }
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                        new LatLngBounds(
                                new LatLng(minLat - minLat / 1000, minLong - minLong / 1000),
                                new LatLng(maxLat + maxLat / 1000, maxLong + maxLong / 1000)),
                        0));
            }
        });

        mViewModel.observeSelectedPropertyLiveData(getViewLifecycleOwner(), selectedItemId -> {
            MapFragmentDirections.ActionNavigationMapToPropertyDetailsFragment directions =
                    MapFragmentDirections.actionNavigationMapToPropertyDetailsFragment().setPropertyId(selectedItemId);
            Navigation.findNavController(Objects.requireNonNull(getView())).navigate(directions);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            googleMap.setOnMarkerClickListener(this);
            mViewModel.viewReady(getViewLifecycleOwner());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker != null && marker.getTag() instanceof Integer) {
            mViewModel.clickedOnItem((Integer) marker.getTag());
        }
        return true;
    }
}
