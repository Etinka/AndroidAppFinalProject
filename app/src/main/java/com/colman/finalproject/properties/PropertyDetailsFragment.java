package com.colman.finalproject.properties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Property;

public class PropertyDetailsFragment extends GagBaseFragment {

    //View
    private View rootView;
    private ViewPager mImagePager;
    private TextView mPrice;
    private TextView mAddress;
    private TextView mType;
    private TextView mNumRooms;
    private TextView mBalcony;
    private TextView mSize;
    private TextView mFloor;
    private TextView mElevator;
    private TextView mSafeRoom;

    //Data
    private Property mProperty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null && getArguments() != null) {
            rootView = inflater.inflate(R.layout.property_details_fragment, container, false);
            findViews();
            mProperty = PropertyDetailsFragmentArgs.fromBundle(getArguments()).getProperty();
            fillPropertyData();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModel.observePropertyLiveData(mProperty.getId(), getViewLifecycleOwner(), property -> logger.logDebug("Property onChanged " + property));
    }

    private void findViews() {
        mImagePager = rootView.findViewById(R.id.property_details_image);
        mPrice = rootView.findViewById(R.id.details_price);
        mAddress = rootView.findViewById(R.id.details_address);
        mType = rootView.findViewById(R.id.details_property_type);
        mNumRooms = rootView.findViewById(R.id.details_num_rooms);
        mBalcony = rootView.findViewById(R.id.details_balcony);
        mSize = rootView.findViewById(R.id.details_property_size);
        mFloor = rootView.findViewById(R.id.details_floor);
        mElevator = rootView.findViewById(R.id.details_elevator);
        mSafeRoom = rootView.findViewById(R.id.details_safe_room);
    }

    private void fillPropertyData() {
        mPrice.setText(getString(R.string.price, mProperty.getPrice()));
        mAddress.setText(mProperty.getAddress());
        mType.setText(mProperty.getHouseType());
        mNumRooms.setText(getString(R.string.num_rooms, mProperty.getNumberOfRooms()));
        mBalcony.setText(getString(R.string.balcony, mProperty.getBalcony()));
        mSize.setText(getString(R.string.size, mProperty.getSize()));
        mFloor.setText(getString(R.string.floor, mProperty.getFloor()));
        mElevator.setText(getString(R.string.elevator, mProperty.isElevator() ?
                getString(R.string.available) : getString(R.string.unavailable)));
        mSafeRoom.setText(getString(R.string.safe_room, mProperty.isSafeRoom() ?
                getString(R.string.available) : getString(R.string.unavailable)));

    }
}
