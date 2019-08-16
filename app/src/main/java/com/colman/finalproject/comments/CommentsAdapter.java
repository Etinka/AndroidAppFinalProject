package com.colman.finalproject.comments;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.finalproject.R;
import com.colman.finalproject.model.firebase.FirebaseManager;
import com.colman.finalproject.models.Comment;
import com.colman.finalproject.properties.PropertyDetailsFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private LayoutInflater inflater;
    private List<Comment> comments;
    private String userId;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.inflater = LayoutInflater.from(context);
        this.comments = comments;
        userId = FirebaseManager.getInstance().getUserUid();
        Log.e(TAG, "CommentsAdapter: " + userId);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.comment_item_layout, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int position) {
        Comment comment = comments.get(position);
        commentViewHolder.writerName.setText(comment.getUserName());
        commentViewHolder.commentDate.setText(DateUtils.getRelativeTimeSpanString(comment.getDate().toDate().getTime()));
        commentViewHolder.commentContent.setText(comment.getText());

        if (!TextUtils.isEmpty(comment.getImageUrl())) {
            Picasso.get()
                    .load(comment.getImageUrl())
                    .into(commentViewHolder.commentImage);
        }

        if (comment.getUserUid().equals(userId)) {
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
        return comments.size();
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