package com.colman.finalproject.properties;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.colman.finalproject.R;
import com.colman.finalproject.bases.GagBaseFragment;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.view.LoaderButton;
import com.squareup.picasso.Picasso;

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
    private String commentToEdit;
    private boolean isEditMode;
    private PropertyDetailsViewModel propertyViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyDetailsViewModel.class);

        propertyId = (getArguments() != null) ?
                AddEditCommentFragmentArgs.fromBundle(getArguments()).getPropertyId() : 0;

        commentToEdit = (getArguments() != null) ?
                AddEditCommentFragmentArgs.fromBundle(getArguments()).getCommentId() : "";

        if (!TextUtils.isEmpty(commentToEdit)) {
            isEditMode = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.add_edit_comment_layout, container, false);
            findViews();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        propertyViewModel.setPropertyId(propertyId, getViewLifecycleOwner(), this::initView);
    }

    private void findViews() {
        address = rootView.findViewById(R.id.property_address);
        commentContent = rootView.findViewById(R.id.comment_content);
        addImageButton = rootView.findViewById(R.id.add_image);
        uploadedImage = rootView.findViewById(R.id.existing_images);
        addCommentButton = rootView.findViewById(R.id.add_comment);
    }

    private void initView (Property property) {
        address.setText(property.getAddress());

        if (isEditMode) {
            uploadedImage.setVisibility(View.VISIBLE);
            for (Comment comment : property.getActiveComments()) {
                if (comment.getId().equals(commentToEdit)) {
                    commentContent.setText(comment.getText());
                    if (!TextUtils.isEmpty(comment.getImageUrl())) {
                        Picasso.get().load(comment.getImageUrl()).into(uploadedImage);
                    }
                    addCommentButton.setOnClickListener(view -> {
                        comment.setText(commentContent.getText().toString());
                        // TODO: 17/08/2019 if the image changed should update the image url property as well
//                        comment.setImageUrl(uploadImage());
                        propertyViewModel.updateComment(comment);
                        Navigation.findNavController(rootView).popBackStack();
                        Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    });
                    break;
                }
            }
        } else {
            uploadedImage.setVisibility(View.GONE);
            addCommentButton.setOnClickListener(view -> {
                propertyViewModel.addComment(commentContent.getText().toString(), uploadImage());
                Navigation.findNavController(rootView).popBackStack();
                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private String uploadImage() {
        // TODO: 17/08/2019 Add upload and return the url back
        return "";
    }
}
