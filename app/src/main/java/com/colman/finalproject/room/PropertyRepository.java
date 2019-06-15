package com.colman.finalproject.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;

import java.util.List;

public class PropertyRepository {

    private PropertyDao mPropertyDao;
    private CommentDao mCommentDao;
    private LiveData<Property> mProperty;
    private LiveData<List<Property>> mAllProperties;
    private LiveData<List<Comment>> mAllComments;

    public PropertyRepository(Application application) {
        PropertyRoomDatabase db = PropertyRoomDatabase.getDatabase(application);
        mPropertyDao = db.propertyDao();
        mCommentDao = db.commentsDao();
        mAllProperties = mPropertyDao.getAllProperty();
        mAllComments = mCommentDao.getAllComments();
    }

    public LiveData<List<Property>> getAllProperties() {
        return mAllProperties;
    }

    public LiveData<Property> getPropertyById(int propertyId){
        mProperty = mPropertyDao.getPropertyById(propertyId);
        return mProperty;
    }

    public LiveData<List<Comment>> getAllComment() {
        return mAllComments;
    }

    public void insert(Property property) {
        new insertPropertyAsyncTask(mPropertyDao).execute(property);
    }

    public void insert(Comment comment) {
        new insertAsyncTask(mCommentDao).execute(comment);
    }

    private static class insertAsyncTask extends AsyncTask<Comment, Void, Void> {

        private CommentDao mAsyncTaskDao;

        insertAsyncTask(CommentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Comment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertPropertyAsyncTask extends AsyncTask<Property, Void, Void> {

        private PropertyDao mAsyncTaskDao;

        insertPropertyAsyncTask(PropertyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Property... params) {
            mAsyncTaskDao.insertProperties(params[0]);
            return null;
        }
    }
}
