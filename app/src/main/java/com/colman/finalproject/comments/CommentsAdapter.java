package com.colman.finalproject.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.finalproject.R;
import com.colman.finalproject.models.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Comment> comments;

    CommentsAdapter(Context context, List<Comment> comments) {
        this.inflater = LayoutInflater.from(context);
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.comment_item_layout, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.writerName.setText(comments.get(i).getUserName());
        commentViewHolder.commentDate.setText(comments.get(i).getDate().toString());
        commentViewHolder.commentData.setText(comments.get(i).getText());

        Picasso.get()
                .load(comments.get(i).getImageUrl())
                .into(commentViewHolder.commentImage);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView commentImage;
        TextView writerName;
        TextView commentDate;
        TextView commentData;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            commentImage = itemView.findViewById(R.id.comment_image);
            writerName = itemView.findViewById(R.id.writer_name);
            commentDate = itemView.findViewById(R.id.comment_date);
            commentData = itemView.findViewById(R.id.comment);

        }
    }
}
