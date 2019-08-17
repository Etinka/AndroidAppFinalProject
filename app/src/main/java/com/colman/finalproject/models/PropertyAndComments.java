package com.colman.finalproject.models;

import androidx.room.Embedded;

public class PropertyAndComments {
    @Embedded
    private Property property;
    @Embedded
    private Comment comments;

    public PropertyAndComments() {
    }

    public Property getProperty() {
        return property;
    }

    public Comment getComments() {
        return comments;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setComments(Comment comments) {
        this.comments = comments;
    }
}
