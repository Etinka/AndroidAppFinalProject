package com.colman.finalproject.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;

@Database(entities = {Property.class, Comment.class}, version = 1)
public abstract class PropertyRoomDatabase extends RoomDatabase {
    public abstract PropertyDao propertyDao();

    public abstract CommentDao commentsDao();

    private static volatile PropertyRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };

    static PropertyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PropertyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PropertyRoomDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}