package com.colman.finalproject.properties;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.view.LoaderButton;

public class AddEditCommentFragment extends GagBaseFragment {

    // View
    private View rootView;
    private TextView address;
    private AppCompatEditText commentContent;
    private AppCompatImageView addImageButton;
    private AppCompatImageView uploadedImage;
    private LoaderButton addCommentButton;

    // Data
    private int propertyId;
    private boolean isEditMode;
    private PropertyDetailsViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(PropertyDetailsViewModel.class);

        propertyId = (getArguments() != null) ?
                PropertyDetailsFragmentArgs.fromBundle(getArguments()).getPropertyId() : 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.add_edit_comment_layout, container, false);
            findViews();
            initView();
        }

        return rootView;
    }

    private void findViews() {
        address = rootView.findViewById(R.id.property_address);
        commentContent = rootView.findViewById(R.id.comment_content);
        addImageButton = rootView.findViewById(R.id.add_image);
        uploadedImage = rootView.findViewById(R.id.existing_images);
        addCommentButton = rootView.findViewById(R.id.add_comment);
    }

    private void initView () {
        if (isEditMode) {

        } else {
            addCommentButton.setOnClickListener(view -> {
                viewModel.addComment(commentContent.getText().toString(), updloadImage());
            });
        }
    }

    private String updloadImage() {
        // TODO: 17/08/2019 Add upload and return the url back
        return "";
    }
}
