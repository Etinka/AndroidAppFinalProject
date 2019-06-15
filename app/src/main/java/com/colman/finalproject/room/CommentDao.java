package com.colman.finalproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.colman.finalproject.models.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    String COMMENTS_TABLE_NAME = "comment_table";

    @Insert
    void insert(Comment comment);

    @Query("DELETE FROM comment_table")
    void deleteAll();

    @Query("SELECT * from comment_table")
    LiveData<List<Comment>> getAllComments();

}
