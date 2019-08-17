package com.colman.finalproject.properties;

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
import com.colman.finalproject.view.LoaderButton;
import com.squareup.picasso.Picasso;

public class AddEditCommentFragment extends GagBaseFragment {

    // View
    private View mRootView;
    private TextView mAddress;
    private AppCompatEditText mCommentContent;
    private AppCompatImageView mAddImageButton;
    private AppCompatImageView mUploadedImage;
    private LoaderButton mAddCommentButton;
    private LoaderButton mDeleteCommentButton;
    // Data
    private PropertyDetailsViewModel mViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(PropertyDetailsViewModel.class);
        initObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.add_edit_comment_layout, container, false);
            findViews();
        }
        return mRootView;
    }

    private void findViews() {
        mAddress = mRootView.findViewById(R.id.property_address);
        mCommentContent = mRootView.findViewById(R.id.comment_content);
        mAddImageButton = mRootView.findViewById(R.id.add_image);
        mUploadedImage = mRootView.findViewById(R.id.existing_images);
        mAddCommentButton = mRootView.findViewById(R.id.add_comment);
        mDeleteCommentButton = mRootView.findViewById(R.id.delete_comment);
    }

    private void initObservers() {
        mViewModel.observeProperty(getViewLifecycleOwner(), property -> mAddress.setText(property.getAddress()));

        mAddCommentButton.setOnClickListener(button -> {
            mViewModel.clickedAddComment(mCommentContent.getText().toString());
            Navigation.findNavController(mRootView).popBackStack();
            Toast.makeText(getContext(), "הצלחה!", Toast.LENGTH_SHORT).show();
        });

        mDeleteCommentButton.setOnClickListener(button -> {
                    mViewModel.deleteComment();
                    Navigation.findNavController(mRootView).popBackStack();
                }
        );

        String commentToEditId = (getArguments() != null) ?
                AddEditCommentFragmentArgs.fromBundle(getArguments()).getCommentId() : "0";
        boolean isInEditMode = !TextUtils.isEmpty(commentToEditId) && !commentToEditId.equals("0");
        if (isInEditMode) {
            mViewModel.setCommentId(commentToEditId, getViewLifecycleOwner(),
                    this::updateCommentViews);
        } else {
            mUploadedImage.setVisibility(View.GONE);
        }

        mDeleteCommentButton.setVisibility(isInEditMode ? View.VISIBLE : View.GONE);
    }

    private void updateCommentViews(Comment comment) {
        mCommentContent.setText(comment.getText());
        if (!TextUtils.isEmpty(comment.getImageUrl())) {
            Picasso.get().load(comment.getImageUrl()).into(mUploadedImage);
        }
    }


    private String uploadImage() {
        // TODO: 17/08/2019 Add upload and return the url back
        return "";
    }
}
