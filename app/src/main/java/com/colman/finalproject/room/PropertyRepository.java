package com.colman.finalproject.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.colman.finalproject.models.Comment;
import com.colman.finalproject.models.Property;
import com.colman.finalproject.models.PropertyAndComments;

import java.util.List;

public class PropertyRepository {

    private PropertyDao mPropertyDao;
    private CommentDao mCommentDao;
    private LiveData<List<Property>> mAllProperties;

    public PropertyRepository(Application application) {
        PropertyRoomDatabase db = PropertyRoomDatabase.getDatabase(application);
        mPropertyDao = db.propertyDao();
        mCommentDao = db.commentsDao();
        mAllProperties = mPropertyDao.getAllProperty();
    }

    public LiveData<List<Property>> getAllProperties() {
        return mAllProperties;
    }

    public LiveData<List<PropertyAndComments>> getPropertyById(int propertyId){
        return mPropertyDao.getPropertyById();
    }

    public LiveData<List<Comment>> getCommentByPropertyId(int propertyId){
        return mCommentDao.getAllCommentsByProperyId(propertyId);
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
