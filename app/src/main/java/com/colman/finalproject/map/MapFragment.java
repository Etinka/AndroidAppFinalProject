package com.colman.finalproject.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.properties.PropertiesListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapFragment extends GagBaseFragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 345;
    private static final float DEFAULT_ZOOM = 15;

    private PropertiesListViewModel mViewModel;
    private GoogleMap mGoogleMap = null;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback locationCallback;

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

        initLocation();

        registerObservers();
    }

    private void initLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);
                    mGoogleMap.animateCamera(cameraUpdate);
                }
            }
        };
    }

    private void registerObservers() {
        mViewModel.observePropertiesList(getViewLifecycleOwner(), properties -> {
            if (properties != null && mGoogleMap != null) {
                for (Property property : properties) {
                    logger.logDebug("onMapReady: " + property.getLatitude() + " " + property.getLongitude());
                    Marker marker = mGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(new LatLng(property.getLatitude(), property.getLongitude()))
                                    .title(property.getAddress())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    marker.setTag(property.getId());
                }
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
            mGoogleMap.setOnMarkerClickListener(this);
            mViewModel.viewReady(getViewLifecycleOwner());
        }
        updateLocationUI();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker != null && marker.getTag() instanceof Integer) {
            mViewModel.clickedOnItem((Integer) marker.getTag());
        }
        return true;
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) return;

        getLocationPermission();
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                mGoogleMap.setOnMyLocationButtonClickListener(this);
                startLocationUpdates();
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void startLocationUpdates() {
        mFusedLocationProviderClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                null /* Looper */);
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}
