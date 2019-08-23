package com.colman.finalproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.colman.finalproject.models.Comment;

import java.util.List;

@Dao
public interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);

    @Query("DELETE FROM comment_table")
    void deleteAll();

    @Query("SELECT * from comment_table  WHERE propertyId = :propertyId AND isActive = 1 ORDER BY date DESC")
    LiveData<List<Comment>> getAllCommentsByProperyId(int propertyId);

    @Query("SELECT * FROM comment_table WHERE comment_id = :commentId ")
    LiveData<Comment> getCommentById(String commentId);

}
