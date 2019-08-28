package com.colman.finalproject.comments;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.finalproject.R;
import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.properties.PropertyDetailsFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comment> mComments = new ArrayList<>();
    private String mUserId;

    public CommentsAdapter() {
        mUserId = FirebaseManager.getInstance().getUserUid();
    }

    public void updateComments(List<Comment> comments) {
        this.mComments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item_layout, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int position) {
        Comment comment = mComments.get(position);
        commentViewHolder.writerName.setText(comment.getUserName());
        commentViewHolder.commentDate.setText(DateUtils.getRelativeTimeSpanString(comment.getDate().toDate().getTime()));
        commentViewHolder.commentContent.setText(comment.getText());
        boolean hasImage = !TextUtils.isEmpty(comment.getImageUrl());

        if (hasImage) {
            Picasso.get()
                    .load(comment.getImageUrl())
                    .placeholder(R.drawable.img_placeholder)
                    .into(commentViewHolder.commentImage);
        }
        commentViewHolder.commentImage.setVisibility(hasImage ? View.VISIBLE : View.GONE);

        if (comment.getUserUid().equals(mUserId)) {
            commentViewHolder.editButton.setVisibility(View.VISIBLE);
            commentViewHolder.editButton.setOnClickListener(view -> {
                PropertyDetailsFragmentDirections.ActionPropertyDetailsFragmentToAddCommentFragment direction =
                        PropertyDetailsFragmentDirections
                                .actionPropertyDetailsFragmentToAddCommentFragment()
                                .setPropertyId(comment.getPropertyId())
                                .setCommentId(comment.getId());
                Navigation.findNavController(commentViewHolder.itemView).navigate(direction);
            });
        } else {
            commentViewHolder.editButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView commentImage;
        TextView writerName;
        TextView commentDate;
        TextView commentContent;
        AppCompatImageView editButton;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentImage = itemView.findViewById(R.id.comment_image);
            writerName = itemView.findViewById(R.id.writer_name);
            commentDate = itemView.findViewById(R.id.comment_date);
            commentContent = itemView.findViewById(R.id.comment);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}