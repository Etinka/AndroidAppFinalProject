package com.colman.finalproject.properties;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.Objects;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class AddEditCommentFragment extends GagBaseFragment {
    private static final int RESULT_LOAD_IMAGE = 202;
    private static final int PICK_FROM_GALLERY = 2222;

    // View
    private View mRootView;
    private TextView mAddress;
    private AppCompatEditText mCommentContent;
    private AppCompatImageView mAddImageButton;
    private LoaderButton mAddCommentButton, mDeleteCommentButton;

    // Data
    private PropertyDetailsViewModel mViewModel;
    private Bitmap mSelectedBitmap;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(PropertyDetailsViewModel.class);
        initObserversAndViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.add_edit_comment_layout, container, false);
            initViews();
        }
        return mRootView;
    }

    private void initViews() {
        mAddress = mRootView.findViewById(R.id.property_address);
        mCommentContent = mRootView.findViewById(R.id.comment_content);
        mAddImageButton = mRootView.findViewById(R.id.add_image);
        mAddCommentButton = mRootView.findViewById(R.id.add_comment);
        mDeleteCommentButton = mRootView.findViewById(R.id.delete_comment);

        mAddCommentButton.setOnClickListener(button -> {
            hideSoftKeyBoard();
            mViewModel.clickedAddComment(
                    mCommentContent.getText() != null ? mCommentContent.getText().toString() : "", mSelectedBitmap);
            mAddCommentButton.handleLoadingStatus(true);
        });

        mDeleteCommentButton.setOnClickListener(button -> {
                    mViewModel.deleteComment();
                    Navigation.findNavController(mRootView).popBackStack();
                }
        );

        mAddImageButton.setOnClickListener(image -> {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void initObserversAndViewModel() {
        mViewModel.getPropertyLiveData().observe(getViewLifecycleOwner(), property -> mAddress.setText(property.getAddress()));

        mViewModel.getPostingResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Navigation.findNavController(mRootView).popBackStack();
                Toast.makeText(getContext(), "הצלחה!", Toast.LENGTH_SHORT).show();
            }
        });

        String commentToEditId = (getArguments() != null) ?
                AddEditCommentFragmentArgs.fromBundle(getArguments()).getCommentId() : "0";
        boolean isInEditMode = !TextUtils.isEmpty(commentToEditId) && !commentToEditId.equals("0");
        mDeleteCommentButton.setVisibility(isInEditMode ? View.VISIBLE : View.GONE);

        mViewModel.setCommentId(commentToEditId, getViewLifecycleOwner(),
                this::updateCommentViews);

    }

    private void updateCommentViews(@Nullable Comment comment) {
        if (comment == null) return;
        mCommentContent.setText(comment.getText());
        if (!TextUtils.isEmpty(comment.getImageUrl())) {
            Picasso.get().load(comment.getImageUrl()).placeholder(R.drawable.img_placeholder).into(mAddImageButton);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PICK_FROM_GALLERY) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage == null) {
                return;
            }
            Cursor cursor = Objects.requireNonNull(getContext()).getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                mSelectedBitmap = BitmapFactory.decodeFile(picturePath);
                mAddImageButton.setImageBitmap(mSelectedBitmap);
            }
        }
    }
}
