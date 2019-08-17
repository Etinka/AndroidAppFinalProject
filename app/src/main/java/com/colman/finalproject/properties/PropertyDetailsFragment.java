package com.colman.finalproject.properties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.comments.CommentsAdapter;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.view.LoaderButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class PropertyDetailsFragment extends GagBaseFragment {

    //View
    private View rootView;
    private ViewPager mImagePager;
    private TabLayout dotsIndicator;
    private TextView mPrice;
    private TextView mAddress;
    private TextView mType;
    private TextView mNumRooms;
    private TextView mBalcony;
    private TextView mSize;
    private TextView mFloor;
    private TextView mElevator;
    private TextView mSafeRoom;
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private int mPropertyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.property_details_fragment, container, false);
            findViews();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PropertyDetailsViewModel viewModel = ViewModelProviders.of(requireActivity()).get(PropertyDetailsViewModel.class);

        mPropertyId = (getArguments() != null) ?
                PropertyDetailsFragmentArgs.fromBundle(getArguments()).getPropertyId() : 0;

        viewModel.setPropertyId(mPropertyId, getViewLifecycleOwner(), property -> {
            if (property != null) {
                fillPropertyData(property);
            }
        });
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
        dotsIndicator = rootView.findViewById(R.id.dots_indicator);
        mCommentsRecyclerView = rootView.findViewById(R.id.comments);
        LoaderButton addCommentButton = rootView.findViewById(R.id.add_comment);

        mCommentsRecyclerView.setHasFixedSize(true);
        ((LinearLayoutManager) Objects.requireNonNull(mCommentsRecyclerView.getLayoutManager())).setOrientation(RecyclerView.VERTICAL);

        addCommentButton.setOnClickListener(view -> {
            PropertyDetailsFragmentDirections.ActionPropertyDetailsFragmentToAddCommentFragment direction =
                    PropertyDetailsFragmentDirections
                            .actionPropertyDetailsFragmentToAddCommentFragment()
                            .setPropertyId(mPropertyId);

            Navigation.findNavController(rootView).navigate(direction);
        });

    }

    private void fillPropertyData(Property property) {
        mPrice.setText(getString(R.string.price, property.getPrice()));
        mAddress.setText(property.getAddress());
        mType.setText(property.getHouseType());
        mNumRooms.setText(getString(R.string.num_rooms, property.getNumberOfRooms()));
        mBalcony.setText(getString(R.string.balcony, property.getBalcony()));
        mSize.setText(getString(R.string.size, property.getSize()));
        mFloor.setText(getString(R.string.floor, property.getFloor()));
        mElevator.setText(getString(R.string.elevator, property.isElevator() ?
                getString(R.string.available) : getString(R.string.unavailable)));
        mSafeRoom.setText(getString(R.string.safe_room, property.isSafeRoom() ?
                getString(R.string.available) : getString(R.string.unavailable)));

        mImagePager.setAdapter(new PropertyImagesAdapter(
                getContext(),
                property.getImages())
        );

        dotsIndicator.setupWithViewPager(mImagePager);

        if (!property.getComments().isEmpty()) {
            if (mCommentsAdapter == null) {
                mCommentsAdapter = new CommentsAdapter(getContext());
                mCommentsRecyclerView.setAdapter(mCommentsAdapter);
            }
            mCommentsAdapter.updateComments(property.getActiveComments());
        }
    }
}
