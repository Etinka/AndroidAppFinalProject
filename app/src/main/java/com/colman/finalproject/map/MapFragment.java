package com.colman.finalproject.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MapFragment extends GagBaseFragment implements OnMapReadyCallback {

    public MapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mModel.observePropertiesLiveData(this, properties -> {
            if(properties != null) {
                double maxLong = 0;
                double maxLat = 0;
                double minLong = 40;
                double minLat = 40;
                for (Property property: properties) {
                    Log.d("Map", "onMapReady: " + property.getLatitude() + " " + property.getLongitude());
                    googleMap.addMarker(
                            new MarkerOptions()
                            .position(new LatLng(property.getLatitude(), property.getLongitude()))
                            .title(property.getAddress())
                    );
                    if (property.getLatitude() > maxLat) {
                        maxLat = property.getLatitude();
                    }
                    else if(property.getLatitude() < minLat) {
                        minLat = property.getLatitude();
                    }
                    if (property.getLongitude() > maxLong) {
                        maxLong = property.getLongitude();
                    }
                    else if(property.getLongitude() < minLong){
                        minLong = property.getLongitude();
                    }
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                        new LatLngBounds(
                                new LatLng(minLat - minLat/1000, minLong - minLong/1000),
                                new LatLng(maxLat + maxLat/1000, maxLong + maxLong/1000)),
                        0));
            }
        });
    }
}
