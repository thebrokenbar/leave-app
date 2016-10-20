package com.meteorhead.leave.remoteDatabase.firebase.base;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wierzchanowskig on 14.10.2016.
 */

public abstract class RemoteDb {

    protected abstract String getDbNodeName();

    protected FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

    private DatabaseReference getRootReference(FirebaseDatabase database) {
        return database.getReference();
    }

    protected DatabaseReference getReference() {
        return getRootReference(getDatabase()).child("static").child(getDbNodeName());
    }
}
